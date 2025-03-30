package com.spec.web.expresso.exceptions;

/**
 * Custom exception to handle runtime exceptions in Expresso.
 */
public class ExpressoRuntimeException extends RuntimeException {

    /**
     * Constructs a Exception with the message as String.
     * 
     * @param message Error message
     */
    public ExpressoRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a Exception with the message from a throwable.
     * 
     * @param throwable Uses another throwable to construct an excepion.
     */
    public ExpressoRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
