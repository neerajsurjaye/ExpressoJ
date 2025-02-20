package com.spec.web.expresso.middleware.standard;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * Hosts static folder that is externl to the packaged JAR.
 */
public class StaticFileServer extends MiddlewareMetaData {

    /**
     * Create an instance of class with a staticFolderPath where this the path to
     * folder containing the static files.
     * 
     * @param staticFolderPath Path to the folder containing static files
     */
    public StaticFileServer(String staticFolderPath) {

        /* instantiates the parent class with StaticFileServerMiddleware. */
        super(new StaticFileServerMiddleware(staticFolderPath), Methods.METHOD_USE);
    }

    /**
     * The middleware needs to know on what path(URL path on web) it is registered.
     * 
     * The following methods passes the registered URL path to the middleware which
     * the middleware uses internally to compute the path to file..
     */
    @Override
    public void setPath(String pathString) {
        /** Sets the path in the middleware */
        StaticFileServerMiddleware middleware = this.getMiddleware();
        if (middleware != null)
            middleware.setHttpUrlPath(pathString);
        else
            throw new IllegalStateException("Middleware not initialized");

        /** Sets the path to itself */
        super.setPath(pathString);
    }

    /**
     * Returns the middleware registered on the following StaticFileServer.
     */
    @Override
    public StaticFileServerMiddleware getMiddleware() {
        if (super.getMiddleware() instanceof StaticFileServerMiddleware)
            return (StaticFileServerMiddleware) super.getMiddleware();
        throw new IllegalStateException("Middleware is not an isntance of StaticFileServerMiddleware");
    }

}
