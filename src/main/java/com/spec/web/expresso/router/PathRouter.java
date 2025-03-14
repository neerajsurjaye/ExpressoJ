package com.spec.web.expresso.router;

import java.util.ArrayList;
import java.util.List;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * Following class exectues middlewares based on http url path and http methods.
 * It can also register other routers,
 * 
 */
public class PathRouter implements IPathRouter {

    /** List of middleware metadata registered on this router */
    protected List<MiddlewareMetaData> middlewares;

    /**
     * Constructs the class.
     */
    public PathRouter() {
        middlewares = new ArrayList<>();
    }

    /**
     * Constructs the class with a list of perdefined middleware metadata.
     * 
     * @param middlewares List of middleware metadata
     */
    private PathRouter(List<MiddlewareMetaData> middlewares) {
        this.middlewares = middlewares;
    }

    /**
     * Appends a already constructed middleware metadata object to the list.
     * 
     * @param middlewareMetaData The middleware metadata to add to the list.
     */
    public void appendMiddlewareMetaData(MiddlewareMetaData middlewareMetaData) {
        middlewares.add(middlewareMetaData);
    }

    /**
     * Registers middlewares. The middlewares will execute regardless of
     * http method.
     * 
     * @param middleware           The primary middleware to register
     * @param additionalMiddleware more middlewares it may execute
     */
    @Override
    public void use(Middleware middleware, Middleware... additionalMiddleware) {

        addMiddlewares(null, Methods.METHOD_USE, middleware, additionalMiddleware);

    }

    /**
     * Registers middlewares on a path. The middlewares will execute regardless of
     * http method.
     * 
     * @param path                 The path on which the middlewares will be
     *                             registered.
     * @param middleware           The primary middleware to register
     * @param additionalMiddleware Optional. More middlewares to regiter
     */
    @Override
    public void use(String path, Middleware middleware, Middleware... additionalMiddleware) {

        addMiddlewares(path, Methods.METHOD_USE, middleware, additionalMiddleware);
    }

    /**
     * Registers routers. All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param router            The primary router to register
     * @param additionalRouters Optional. additional routers to register
     */
    @Override
    public void use(IPathRouter router, IPathRouter... additionalRouters) {
        middlewares.addAll(router.getMiddlewareMetadataAsList());

        for (IPathRouter currRouter : additionalRouters) {
            middlewares.addAll(currRouter.getMiddlewareMetadataAsList());
        }

    }

    /**
     * Registers routers on a path. All the middlewares registered on these routers
     * will execute on current router path + the registered path.
     * 
     * @param path       The path on which the routers will be
     *                   registered.
     * @param router     The Primary router to register
     * @param addRouters Optional. additional routers to register
     */
    @Override
    public void use(String path, IPathRouter router, IPathRouter... addRouters) {

        IPathRouter firstRouterCopy = router.registerRouterOnPath(path);
        this.middlewares.addAll(firstRouterCopy.getMiddlewareMetadataAsList());

        for (IPathRouter currentRouter : addRouters) {
            IPathRouter copyOfCurrentRouter = currentRouter.registerRouterOnPath(path);
            this.middlewares.addAll(copyOfCurrentRouter.getMiddlewareMetadataAsList());
        }

    }

    /**
     * Registers middlewares on a path which will only execute for HTTP get methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void get(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_GET, middleware, additionalMiddleware);
    }

    /**
     * Registers middlewares on a path which will only execute for HTTP POST
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void post(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_POST, middleware, additionalMiddleware);
    }

    /**
     * Registers middlewares on a path which will only execute for HTTP PUT
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void put(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_PUT, middleware, additionalMiddleware);
    }

    /**
     * Registers middlewares on a path which will only execute for HTTP DELETE
     * methods.
     * 
     * @param path                 The path on which the middleware will be
     *                             registered.
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void delete(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_DELETE, middleware, additionalMiddleware);
    }

    /**
     * Creates a clone of current router and registers it on a path. The current
     * router is unchanged.
     * 
     * @param path The path on which the router should be registered.
     * @return The new router on which is registered on the provided path.
     */
    @Override
    public IPathRouter registerRouterOnPath(String path) {

        List<MiddlewareMetaData> copyofMiddlewares = new ArrayList<>();

        for (MiddlewareMetaData mmd : middlewares) {
            MiddlewareMetaData newMmd = mmd.copy();
            newMmd.addPrefixPath(path);
            copyofMiddlewares.add(newMmd);
        }

        return new PathRouter(copyofMiddlewares);
    }

