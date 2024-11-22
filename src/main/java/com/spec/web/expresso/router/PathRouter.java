package com.spec.web.expresso.router;

import java.util.ArrayList;
import java.util.List;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * The class will be used to route the requests to the required middlewares.
 * 
 * todo : Get post etc should not accept other routers
 * todo : Create a common method to add the middlewares to path with its method
 * then implement get post etc
 */
public class PathRouter implements IPathRouter {

    List<MiddlewareMetaData> middleWares;

    PathRouter() {
        middleWares = new ArrayList<>();
    }

    PathRouter(List<MiddlewareMetaData> middleWares) {
        this.middleWares = middleWares;
    }

    /** Appends a already constructed middleware metadata object to the list */
    @Override
    public void appendMiddlewareMetaData(MiddlewareMetaData middlewareMetaData) {
        middleWares.add(middlewareMetaData);
    }

    /** Adds the middlewares to the list of MiddlewareMetaData */
    @Override
    public void use(Middleware middleware, Middleware... additionalMiddleware) {

        /** Adds the first middleware to the list */
        MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, Methods.METHOD_USE);
        middleWares.add(currMiddlewareMeta);

        /** Adds the rest of middlewares to the list */
        for (Middleware addMiddlewareIterVal : additionalMiddleware) {
            currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, Methods.METHOD_USE);
            middleWares.add(currMiddlewareMeta);
        }

    }

    /** Adds the middlewares to the list of middlewares with a path */
    @Override
    public void use(String path, Middleware middleware, Middleware... additionalMiddleware) {

        /** Adds the first middleware to the list with their path */
        MiddlewareMetaData currMiddlewareMeta = new MiddlewareMetaData(middleware, path, Methods.METHOD_USE);
        middleWares.add(currMiddlewareMeta);

        /** Adds the rest of middlewares to the list with their path */
        for (Middleware addMiddlewareIterVal : additionalMiddleware) {
            currMiddlewareMeta = new MiddlewareMetaData(addMiddlewareIterVal, path, Methods.METHOD_USE);
            middleWares.add(currMiddlewareMeta);
        }

    }

    /** Adds the middlewares of the routers to the current list of middlewares */
    @Override
    public void use(IPathRouter router, IPathRouter... additionalRouters) {
        middleWares.addAll(router.getMiddlewaresAsList());

        for (IPathRouter currRouter : additionalRouters) {
            middleWares.addAll(currRouter.getMiddlewaresAsList());
        }

    }

    /**
     * Registers all the middlewares of the router to the new path then adds them to
     * the current router
     */
    @Override
    public void use(String path, IPathRouter router, IPathRouter... addRouters) {

        IPathRouter firstRouterCopy = router.registerRouterOnPath(path);
        this.middleWares.addAll(firstRouterCopy.getMiddlewaresAsList());

        for (IPathRouter currentRouter : addRouters) {
            IPathRouter copyOfCurrentRouter = currentRouter.registerRouterOnPath(path);
            this.middleWares.addAll(copyOfCurrentRouter.getMiddlewaresAsList());
        }

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
        return copyofPathRouter;
    }

    /**
     * Returns the list of middlewars.
     * 
     * Returns new Arraylist as we dont want to accidently modify the original
     * internal list.
     */
    @Override
    public List<MiddlewareMetaData> getMiddlewaresAsList() {
        return new ArrayList<>(middleWares);
    }

}
