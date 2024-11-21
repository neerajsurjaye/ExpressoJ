package com.spec.web.expresso.middleware;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

/**
 * The following functional interface will act as a middleware.
 * Where it accepts three parameters req, res and next() where
 */
@FunctionalInterface
public interface Middleware {

    /**
     * The following method is implemented by user. Modifying the http request and
     * modify the response.
     * 
     * The MiddlewareFlowController.next() is used to call the next middleware on
     * the queue.
     * 
     * @param req  the http request
     * @param res  the http response
     * @param next MiddlewareFlowController
     */
    public void execute(HttpRequest req, HttpResponse res, MiddlewareFlowController next);

}