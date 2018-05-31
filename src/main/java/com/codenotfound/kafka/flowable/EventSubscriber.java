package com.codenotfound.kafka.flowable;

/**
 * Created by kevin on 2018/5/30.
 */
public interface EventSubscriber {

    void onEvents(Event... event);

//    void subscribeInBatch(Event... events);
}
