package edu.xupt.cs.factory.anno;

public class NoSuchActionException extends RuntimeException{
    public NoSuchActionException() {
        super();
    }

    public NoSuchActionException(String message) {
        super(message);
    }

    public NoSuchActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchActionException(Throwable cause) {
        super(cause);
    }

    protected NoSuchActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
