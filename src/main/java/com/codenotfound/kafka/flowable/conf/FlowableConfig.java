package com.codenotfound.kafka.flowable.conf;

import com.codenotfound.kafka.flowable.impl.DeferredFlowStage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kevin on 31/05/2018.
 */
@Configuration
public class FlowableConfig {

    @Bean
    public DeferredFlowStage deferredFlowStage(){
        return new DeferredFlowStage();
    }

//    @Bean
//    public JdbcTemplate jdbcTemplate(){
//        return new JdbcTemplate();
//    }
}
