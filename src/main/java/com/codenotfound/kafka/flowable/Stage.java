package com.codenotfound.kafka.flowable;

import com.codenotfound.kafka.flowable.exceptions.StageException;

/**
 * Created by kevin on 2018/5/30.
 */
public interface Stage {

//
//    String STAGE_FAIL="F";
//
//    String STAGE_SUCCESS="S";

    void beforeProcess(Event event) throws StageException;

    void process(Event event) throws StageException;

    void afterProcess(Event event) throws StageException;


}
