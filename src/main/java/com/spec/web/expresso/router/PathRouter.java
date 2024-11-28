package com.spec.web.expresso.router;

import java.util.ArrayList;
import java.util.List;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * The class will be used to route the requests to the required middlewares.
 * 
 * then implement get post etc
 */
public class PathRouter implements IPathRouter {

    protected List<MiddlewareMetaData> middleWares;

    public PathRouter() {
        middleWares = new ArrayList<>();
    }

    private PathRouter(List<MiddlewareMetaData> middleWares) {
        this.middleWares = middleWares;
    }

    /** Appends a already constructed middleware metadata object to the list */
    public void appendMiddlewareMetaData(MiddlewareMetaData middlewareMetaData) {
        middleWares.add(middlewareMetaData);
    }

    /** Registers a middleware with no path which will accept any http method. */
    @Override
    public void use(Middleware middleware, Middleware... additionalMiddleware) {

        addMiddlewares(null, Methods.METHOD_USE, middleware, additionalMiddleware);

    }

    /** Registers a middleware with a path which will accept any http method. */
    @Override
    public void use(String path, Middleware middleware, Middleware... additionalMiddleware) {

        addMiddlewares(path, Methods.METHOD_USE, middleware, additionalMiddleware);
    }

    /** Registers middlewares of a router to the current router */
    @Override
    public void use(IPathRouter router, IPathRouter... additionalRouters) {
        middleWares.addAll(router.getMiddlewareMetadataAsList());

        for (IPathRouter currRouter : additionalRouters) {
            middleWares.addAll(currRouter.getMiddlewareMetadataAsList());
        }

    }

    /**
     * Registers middlewares of a router to the current router with a path
     */
    @Override
    public void use(String path, IPathRouter router, IPathRouter... addRouters) {

        IPathRouter firstRouterCopy = router.registerRouterOnPath(path);
        this.middleWares.addAll(firstRouterCopy.getMiddlewareMetadataAsList());

        for (IPathRouter currentRouter : addRouters) {
            IPathRouter copyOfCurrentRouter = currentRouter.registerRouterOnPath(path);
            this.middleWares.addAll(copyOfCurrentRouter.getMiddlewareMetadataAsList());
        }

    }

    /** Registers middleware with HTTP get method */
    @Override
    public void get(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_GET, middleware, additionalMiddleware);
    }

    /** Registers middleware with HTTP post method */
    @Override
    public void post(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_POST, middleware, additionalMiddleware);
    }

    /** Registers middleware with HTTP put method */
    @Override
    public void put(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_PUT, middleware, additionalMiddleware);
    }

    /** Registers middleware with HTTP delete method */
    @Override
    public void delete(String path, Middleware middleware, Middleware... additionalMiddleware) {
        addMiddlewares(path, Methods.METHOD_DELETE, middleware, additionalMiddleware);
    }

    /**
     * Returns a new router where all the middlewares are registerd on the new path.
     * 
     * @param path
     * @return
     */
    @Override
    public IPathRouter registerRouterOnPath(String path) {

        List<MiddlewareMetaData> copyofMiddlewares = new ArrayList<>();

        for (MiddlewareMetaData mmd : middleWares) {
            MiddlewareMetaData newMmd = mmd.copy();
            newMmd.addPrefixPath(path);
            copyofMiddlewares.add(newMmd);
        }

        IPathRouter copyofPathRouter = new PathRouter(copyofMiddlewares);
        System.out.println("Registering router on path : " + copyofMiddlewares);
        return copyofPathRouter;
    }

    /**
     * Creates middleware metadata and adds it to the current list of middleware
     * metadata
     */
    private void addMiddlewares(String path, String method, Middleware middleware,
            Middleware... additionalMiddleware) {

        if (path == null) {
            /** Adds the first middleware to the list */
            MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, method);
            middleWares.add(currMiddlewareMeta);

            /** Adds the rest of middlewares to the list */
            for (Middleware addMiddlewareIterVal : additionalMiddleware) {
                currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, method);
                middleWares.add(currMiddlewareMeta);
            }
        } else {
            /** Adds the first middleware to the list with their path */
            MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, path, method);
            middleWares.add(currMiddlewareMeta);

            /** Adds the rest of middlewares to the list with their path */
            for (Middleware addMiddlewareIterVal : additionalMiddleware) {
                currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, path, method);
                middleWares.add(currMiddlewareMeta);
            }
        }
    }

    /**
     * Returns the list of middlewars.
     * 
     * Returns new Arraylist as we dont want to accidently modify the original
     * internal list.
     */
    @Override
    public List<MiddlewareMetaData> getMiddlewareMetadataAsList() {
        return new ArrayList<>(middleWares);
    }

    /**
     * Registers a middleware metadata
     * Todo: should register multiple middlewares
     */
    @Override
    public void use(String path, MiddlewareMetaData middlewareMetaData, MiddlewareMetaData... middlewareMetaDatas) {
        middlewareMetaData.setPath(path);
        middleWares.add(middlewareMetaData);

        for (MiddlewareMetaData mmd : middlewareMetaDatas) {
            mmd.setPath(path);
            middleWares.add(mmd);
        }

    }
}
