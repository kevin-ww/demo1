package com.codenotfound.kafka.flowable.exceptions;

/**
 * Created by kevin on 04/06/2018.
 */
public class AssetChangeException extends Exception {

    public AssetChangeException() {
    }

    public AssetChangeException(String message) {
        super(message);
    }

    public AssetChangeException(String user, String symbol, String cause) {
        super(String.format("asset [%s] change failed for user %s with cause", symbol, user, cause));
    }

    public AssetChangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
