package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventEmitter;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by kevin on 2018/5/30.
 */
public class DeferredFlowStage extends FlowStage implements EventEmitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeferredFlowStage.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String KAFKA_TOPIC="test";


    @Override
    public void emit(Event event) {

        final ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(KAFKA_TOPIC, event.toString());
        //handle the send;
        LOGGER.info("msg status {}",send);

    }

    @Override
    public void customProcess(Event event) throws StageException {
        //NO-OP
    }

    @Override
    public void process(Event event) throws StageException {
        persistentEvent(event);
        customProcess(event);
        emit(event);
    }
}
