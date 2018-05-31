package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventSubscriber;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.List;

/**
 * Created by kevin on 2018/5/30.
 */
public class SubscriberFlowStage extends FlowStage implements EventSubscriber {


    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberFlowStage.class);

    public SubscriberFlowStage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void onEvents(Event... event) {

    }

    @Override
    public void customProcess(Event event) throws StageException{
        //NO-OP
    }

    @Override
    public void process(Event event) throws StageException{
        persistentEvent(event);
        customProcess(event);
    }


    @KafkaListener(id = "batch-listener", topics = "${kafka.topic.batch}")
    public void subscribe(List<String> data,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOGGER.info("start of batch receive");
        for (int i = 0; i < data.size(); i++) {
            LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
                    partitions.get(i) + "-" + offsets.get(i));
            // handle message

//            latch.countDown();
        }
        LOGGER.info("end of batch receive");
    }


//    @KafkaListener(id = "batch-listener", topics = "${kafka.topic.batch}")
//    public void receive(List<String> data,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
//                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
//        LOGGER.info("start of batch receive");
//        for (int i = 0; i < data.size(); i++) {
//            LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
//                    partitions.get(i) + "-" + offsets.get(i));
//            // handle message
//
////            latch.countDown();
//        }
//        LOGGER.info("end of batch receive");
//    }



}
