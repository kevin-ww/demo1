package com.codenotfound.kafka.service;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.SequentialFlow;
import com.codenotfound.kafka.flowable.SequentialFlowBuilder;
import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.flowable.impl.DeferredFlowStage;
import com.codenotfound.kafka.flowable.impl.FlowStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kevin on 31/05/2018.
 */

@Service
public class SampleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public String sampleService(String name,String org){
        //


        Event event = new Event();

        event.setPayload(new TestPayload(name,org));



        final SequentialFlow sequentialFlow = new SequentialFlowBuilder()

                .addStage(new FlowStage(jdbcTemplate) {
                    @Override
                    public void customProcess(Event event) throws StageException {
                        final TestPayload payload = (TestPayload) event.getPayload();

                        try {
                            save(payload);
                        } catch (Exception e) {
                            throw new StageException("exception occurred",e);
                        }

                    }
                }).addStage(new DeferredFlowStage(jdbcTemplate,kafkaTemplate) {
                    @Override
                    public void customProcess(Event event) throws StageException {

                    }
                }).createSequentialFlow();

        //
        try {
            sequentialFlow.startWith(event);
        } catch (StageException e) {
            return e.getMessage();
        }

        return event.toString();
    }

    @Transactional
    private void save(TestPayload payload) throws Exception{

        String name = payload.getName();

        String org = payload.getOrg();

        String sql = String.format("INSERT INTO USER (NAME,ORG) VALUES ('%s','%s')", name,org);

        jdbcTemplate.execute(sql);

    }


    @Data
    @AllArgsConstructor
    class TestPayload {
        String name;
        String org;
    }
}
