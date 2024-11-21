package com.spec.web.expresso.middleware;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

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
    void execute(HttpRequest req, HttpResponse res);

}
