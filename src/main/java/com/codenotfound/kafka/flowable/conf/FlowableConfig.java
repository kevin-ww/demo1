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


    public static final int DEFAULT_HTTP_TIMEOUT = 500;

    public static final int DEFAULT_KAFKA_PRODUCER_TIMEOUT = 3000;


    //TODO , don't use this in production;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }


    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(DEFAULT_HTTP_TIMEOUT);
        return simpleClientHttpRequestFactory;
    }

}
