package com.spec.web.expresso.middleware;

/**
 * Represents the metadata for a middleware.
 */
public class MiddlewareMetaData {

    /** The middleware that will be encapsulated by this class */
    private Middleware middleware;

    /**
     * The path of the middleware. One of the condition of middleware execution the
     * path should match.
     */
    private String path = "";

    /**
     * Will be used to build the path
     */
    private StringBuilder pathBuilder;

    /**
     * The method on which the middleware is registered on.
     * 
     * Generally the method will be HTTP method but is palced as generic as it
     * supports custom methods like 'USE'.
     * 
     * Will be set to "USE" to execute on all methods.
     */
    private String method;

    /**
     * Returns the method on which the middleware is registerd on.
     * 
     * @return Method name
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns the middleware which is registerd on it.
     * 
     * @return Instance of middleware.
     */
    public Middleware getMiddleware() {
        return middleware;
    }

    /**
     * Instantaiats the class
     * 
     * @param middleware The middleware on which the metadata should be created.
     * @param method     The method on which the middleware should be registered.
     */
    public MiddlewareMetaData(Middleware middleware, String method) {
        this.middleware = middleware;
        this.method = method;
        this.pathBuilder = new StringBuilder();
    }

    /**
     * Instantaiats the class
     * 
     * @param middleware The middleware on which the metadata should be created.
     * @param path       The path on which the middleware should be registered.
     * @param method     The method on which the middleware should be registered.
     */
    public MiddlewareMetaData(Middleware middleware, String path, String method) {
        this.middleware = middleware;
        this.method = method;
        this.pathBuilder = new StringBuilder(path);
        calcPath();
    }

    /* Instantaiats the class */
    private MiddlewareMetaData(Middleware middleware, String path, StringBuilder pathBuilder, String method) {
        this.middleware = middleware;
        this.pathBuilder = new StringBuilder(pathBuilder);
        this.path = path;
        this.method = method;
        calcPath();
    }

    /**
     * Sets a path for the current middleware
     * 
     * @param path the path which should be appended to the current path.
     */
    public void appendPath(String path) {
        this.pathBuilder.append(path);
        calcPath();
    }

    /**
     * Prepends a path to the current path
     * 
     * @param prefixPath the path which should be prepended.
     */
    public void addPrefixPath(String prefixPath) {
        this.pathBuilder.insert(0, prefixPath);
        calcPath();
    }

    /**
     * Sets the method on which the middleware should execute
     * 
     * @param method the HTTP method on which the metadata should be registered.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /** Sets the path that has been calculated by the string builder */
    public void calcPath() {
        this.path = pathBuilder.toString();
    }

    /**
     * Creates a copy of the current MiddewareMetaData instance
     * 
     * @return the copy of the current class
     */
    public MiddlewareMetaData copy() {
        return new MiddlewareMetaData(this.middleware, this.path, new StringBuilder(this.pathBuilder), this.method);
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

    /**
     * Returns the path on which the middleware metadata is registered on.
     * 
     * @return path.
     */
    public String getPath() {
        return path;
    }

}
