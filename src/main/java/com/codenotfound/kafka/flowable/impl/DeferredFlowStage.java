package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventEmitter;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.utils.JsonEventSerializer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

import static com.codenotfound.kafka.flowable.conf.FlowableConfig.DEFAULT_KAFKA_PRODUCER_TIMEOUT;


public class DeferredFlowStage extends FlowStage implements EventEmitter<Event> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeferredFlowStage.class);

    private KafkaTemplate<String, String> kafkaTemplate;


    private String kafkaTopic;

    public DeferredFlowStage(JdbcTemplate jdbcTemplate, KafkaTemplate kafkaTemplate, String kafkaTopic) {
        super(jdbcTemplate);
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
    }


    @Override
    public String getName() {
        return "deferredStage";
    }

    @Override
    public void emit(Event event) throws Exception {

        //TODO; confirm the message emit;

        final RecordMetadata recordMetadata = kafkaTemplate
                .send(this.kafkaTopic, JsonEventSerializer.to(event))
                .get(DEFAULT_KAFKA_PRODUCER_TIMEOUT, TimeUnit.MILLISECONDS)
                .getRecordMetadata();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("record meta data : ", recordMetadata);
        }

    }

    @Override
    public void customProcess(Event event) throws StageException {
        //NO-OP
    }

    @Override
    public void process(Event event) throws StageException {
        internalProcess(event);
        try {
            emit(event);
        } catch (Exception e) {
            throw new StageException(e);
        }
    }
}
