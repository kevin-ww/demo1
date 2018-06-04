package com.codenotfound.kafka.flowable;

/**
 * Created by kevin on 04/06/2018.
 */
public interface EventSerializer<E,T> {

    T to(E o);

    E from(T t);


}
