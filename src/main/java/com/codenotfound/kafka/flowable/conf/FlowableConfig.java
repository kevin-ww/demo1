package com.codenotfound.kafka.flowable.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by kevin on 31/05/2018.
 */
@Configuration
public class FlowableConfig {
//
//    @Bean
//    public DeferredFlowStage deferredFlowStage(){
//        return new DeferredFlowStage();
//    }


//@Bean
//    private DataSource dataSource;

//    @Bean
//    public JdbcTemplate jdbcTemplate(){
//        return new JdbcTemplate();
//    }


    //TODO , don't use this;
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 1000;

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(timeout);
        return simpleClientHttpRequestFactory;
    }

}
