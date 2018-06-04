package com.codenotfound.kafka.flowable;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2018/5/30.
 */

@Data
//@Entity
public class Event implements Serializable{


    public static final String FLOW_STATUS = "FS";

    public static final String FLOW_STATUS_FAILED="FSF";

    public static final String FLOW_STATUS_SUCCEED="FSS";

    private Long eventId;

    private Long correlationId;

    Object payload;

//    Map<String,Object> callbacks;

    Map<String,String> headers = new HashMap<>();

    public Event(Object payload){
        this.payload = payload;
    }

    public void setHeader(String k, String val){
        headers.put(k,val);
    }

    public String getHeader(String k){
        return headers.get(k);
    }

    public String Serialize(){
        return this.toString();
    }

    public Event deSerialize(String s){
        return new Event(null);
    }

}
