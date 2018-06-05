package com.codenotfound.kafka.flowable.utils;

import com.alibaba.fastjson.JSON;
import com.codenotfound.kafka.flowable.Event;


/***
 * Serializer backed with fastJson
 */
public class JsonEventSerializer {

    public static String to(Event o) {
        return JSON.toJSONString(o);
    }

    public static Event from(String s) {
        return JSON.parseObject(s,Event.class);
    }
}
