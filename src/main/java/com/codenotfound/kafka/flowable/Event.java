package com.codenotfound.kafka.flowable;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2018/5/30.
 */

@Data
//@Entity
public class Event {

    public static final String STAGE_HEADER = "STAGE";

    public static final String CUSTOM_HEADER = "CUSTOM";

    private Long eventId;

    private Long correlationId;

    Object payload;

    Map<String,String> headers = new HashMap<>();

    public void setHeader(String k, String val){
        headers.put(k,val);
    }

    public String getHeader(String k){
        return headers.get(k);
    }

}
