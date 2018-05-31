package com.codenotfound.kafka.flowable.exceptions;

/**
 * Created by kevin on 31/05/2018.
 */
public class StageException extends Exception {

    public StageException(String message) {
        super(message);
    }

    public StageException(String message, Throwable cause) {
        super(message, cause);
    }


}
