package com.codenotfound.kafka.web;

import com.codenotfound.kafka.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kevin on 2018/5/8.
 */
@RestController

public class KafkaController {

    @Autowired
    Sender sender;

    @RequestMapping("/c1")
    @ResponseBody
    public String sendMsg(String msg){

        sender.send("test",msg);

        return "msg sent";
    }
}
