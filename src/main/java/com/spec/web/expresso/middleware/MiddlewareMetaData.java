package com.spec.web.expresso.middleware;

public class MiddlewareMetaData {

    /** The middleware that will be encapsulated by this class */
    Middleware middleware;

    /**
     * The path of the middleware. One of the condition of middleware execution the
     * path should match.
     */
    String path;

    /**
     * Will be used to build the path
     */
    StringBuilder pathBuilder;

    /**
     * The method on which the middleware is registered on.
     * 
     * Generally the method will be HTTP method but is palced as generic as it may
     * support more features.
     * 
     * Will be set to "use" to execute on all methods.
     */
    String method;

    /** Instantaiats the class */
    public MiddlewareMetaData(Middleware middleware, String method) {
        this.middleware = middleware;
        this.method = method;
    }

    /** Instantaiats the class */
    public MiddlewareMetaData(Middleware middleware, String path, String method) {
        this.middleware = middleware;
        this.path = path;
        this.method = method;
    }

    private MiddlewareMetaData(Middleware middleware, String path, StringBuilder pathBuilder, String method) {
        this.middleware = middleware;
        this.path = path;
        this.pathBuilder = new StringBuilder(pathBuilder);
        this.method = method;
    }

    /** Sets a path for the current middleware */
    public void addPath(String path) {
        this.pathBuilder = new StringBuilder();
        this.pathBuilder.append(path);
    }

    /** Prepends a path to the current path */
    public void addPrefixPath(String prefixPath) {

        if (this.pathBuilder == null) {
            this.addPath(prefixPath);
            return;
        }

        this.pathBuilder.insert(0, prefixPath);
    }

    /** Sets the method on which the middleware should execute */
    public void setMethod(String method) {
        this.method = method;
    }

    /** Sets the path that has been calculated by the string builder */
    public void calcPath() {
        this.path = pathBuilder.toString();
    }

    /** Creates a copy of the current MiddewareMetaData instance */
    public MiddlewareMetaData copy() {
        return new MiddlewareMetaData(this.middleware, this.path, this.pathBuilder, this.method);
    }

}
