package com.codenotfound.kafka.flowable.mock;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.SequentialFlow;
import com.codenotfound.kafka.flowable.SequentialFlowBuilder;
import com.codenotfound.kafka.flowable.exceptions.AssetChangeException;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.impl.DeferredFlowStage;
import com.codenotfound.kafka.flowable.impl.FlowStage;
import com.codenotfound.kafka.flowable.impl.SubscriberFlowStage;
import com.codenotfound.kafka.flowable.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;


@Service
public class AssetService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AssetService.class);

    protected final String FABRIC_COMPOSER_ASSET_CHANGE_URL = "http://www.baidu.com";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;


    @Bean
    public SubscriberFlowStage subscriberFlowStage() {
        return new SubscriberFlowStage(jdbcTemplate) {
            @Override
            public void onFailure(Event event, Object... parameters) {
                System.out.println(parameters);

            }

            @Override
            public void onSuccess(Event event, Object... parameters) {

                try {
                    final AssetChangeRequest assetChangeRequest
                            = (AssetChangeRequest) event.getPayload();
                    updateMysqlIfFailedInFabric(assetChangeRequest);
                } catch (Exception e) {
                   LOGGER.error("onSuccess in SubscriberFlowStage",e);
                }
            }
        };
    }


    @Autowired
    SubscriberFlowStage subscriberFlowStage;

    private SequentialFlow sequentialFlow;

    public static final String DEFAULT_KAFKA_TOPIC = "test";


    @PostConstruct
    public void init() {

        sequentialFlow = new SequentialFlowBuilder()
                .addStage(new FlowStage(jdbcTemplate) {
                    @Override
                    public void customProcess(Event event) throws StageException {
                        final AssetChangeRequest assetChangeRequest = (AssetChangeRequest) event.getPayload();
                        try {
                            changeAssetInMysql(assetChangeRequest);
                        } catch (AssetChangeException e) {
                            throw new StageException(e);
                        }
                    }
                }).addStage(new DeferredFlowStage(jdbcTemplate, kafkaTemplate, DEFAULT_KAFKA_TOPIC) {
                    @Override
                    public void customProcess(Event event) throws StageException {
                        final AssetChangeRequest assetChangeRequest = (AssetChangeRequest) event.getPayload();
                        try {
                            changeAssetInFabric(assetChangeRequest);
                        } catch (AssetChangeException e) {
                            throw new StageException(e);
                        }

                    }
                })

                .addStage(subscriberFlowStage)
//
//                .addStage(new SubscriberFlowStage(jdbcTemplate) {
//                    @Override
//                    public void onFailure() {
//                        LOGGER.info("onFail");
////                        updateMysqlIfFailedInFabric("userId", "org", "symbol", new BigDecimal(1.0));
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        LOGGER.info("onSuccess");
//                    }
//
//
//                    @Override
//                    public void customProcess(Event event) throws StageException {
////                        super.customProcess(event);
//                        LOGGER.info("",event);
//                    }
//                })

                .createSequentialFlow();
    }


    //major service;
    public void changeAsset(AssetChangeRequest req) throws AssetChangeException {

        Event assetChangeEvent = new Event(req);

        try {
            sequentialFlow.startWith(assetChangeEvent);
        } catch (Exception e) {
            throw new AssetChangeException(e.getMessage());
        }

    }


    @Transactional //TODO
    protected void changeAssetInMysql(AssetChangeRequest assetChangeRequest) throws
            AssetChangeException {


        String user = assetChangeRequest.user;
        String org = assetChangeRequest.org;
        String symbol = assetChangeRequest.symbol;
        BigDecimal amount = assetChangeRequest.amount;


        //complicated transaction with two phase
        //phase-1, create user if not exist
        //phase-2, update the asset amount with the specific symbol

        try {

            LOGGER.info("create user if not exist");

            int userId = userService.createUser(user, org);


            LOGGER.info("update the asset %s for user %s", symbol, userId);

            String sql = String.format("INSERT INTO ASSET " +
                            "(user,org,symbol, amount)" +
                            " VALUES ('%s','%s','%s','%d')",
                    userId, //use userId here
                    org,
                    symbol,
                    amount.intValueExact());


            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new AssetChangeException(user, symbol, e.getMessage());
        }


    }

    protected String changeAssetInFabric(AssetChangeRequest assetChangeRequest) throws
            AssetChangeException {

        final ResponseEntity<String> res = restTemplate.postForEntity(FABRIC_COMPOSER_ASSET_CHANGE_URL,
                assetChangeRequest, String.class);

        return res.getBody();

    }


    // TODO
    protected void updateMysqlIfFailedInFabric(AssetChangeRequest assetChangeRequest) {


        String user = assetChangeRequest.user;
        String org = assetChangeRequest.org;
        String symbol = assetChangeRequest.symbol;
        BigDecimal amount = assetChangeRequest.amount;


        //TODO compensating transaction
        amount = amount.multiply(new BigDecimal(-1));

        String sql =
                String.format("INSERT INTO ASSET " +
                                "(user,org,symbol,amount)" +
                                " VALUES ('%s','%s','%s','%d')",
                        user, //use userId here
                        org,
                        symbol,
                        amount);


        JdbcUtils.execute(jdbcTemplate, sql);

    }


}
