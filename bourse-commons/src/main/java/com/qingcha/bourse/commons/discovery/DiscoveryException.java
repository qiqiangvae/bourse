package com.qingcha.bourse.commons.discovery;

/**
 * @author qiqiang
 */
public class DiscoveryException extends RuntimeException{
    public DiscoveryException() {
    }

    public DiscoveryException(String message) {
        super(message);
    }

    public DiscoveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscoveryException(Throwable cause) {
        super(cause);
    }

    public DiscoveryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}