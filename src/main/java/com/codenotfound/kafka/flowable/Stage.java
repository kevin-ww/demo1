package com.codenotfound.kafka.flowable;

import com.codenotfound.kafka.flowable.exceptions.StageException;

/**
 * Created by kevin on 2018/5/30.
 */
public interface Stage<E>{


    void beforeProcess(E event) throws StageException;

    void process(E event) throws StageException;

    void afterProcess(E event) throws StageException;

    String getName();


}
