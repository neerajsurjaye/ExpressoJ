package com.spec.web.expresso.middleware;

import java.util.HashMap;
import java.util.Map;

import lombok.val;

/**
 * The class instance curretly has the following responsibilites.
 * 1. Controls if next middleware should be called or not.
 * 2. Carries data between middlewares using a map.
 */
public class MiddlewareContext {

    /**
     * Used to check if next middleware should be executed or not.
     */
    private boolean shouldExecuteNextMiddleware;
    private boolean isMiddlewareExecutedOnCurrentPath;
    private Map<String, String> ctxState;

    /**
     * Instanstiate the class.
     */
    protected MiddlewareContext() {
        shouldExecuteNextMiddleware = true;
        ctxState = new HashMap<>();
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

    /**
     * Checks if next middleware shouold be executed.
     * 
     * @return Returns true if allowed.
     */
    protected boolean isFlowAllowed() {
        return shouldExecuteNextMiddleware;
    }

    /**
     * Marks the middleware executed.
     */
    protected void markMiddlewareExecuted() {
        isMiddlewareExecutedOnCurrentPath = true;
    }

    /**
     * Marks the middleware not executed.
     */
    public void markMiddlewareNotExecuted() {
        isMiddlewareExecutedOnCurrentPath = false;
    }

    /**
     * Checks if middleare was executed.
     * 
     * @return True if executed.
     */
    protected boolean wasMiddlewareExecutedOnCurrentPath() {
        return isMiddlewareExecutedOnCurrentPath;
    }

    /**
     * Sets the state value for a key.
     * 
     * @param key   Key
     * @param value Value
     */
    public void putState(String key, String value) {
        this.ctxState.put(key, value);
    }

    /**
     * Fetches the state value for a key.
     * 
     * @param key The key for which the state should be returned.
     * @return The state for the key.
     */
    public String getState(String key) {
        String stateValue = this.ctxState.get(key);

        return stateValue != null ? stateValue : "";
    }

}
