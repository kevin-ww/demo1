package com.codenotfound.kafka.flowable;

/**
 * Created by kevin on 2018/5/30.
 */
public interface EventEmitter {
    void emit(Event event);
}
