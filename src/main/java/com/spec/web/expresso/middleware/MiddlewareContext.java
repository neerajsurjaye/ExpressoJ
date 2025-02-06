package com.spec.web.expresso.middleware;

/**
 * The class instance curretly has the following responsibilites.
 * 1. Controls if next middleware should be called or not.
 * 2. Carries data between middlewares using a map.
 */
public class MiddlewareContext {

    private boolean shouldExecuteNextMiddleware;

    /**
     * Instanstiate the class.
     */
    protected MiddlewareContext() {
        shouldExecuteNextMiddleware = true;
    }

    /**
     * Sets the shouldExecuteNextMiddleware flag to true.
     */
    public void executeNextMiddleware() {
        shouldExecuteNextMiddleware = true;
    }

    /**
     * Sets shouldExecuteNextMiddleware to false.
     */
    protected void reset() {
        shouldExecuteNextMiddleware = false;
    }

    protected boolean isFlowAllowed() {
        return shouldExecuteNextMiddleware;
    }

}
