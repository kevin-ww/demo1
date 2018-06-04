package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventSerializer;
import com.codenotfound.kafka.flowable.EventSubscriber;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.utils.JsonEventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SubscriberFlowStage extends FlowStage implements EventSubscriber<Event> {


    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberFlowStage.class);


    @Override
    public String getName() {
        return "subscriberStage";
    }

    private KafkaTemplate<String, String> kafkaTemplate;

    private EventSerializer<Event, String> eventSerializer = new JsonEventSerializer();


    public SubscriberFlowStage(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onEvents(Event... event) {

    }

    @Override
    public void customProcess(Event event) throws StageException {

    }

    @Override
    public void process(Event event) throws StageException {
        internalProcess(event);
    }


    @KafkaListener(id = "batch-listener-kevin", topics = "${kafka.topic.batch}")
    public void receive(List<String> data,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                         @Header(KafkaHeaders.OFFSET) List<Long> offsets)
            throws Exception {

        LOGGER.info("start of batch receive");

        for (int i = 0; i < data.size(); i++) {

            LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
                    partitions.get(i) + "-" + offsets.get(i));

            final Event e = eventSerializer.from(data.get(i));


            internalProcess(e);

            //TODO;

            this.onFail();


        }

        LOGGER.info("end of batch process");
    }

    //
    public void onFail() {
        LOGGER.error("ON FAIL");
    }

    //
    public void onSuccess() {

    }


}
