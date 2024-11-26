package com.spec.web.expresso.middleware.standard;

import java.lang.reflect.Method;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareFlowController;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * Hosts static folder
 * Where every server hit will read from the disk
 * 
 * TODO: it should be called like app.use("/static" ,
 * new StaticFileServer("C://home/data"));
 */
public class StaticFileServer extends MiddlewareMetaData {

    private String staticFolderPath;

    public StaticFileServer(String staticFolderPath) {
        this(new StaticFileServerMiddleware());
        this.staticFolderPath = staticFolderPath;

    }

    private StaticFileServer(Middleware middleware) {
        super(middleware, Methods.METHOD_USE);
    }

}

/** The middleware for staticfileserver */
class StaticFileServerMiddleware implements Middleware {

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareFlowController next) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}