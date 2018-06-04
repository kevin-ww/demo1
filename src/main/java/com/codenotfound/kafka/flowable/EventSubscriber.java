package com.codenotfound.kafka.flowable;

/**
 * Created by kevin on 2018/5/30.
 */
public interface EventSubscriber<E> {

    void onEvents(E... events);

}
