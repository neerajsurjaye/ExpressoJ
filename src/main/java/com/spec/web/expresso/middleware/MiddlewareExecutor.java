package com.spec.web.expresso.middleware;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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

    private static final ConcurrentHashMap<String, Pattern> dynamicPathPatternCache = new ConcurrentHashMap<>();

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
                /*
                 * Resets context if current middleware doesn't call executeNextMiddleware().
                 * Execution will stop.
                 */
                ctx.reset();
                ctx.markMiddlewareExecuted();

                /*
                 * The path passed is used for extracting url parameters.
                 */
                req.setCurrentUrlPattern(currMiddlewareMeta.getPath());
                currMiddlewareMeta.getMiddleware().execute(req, res, ctx);
            }

        }

        /*
         * If no middleware is executed returns a 404 error message.
         */
        if (!ctx.wasMiddlewareExecutedOnCurrentPath()) {
            res.setStatusCode(404).writeResponse("404 : No method to execute on this path");
        }

        res.closeOutputStream();

    }

    /* Matches the path with method on a middlewareMetada. If match returns true */
    private boolean shouldMiddlewareExecute(String path, String method, MiddlewareMetaData middlewareMetaData) {

        return (matchPath(path, middlewareMetaData)) &&
                (matchMethod(method, middlewareMetaData));

    }

    /*
     * Checks if the middleware should be executed on current path or not.
     * 
     * On below conditions returns true.
     * 
     * 1. If path matches
     * 2. if middleware path is set to empty string. It implies that middleware
     * should execute on every request.
     * 3. Mathces paths with route params ex: /user/:uid/comment/:cid
     * 4. Matches dynamic paths ex: /user/*
     * 
     */
    private boolean matchPath(String path, MiddlewareMetaData middlewareMetaData) {
        String middlewarePath = middlewareMetaData.getPath();

        return middlewarePath.equals(path)
                || middlewarePath.equals("")
                || matchPathWithRouteParams(path, middlewareMetaData)
                || matchWildcardPath(path, middlewareMetaData);
    }

    /**
     * Matches paths with route paarmeter.
     * 
     * @param path
     * @param middlewareMetaData
     * @return Returns true if path matches.
     */
    private boolean matchPathWithRouteParams(String path, MiddlewareMetaData middlewareMetaData) {

        String rawPath = middlewareMetaData.getPath();

        /*
         * Replaces :id with \\w+. Thus making a regex url pattern.
         * Thus user/:id will match user/123
         */
        dynamicPathPatternCache.computeIfAbsent(rawPath, k -> {
            String regexPattern = k.replaceAll(":(\\w+)", "(\\\\w+)");
            return Pattern.compile(regexPattern);
        });

        Pattern pattern = dynamicPathPatternCache.get(rawPath);
        Matcher matcher = pattern.matcher(path);

        return matcher.matches();

    }

    /**
     * Checks if the path is a wildcard path
     * 
     * ex wildcard path /user/order/*
     * '*' Can be replaced by any path after it
     * 
     * @param path Path on which the middleware is set.
     * @return Checks if paths match.
     */
    private boolean matchWildcardPath(String path, MiddlewareMetaData middlewareMetaData) {
        String rawPath = middlewareMetaData.getPath();

        if (rawPath.lastIndexOf('*') != rawPath.length() - 1) {
            return false;
        }

        String pathWithoutWildCard = rawPath.substring(0, rawPath.length() - 1);

        return path.indexOf(pathWithoutWildCard) == 0;

    }

    /**
     * Matches the HTTP method on which the request is called on.
     * 'USE' matches all HTTP methods.
     * 
     * @param method
     * @param middlewareMetaData
     * @return returns true if method matches
     */
    private boolean matchMethod(String method, MiddlewareMetaData middlewareMetaData) {
        String middlewareMethod = middlewareMetaData.getMethod();
        return middlewareMethod.equals(method) || middlewareMethod.equals(Methods.METHOD_USE);
    }

}
