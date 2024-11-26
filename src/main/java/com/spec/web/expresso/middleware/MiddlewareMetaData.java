package com.spec.web.expresso.middleware;

import lombok.Getter;

public class MiddlewareMetaData {

    /** The middleware that will be encapsulated by this class */
    @Getter
    private Middleware middleware;

    /**
     * The path of the middleware. One of the condition of middleware execution the
     * path should match.
     */
    @Getter
    private String path = "";

    /**
     * Will be used to build the path
     */
    private StringBuilder pathBuilder;

    /**
     * The method on which the middleware is registered on.
     * 
     * Generally the method will be HTTP method but is palced as generic as it may
     * support more features.
     * 
     * Will be set to "use" to execute on all methods.
     */
    @Getter
    private String method;

    /** Instantaiats the class */
    public MiddlewareMetaData(Middleware middleware, String method) {
        this.middleware = middleware;
        this.method = method;
        this.pathBuilder = new StringBuilder();

    }

    /** Instantaiats the class */
    public MiddlewareMetaData(Middleware middleware, String path, String method) {
        this.middleware = middleware;
        this.method = method;
        this.pathBuilder = new StringBuilder(path);
        calcPath();
    }

    /** Instantaiats the class */
    private MiddlewareMetaData(Middleware middleware, String path, StringBuilder pathBuilder, String method) {
        this.middleware = middleware;
        this.pathBuilder = new StringBuilder(pathBuilder);
        this.method = method;
        calcPath();
    }

    /** Sets a path for the current middleware */
    public void appendPath(String path) {
        this.pathBuilder.append(path);
        calcPath();
    }

    /** Prepends a path to the current path */
    public void addPrefixPath(String prefixPath) {
        this.pathBuilder.insert(0, prefixPath);
        calcPath();
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

    @Override
    public String toString() {
        return String.format("{Middleware: %s, path: %s, method: %s}%n", middleware, path, method);
    }

    /** Sets the path */
    public void setPath(String pathString) {
        this.pathBuilder = new StringBuilder(pathBuilder);
        this.path = pathString;
    }

}
