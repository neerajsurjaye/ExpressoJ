package com.spec.web.expresso.middleware.standard;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * Hosts the static files contained in /resrouces folder.
 */
public class StaticResourceServer extends MiddlewareMetaData {
    /**
     * Create an instance of class with a staticResourcePath
     * 
     * @param staticResourcePath Path to the folder containing static files
     */
    public StaticResourceServer(String staticResourcePath) {

        /* instantiates the parent class with StaticResourceServerMiddleware. */
        super(new StaticResourceServerMiddleware(staticResourcePath), Methods.METHOD_USE);
    }

    /**
     * The middleware needs to know on what path(URL path on web) it is registered.
     * The following method passes the registered url path to the middleware.
     */
    @Override
    public void setPath(String pathString) {
        /** Sets the path in the middleware */
        this.getMiddleware().setHttpUrlPath(pathString);
        /** Sets the path to itself */
        super.setPath(pathString);
    }

    /**
     * Returns the middleware registered on the following StaticResourceServer.
     */
    @Override
    public StaticResourceServerMiddleware getMiddleware() {
        return (StaticResourceServerMiddleware) super.getMiddleware();
    }

}
