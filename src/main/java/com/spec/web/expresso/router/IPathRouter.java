package com.spec.web.expresso.router;

import java.util.List;

import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/** Defines guidelines on what methods are required on a router */
public interface IPathRouter {

    /**
     * Registers a middleware. The middleware will execute regardless of
     * http method.
     * 
     * @param middleware The primary middleware to register
     */
    void use(Middleware middleware);

    /**
     * Registers a middleware on a path. The middlewares will execute regardless of
     * http method.
     * 
     * @param path       The path on which the middlewares will be
     *                   registered.
     * 
     * @param middleware The primary middleware to register
     */
    void use(String path, Middleware middleware);

    /**
     * Registers middlewares. The middlewares will execute regardless of
     * http method.
     * 
     * 
     * 
     * @param middleware            The primary middleware to register
     * @param addMiddleware         Additional middleware
     * @param additionalMiddlewares more middlewares it may execute
     */
    void use(Middleware middleware, Middleware addMiddleware, Middleware... additionalMiddlewares);

    /**
     * Registers middlewares on a path. The middlewares will execute regardless of
     * http method.
     * 
     * @param path                  The path on which the middlewares will be
     *                              registered.
     * @param middleware            The primary middleware to register
     * @param addMiddleware         Additional middleware
     * @param additionalMiddlewares Optional. More middlewares to regiter
     */
    void use(String path, Middleware middleware, Middleware addMiddleware, Middleware... additionalMiddlewares);

    /**
     * Registers routers. All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param router     The primary router to register
     * @param addRouters Optional. additional routers to register
     */
    void use(IPathRouter router, IPathRouter... addRouters);

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
     * Registers middlewares on empty path which will only execute for HTTP get
     * methods.
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void get(Middleware middleware, Middleware... additionalMiddleware);

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
     * Registers middlewares on empty path which will only execute for HTTP POST
     * methods.
     * 
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void post(Middleware middleware, Middleware... additionalMiddleware);

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
     * Registers middlewares on a path which will only execute for HTTP PUT
     * methods.
     *
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void put(Middleware middleware, Middleware... additionalMiddleware);

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
     * Registers middlewares on a path which will only execute for HTTP DELETE
     * methods.
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    void delete(Middleware middleware, Middleware... additionalMiddleware);

    /**
     * Creates a clone of current router and registers it on a path. The current
     * router is unchanged.
     * 
     * @param path The path on which the router should be registered.
     * @return The new router on which is registered on the provided path.
     */
    IPathRouter registerRouterOnPath(String path);

    /**
     * Returns the list of all middlewareMetadatas registerd on the current router
     * 
     * @return List of middlewareMetadata
     */
    List<MiddlewareMetaData> getMiddlewareMetadataAsList();

    /**
     * Method will register a middlewareMetadata on a specific path.
     * 
     * @param path                The path on which the middlewareMetadata should be
     *                            registered.
     * @param middlewareMetaData  Middlewaremetadata to Register.
     * @param middlewareMetaDatas Additional Middlewaremetadata to Register.
     */
    void use(String path, MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas);

    /**
     * Method will register a middlewareMetadata on a specific path.
     * 
     * @param middlewareMetaData  MiddlewareMetadata to register.
     * @param middlewareMetaDatas Additional middlewareMetadata's to register.
     */
    void use(MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas);

    /**
     * Registers one middleware which will execute before the routers being
     * registerd.
     * 
     * All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param middleware        The middleware to register
     * @param iPathRouter       Router to register on this path.
     * @param additionalRouters Optional. List of routers to registers with the
     *                          current
     *                          middleware
     */
    void use(Middleware middleware, IPathRouter iPathRouter, IPathRouter... additionalRouters);

    /**
     * Registers one middleware which will execute before the routers being
     * registerd. All of these are registered on the a path.
     * 
     * All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * 
     * 
     * @param path        Path on which middleware and router's should execute.
     * @param middleware  The middleware to register
     * @param iPathRouter First router that should be registerd after the
     *                    middleware.
     * @param addRouters  Optional. List of routers to registers with the current
     *                    middleware
     */
    void use(String path, Middleware middleware, IPathRouter iPathRouter, IPathRouter... addRouters);

}
