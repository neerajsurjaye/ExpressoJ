package com.spec.web.expresso.middleware;

/**
 * The class instance is used to decide if the next middleware function in the
 * queue should be called or not.
 */
public class MiddlewareFlowController {

    boolean flag;

    /**
     * Instantiates the class.
     */
    public MiddlewareFlowController() {
        flag = true;
    }

    /**
     * Sets the flag to true. If true the executor will call the next middleware.
     */
    public void next() {
        flag = true;
    }

    /**
     * Sets the current flag to false. The executor will stop calling the next
     * middlewares.
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
