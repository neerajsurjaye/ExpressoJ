package com.spec.web.expresso.router;

import java.util.List;

import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/** Defines guidelines on what methods are required on a router */
public interface IPathRouter {

    /**
     * Registers middlewares. The middlewares will execute regardless of
     * http method.
     * 
     * @param middleware           The primary middleware to register
     * @param additionalMiddleware more middlewares it may execute
     */
    void use(Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers middlewares on a path. The middlewares will execute regardless of
     * http method.
     * 
     * @param path                 The path on which the middlewares will be
     *                             registered.
     * @param middleware           The primary middleware to register
     * @param additionalMiddleware Optional. More middlewares to regiter
     */
    void use(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers routers. All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param router     The primary router to register
     * @param addRouters Optional. additional routers to register
     */
    void use(IPathRouter router, IPathRouter... addRouters);

    /** Middleware wiil execute regardless of HTTP method */

    /**
     * Registers routers on a path. All the middlewares registered on these routers
     * will execute on current router path + the registered path.
     * 
     * @param path       The path on which the routers will be
     *                   registered.
     * @param router     The Primary router to register
     * @param addRouters Optional. additional routers to register
     */
    void use(String path, IPathRouter router, IPathRouter... addRouters);

    /**
     * Registers middlewares on a path which will only execute for HTTP get methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void get(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers middlewares on a path which will only execute for HTTP POST
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void post(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers middlewares on a path which will only execute for HTTP PUT
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void put(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Registers middlewares on a path which will only execute for HTTP DELETE
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void delete(String path, Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Creates a clone of current router and registers it on a path. The current
     * router is unchanged.
     * 
     * @param path The path on which the router should be registered.
     * @return The new router on which is registered on the provided path.
     */
    IPathRouter registerRouterOnPath(String path);

    /** Returns all the middlewares registerd on the current router */

    /**
     * Returns the list of all middlewareMetadatas registerd on the current router
     * 
     * @return List of middlewareMetadata
     */
    List<MiddlewareMetaData> getMiddlewareMetadataAsList();

    /** Middleware will register a middlewareMetadata */
    void use(String path, MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas);

}
