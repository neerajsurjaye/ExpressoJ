package com.spec.web.expresso.middleware;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

/**
 * The following class will be used to call the next callback on the callback
 * chain.
 */
public class MiddlewareExecutor {

    /** Holds all the middlewareMetadata */
    List<MiddlewareMetaData> middlewareList;

    /**
     * Constructs a instance of the class
     */
    public MiddlewareExecutor() {
        middlewareList = new LinkedList<>();
    }

    /**
     * Creates an instance with a single middleware.
     * 
     * @param middlewareMetadata the middlware function that needs to be called
     */
    public MiddlewareExecutor(MiddlewareMetaData middlewareMetadata) {
        middlewareList = new LinkedList<>();
        middlewareList.add(middlewareMetadata);
    }

    /**
     * Creates and instance with a list of middlewares
     * 
     * @param middlewaresMetadata List of middlewares to add
     */
    public MiddlewareExecutor(List<MiddlewareMetaData> middlewaresMetadata) {
        middlewareList = new LinkedList<>();
        middlewareList.addAll(middlewaresMetadata);
    }

    /**
     * The following method will execute request and response on the defined
     * middleware list.
     * 
     * @param req The Http request object
     * @param res The Http response object
     */
    public void execute(HttpRequest req, HttpResponse res, String path, String method) {

        MiddlewareFlowController mfc = new MiddlewareFlowController();

        Iterator<MiddlewareMetaData> iter = middlewareList.iterator();

        while (iter.hasNext() && mfc.isFlowAllowed()) {
            MiddlewareMetaData currMiddlewareMeta = iter.next();

            if (matchExactPath(path, method, currMiddlewareMeta)
                    || matchPathPattern(path, method, currMiddlewareMeta)) {
                mfc.reset();
                currMiddlewareMeta.getMiddleware().execute(req, res, mfc);
            }

        }

    }

    /** Matches the exact path with method on a middlewareMetada */
    private boolean matchExactPath(String path, String method, MiddlewareMetaData middlewareMetaData) {

        String middlewarePath = middlewareMetaData.getPath();
        String middlewareMethod = middlewareMetaData.getMethod();

        return (middlewarePath.equals(path) || middlewarePath.equals("")) &&
                (middlewareMethod.equals(method) || middlewareMethod.equals(Methods.METHOD_USE));

    }

    /**
     * Matches path pattern
     * 
     * TODO: fix method currently only matches //*
     * Currently runs on any method get , put etc
     * 
     * @param path
     * @param method
     * @param middlewareMetaData
     * @return
     */
    private boolean matchPathPattern(String path, String method, MiddlewareMetaData middlewareMetaData) {

        // if (path.endsWith("/*")) {
        // System.out.println("path ends with /*" + path);
        // path = path.substring(0, path.length() - 1);

        // /* TODO: add check for method */
        // if (middlewareMetaData.getPath().startsWith(path)) {
        // return true;
        // }

        String middlewarePath = middlewareMetaData.getPath();
        if (middlewarePath.endsWith("/*")) {
            System.out.println("path ends with /*" + middlewarePath);
            middlewarePath = middlewarePath.substring(0, middlewarePath.length() - 1);

            System.out.println("Middleware path " + middlewarePath + " url : " + path);
            if (path.startsWith(middlewarePath)) {
                return true;
            }
        }

        return false;
    }

}
