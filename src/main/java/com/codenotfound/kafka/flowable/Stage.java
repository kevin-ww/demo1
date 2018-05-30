package com.codenotfound.kafka.flowable;

/**
 * Created by kevin on 2018/5/30.
 */
public interface Stage {


    void beforeProcess(Event event);

    void process(Event event);

    void afterProcess(Event event);


}
