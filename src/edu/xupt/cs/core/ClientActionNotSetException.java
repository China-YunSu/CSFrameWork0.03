package edu.xupt.cs.core;

public class ClientActionNotSetException extends Exception {
    public ClientActionNotSetException() {
        super();
    }

    public ClientActionNotSetException(String message) {
        super(message);
    }

    public ClientActionNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientActionNotSetException(Throwable cause) {
        super(cause);
    }

    protected ClientActionNotSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
