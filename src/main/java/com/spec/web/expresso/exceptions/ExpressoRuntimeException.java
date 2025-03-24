package com.spec.web.expresso.exceptions;

/**
 * Custom exception to handle runtime exceptions in Expresso.
 */
public class ExpressoRuntimeException extends RuntimeException {
    public ExpressoRuntimeException(String message) {
        super(message);
    }

    public ExpressoRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
