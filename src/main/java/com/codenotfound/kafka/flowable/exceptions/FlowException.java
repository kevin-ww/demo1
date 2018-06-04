package com.codenotfound.kafka.flowable.exceptions;


public class FlowException extends Exception{


    public FlowException(String msg) {
        super(msg);
    }

    public FlowException(String msg,Throwable t){
        super(msg,t);
    }

    public FlowException(StageException e) {
        super(e);
    }
}
