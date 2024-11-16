package com.spec.web.expresso.callback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * The following functional interface will be implemented by the user, using a lambda expression.
 * 
 * This lambda will act as middleware. where the user will define custom logic that will modify HTTP request and response.
 */
@FunctionalInterface
public interface RequestCallback {

    /**
     * Process request and response
     * 
     * @param req represents client request
     * @param res represents client response
     * 
     */
    void execute(HttpServletRequest req, HttpServletResponse res);

}
