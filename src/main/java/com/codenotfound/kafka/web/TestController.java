package com.codenotfound.kafka.web;

import com.codenotfound.kafka.flowable.mock.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by kevin on 2018/5/8.
 */
@RestController

public class TestController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

//    @Autowired
//    Sender sender;


    @Autowired
    AssetService sampleService;

    @RequestMapping(value = "case1",method = RequestMethod.POST)
    @ResponseBody
    public void case1(String org, String user, String symbol, BigDecimal amount){

        LOGGER.info("test case to increase the {} of {}/{} with the quantity of {}", symbol,user,org,amount);

    }


}
