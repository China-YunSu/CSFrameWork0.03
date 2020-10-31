package edu.xupt.cs.factory.anno;

public class ParameterAnnoExption extends RuntimeException{
    private static final long serialVersionUID = -5560267952964145542L;

    public ParameterAnnoExption() {
        super();
    }

    public ParameterAnnoExption(String message) {
        super(message);
    }

    public ParameterAnnoExption(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterAnnoExption(Throwable cause) {
        super(cause);
    }

    protected ParameterAnnoExption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
