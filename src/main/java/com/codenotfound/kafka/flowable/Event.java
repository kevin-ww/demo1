package com.codenotfound.kafka.flowable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2018/5/30.
 */

@Data
@NoArgsConstructor
public class Event implements Serializable{

    public static final String FLOW_STATUS = "FS";

    public static final String FLOW_STATUS_FAILED="FSF";

    public static final String FLOW_STATUS_SUCCEED="FSS";

    @JSONField
    private Long eventId;

    @JSONField
    private Long correlationId;

    @JSONField
    Object payload;

    @JSONField
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

}
