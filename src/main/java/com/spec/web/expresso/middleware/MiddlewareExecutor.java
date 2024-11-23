package com.spec.web.expresso.middleware;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

/**
 * The following class will be used to call the next callback on the callback
 * chain.
 */
public class MiddlewareExecutor {

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

            String middlewarePath = currMiddlewareMeta.getPath();
            String middlewareMethod = currMiddlewareMeta.getMethod();

            if ((middlewarePath.equals(path) || middlewarePath.equals(""))
                    &&
                    (middlewareMethod.equals(method) || middlewareMethod.equals(Methods.METHOD_USE))) {
                mfc.reset();
                currMiddlewareMeta.getMiddleware().execute(req, res, mfc);
            }

        }

    }

}
