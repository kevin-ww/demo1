package com.codenotfound.kafka.flowable.utils;

import com.alibaba.fastjson.JSON;
import com.codenotfound.kafka.flowable.Event;
import com.codenotfound.kafka.flowable.EventSerializer;


/***
 * Serializer backed with fastJson
 */
public class JsonEventSerializer implements EventSerializer<Event,String> {



    @Override
    public String to(Event o) {
        return JSON.toJSONString(o);
    }

    @Override
    public Event from(String s) {
        return JSON.parseObject(s,Event.class);
    }
}
