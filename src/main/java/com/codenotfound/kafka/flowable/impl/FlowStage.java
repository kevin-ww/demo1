package com.codenotfound.kafka.flowable.impl;

import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kevin on 2018/5/30.
 */
public abstract class FlowStage implements Stage{


    public static final String PROCESS_SUCCESS="S";


    public static final String PROCESS_FAIL="F";


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void beforeProcess(Event event) {

    }

    @Transactional
    public abstract void customProcess(Event event);

    @Override
    public void process(Event event) {
        final String h = event.getHeader(Event.CUSTOM_HEADER);
        if(h.equalsIgnoreCase(PROCESS_SUCCESS)){
//            jdbcTemplate.execute(execute);
//            jdbcTemplate.
        }
    }

    @Override
    public void afterProcess(Event event) {

    }


    @Data
    @AllArgsConstructor
    class StageStatus{
        String stageName;
        String status;
        Throwable exception;
    }

//    enum TransactionStatus{
//        S,  //success
//        F   //fail
//    }
}
