package com.spec.web.expresso.callback;

/**
 * The class instance is used to decide if the next middleware function in the
 * queue should be called or not.
 */
public class MiddlewareFlowController {

    boolean flag;

    /**
     * 
     */
    public MiddlewareFlowController() {
        flag = true;
    }

    /**
     * Sets the flag to true essentially calling the next middleware
     */
    public void next() {
        flag = true;
    }

    /**
     * Sets the flag to false
     */
    public void reset() {
        flag = false;
    }

    /**
     * Returns the value of the flag
     * 
     * @return value of flag
     */
    public boolean isFlowAllowed() {
        return flag;
    }

}
