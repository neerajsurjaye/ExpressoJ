package com.spec.web.expresso.router;

import java.util.List;

import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/** Defines guidelines on what methods are required on a router */
public interface IPathRouter {

    /** Middleware wiil execute regardless of HTTP method */
    void use(Middleware middleware, Middleware... additionalMiddleware);

    /** Middleware wiil execute regardless of HTTP method */
    void use(String path, Middleware middleware, Middleware... additionalMiddleware);

    /** Middleware wiil execute regardless of HTTP method */
    void use(IPathRouter router, IPathRouter... addRouters);

    /** Middleware wiil execute regardless of HTTP method */
    void use(String path, IPathRouter router, IPathRouter... addRouters);

    /** Middleware will register a middlewareMetadata */
    void use(String path, MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas);

    /** Middleware wiil execute on HTTP GET method */
    void get(String path, Middleware middleware, Middleware... additionalMiddleware);

    /** Middleware wiil execute on HTTP POST method */
    void post(String path, Middleware middleware, Middleware... additionalMiddleware);

    /** Middleware wiil execute on HTTP PUT method */
    void put(String path, Middleware middleware, Middleware... additionalMiddleware);

    /** Middleware wiil execute on HTTP DELETE method */
    void delete(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers router on a path. The middleware will require the given path to
     * execute
     */
    IPathRouter registerRouterOnPath(String path);

    /** Returns all the middlewares registerd on the current router */
    List<MiddlewareMetaData> getMiddlewareMetadataAsList();

}
