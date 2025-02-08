package com.spec.web.expresso.middleware;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param req    The Http request object
     * @param res    The Http response object
     * @param path   The path on which the exector is currently working.
     * @param method The http method on which the executor is currently working.
     */
    public void execute(HttpRequest req, HttpResponse res, String path, String method) {

        /**
         * The following ctx will be passed down to a middleware.
         * If executeNextMiddleware is called on it its shouldExecuteNextMiddleware
         * becmoes true.
         */
        MiddlewareContext ctx = new MiddlewareContext();

        Iterator<MiddlewareMetaData> iter = middlewareList.iterator();

        while (iter.hasNext() && ctx.isFlowAllowed()) {
            MiddlewareMetaData currMiddlewareMeta = iter.next();

            if (shouldMiddlewareExecute(path, method, currMiddlewareMeta)) {
                ctx.reset();
                req.setCurrentUrlPattern(currMiddlewareMeta.getPath());
                currMiddlewareMeta.getMiddleware().execute(req, res, ctx);
            }

        }

    }

    /** Matches the path with method on a middlewareMetada */
    private boolean shouldMiddlewareExecute(String path, String method, MiddlewareMetaData middlewareMetaData) {

        System.out.println("Should middleware execute Path : " + path + "method : " + method + "midddlwareMetadata : "
                + middlewareMetaData);

        return (matchPath(path, middlewareMetaData)) &&
                (matchMethod(method, middlewareMetaData));

    }

    private boolean matchPath(String path, MiddlewareMetaData middlewareMetaData) {
        String middlewarePath = middlewareMetaData.getPath();

        return middlewarePath.equals(path)
                || middlewarePath.equals("")
                || matchPathWithRouteParams(path, middlewareMetaData);
    }

    private boolean matchPathWithRouteParams(String path, MiddlewareMetaData middlewareMetaData) {

        String rawPath = middlewareMetaData.getPath();
        String regexPattern = rawPath.replaceAll(":(\\w+)", "(\\\\w+)");

        System.out.println("matchPathWithRouteParams : regexPattern : " + regexPattern);

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(path);

        System.out.println("matchPathWithRouteParams : matches : " + matcher.matches());

        return matcher.matches();

    }

    private boolean matchMethod(String method, MiddlewareMetaData middlewareMetaData) {
        String middlewareMethod = middlewareMetaData.getMethod();
        return middlewareMethod.equals(method) || middlewareMethod.equals(Methods.METHOD_USE);
    }

}
