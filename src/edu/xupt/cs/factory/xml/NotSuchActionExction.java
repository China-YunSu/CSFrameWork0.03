package edu.xupt.cs.factory.xml;

public class NotSuchActionExction extends Exception {
    private static final long serialVersionUID = -3571610231268509918L;

    public NotSuchActionExction() {
        super();
    }

    public NotSuchActionExction(String message) {
        super(message);
    }

    public NotSuchActionExction(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSuchActionExction(Throwable cause) {
        super(cause);
    }

    protected NotSuchActionExction(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
