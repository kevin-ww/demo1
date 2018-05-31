package com.codenotfound.kafka.web;

import com.codenotfound.kafka.flowable.exceptions.StageException;
import com.codenotfound.kafka.producer.Sender;
import com.codenotfound.kafka.service.SampleService;
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


    @Autowired
    SampleService sampleService;

//    @Autowired
//    DeferredFlowStage stage;

    @RequestMapping("/c1")
    @ResponseBody
    public String sendMsg(String msg) {

        sender.send("test", "testmsg" + System.nanoTime());

        return "msg sent";
    }


//    @RequestMapping("c2")
//    @ResponseBody
//    public String case2() {
//
//        Event event = new Event();
//        event.setPayload(new TestPayload("kevin", "mw"));
//        stage.emit(event);
//
//        return "msg sent";
//    }


    @RequestMapping("c3")
    @ResponseBody
    public String case3() throws StageException {

        //

        return sampleService.sampleService("kevin","magic");



//        return "flow done";
    }


}