    /**
     * Creates middleware metadata and adds it to the current list of middleware
     * metadata
     * 
     * @param path                 The path on which the middlewares should be
     *                             registered.
     * @param method               The http methods on which the middleware should
     *                             be registered.
     * @param middleware           The primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    private void addMiddlewares(String path, String method, Middleware middleware,
            Middleware... additionalMiddleware) {

        if (path == null) {
            /* Adds the first middleware to the list */
            MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, method);
            middlewares.add(currMiddlewareMeta);

            /* Adds the rest of middlewares to the list */
            for (Middleware addMiddlewareIterVal : additionalMiddleware) {
                currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, method);
                middlewares.add(currMiddlewareMeta);
            }
        } else {
            /* Adds the first middleware to the list with their path */
            MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, path, method);
            middlewares.add(currMiddlewareMeta);

            /* Adds the rest of middlewares to the list with their path */
            for (Middleware addMiddlewareIterVal : additionalMiddleware) {
                currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, path, method);
                middlewares.add(currMiddlewareMeta);
            }
        }
    }

    /**
     * Creates middleware metadata and adds it to the current list of middleware
     * metadata
     * 
     * @param method               The http methods on which the middleware should
     *                             be registered.
     * @param middleware           The primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    private void addMiddlewares(String method, Middleware middleware,
            Middleware... additionalMiddleware) {
        addMiddlewares(null, method, middleware, additionalMiddleware);
    }

    /**
     * Returns the list of all middlewareMetadatas registerd on the current router
     * 
     * @return List of middlewareMetadata
     */
    @Override
    public List<MiddlewareMetaData> getMiddlewareMetadataAsList() {
        return new ArrayList<>(middlewares);
    }

    /**
     * Registers a middleware metadata while also setting its path on it.
     */
    @Override
    public void use(String path, MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas) {
        middlewareMetaData.setPath(path);
        middlewares.add(middlewareMetaData);

        for (MiddlewareMetaData mmd : middlewareMetaDatas) {
            mmd.setPath(path);
            middlewares.add(mmd);
        }

    }

    /**
     * Registers a middleware metadata while also setting its path on it.
     */
    @Override
    public void use(MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas) {
        String path = "";
        middlewareMetaData.setPath(path);
        middlewares.add(middlewareMetaData);

        for (MiddlewareMetaData mmd : middlewareMetaDatas) {
            mmd.setPath(path);
            middlewares.add(mmd);
        }

    }

    /**
     * Registers middlewares on empty path which will only execute for HTTP get
     * methods.
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void get(Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(Methods.METHOD_GET, middleware, additionalMiddleware);
    }

    /**
     * Registers middlewares on empty path which will only execute for HTTP POST
     * methods.
     * 
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void post(Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(Methods.METHOD_POST, middleware, additionalMiddleware);

    }

    /**
     * Registers middlewares on a path which will only execute for HTTP PUT
     * methods.
     *
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void put(Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(Methods.METHOD_PUT, middleware, additionalMiddleware);
    }

    /**
     * Registers middlewares on a path which will only execute for HTTP DELETE
     * methods.
     * 
     * @param middleware           Primary middleware to register.
     * @param additionalMiddleware Optional. Additional middlewares to register.
     */
    @Override
    public void delete(Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(Methods.METHOD_DELETE, middleware, additionalMiddleware);

    }

    /**
     * Registers one middleware which will execute before the routers being
     * registerd.
     * 
     * All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param middleware The middleware to register
     * @param addRouters Optional. List of routers to registers with the current
     *                   middleware
     */
    @Override
    public void use(Middleware middleware, IPathRouter... additionalRouters) {
        addMiddlewares(null, Methods.METHOD_USE, middleware);
        for (IPathRouter currRouter : additionalRouters) {
            middlewares.addAll(currRouter.getMiddlewareMetadataAsList());
        }
    }

    /**
     * Registers one middleware which will execute before the routers being
     * registerd. All of these are registered on the a path.
     * 
     * All the middlewares registered on these routers will
     * execute on current router path.
     * 
     * @param middleware The middleware to register
     * @param addRouters Optional. List of routers to registers with the current
     *                   middleware
     */
    @Override
    public void use(String path, Middleware middleware, IPathRouter... addRouters) {
        addMiddlewares(path, Methods.METHOD_GET, middleware);
        for (IPathRouter currentRouter : addRouters) {
            IPathRouter copyOfCurrentRouter = currentRouter.registerRouterOnPath(path);
            this.middlewares.addAll(copyOfCurrentRouter.getMiddlewareMetadataAsList());
        }
    }
}
