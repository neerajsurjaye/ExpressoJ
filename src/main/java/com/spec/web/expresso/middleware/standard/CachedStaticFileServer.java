package com.spec.web.expresso.middleware.standard;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareFlowController;

/**
 * Hosts static folder.
 * Only reads data from disk when server starts then it sends data to user from
 * memory.
 */
public class CachedStaticFileServer implements Middleware {

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareFlowController next) {

    }

}
