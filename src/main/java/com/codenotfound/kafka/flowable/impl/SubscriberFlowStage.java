package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventSubscriber;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.utils.JsonEventSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


@Component
public abstract class SubscriberFlowStage extends FlowStage implements EventSubscriber<Event> {


    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberFlowStage.class);


    @Override
    public String getName() {
        return "subscriberStage";
    }


    public SubscriberFlowStage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void onEvents(Event... event) {

    }

    @Override
    public void customProcess(Event event) throws StageException {

    }

    @Override
    public void process(Event event) throws StageException {
        //NO-OP;
    }


    @KafkaListener(id = "batch-listener-subscriberStage", topics = "${kafka.topic.batch}")
    public void listen(List<String> events) throws Exception {

        for (String s : events) {
            try {
                Event event = JsonEventSerializer.from(s);
                handle(event);
//                ack.acknowledge();
            } catch (Exception e) {
                throw new StageException("listen on SubscriberFlowStage", e);
            }
        }

    }

    private void handle(Event event) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("handle {}", event);
        }

        internalProcess(event);

        if (eventHasError(event)) {
            onFailure(event);
        } else {
            onSuccess(event);
        }


    }

    protected boolean eventHasError(Event event) {

        final String flowStatus = event.getHeader(Event.FLOW_STATUS);
        return StringUtils.isEmpty(flowStatus) ?
                false : flowStatus.equalsIgnoreCase(Event.FLOW_STATUS_FAILED);

    }

    //
    public abstract void onFailure(Event event, Object... parameters);

    //
    public abstract void onSuccess(Event event, Object... parameters);

}
