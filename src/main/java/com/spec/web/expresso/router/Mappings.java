package com.spec.web.expresso.router;

import java.util.HashMap;

import com.spec.web.expresso.middleware.RequestCallback;

/**
 * The class maps url paths to user defined functions.
 * 
 * @deprecated replaced by path router
 */
@Deprecated
public class Mappings {

    /** Stores the mappings between paths and their functions */
    private HashMap<String, RequestCallback> pathMapping;

    /**
     * Instantiates the class.
     */
    Mappings() {
        pathMapping = new HashMap<>();
    }

    /**
     * Maps a path to a user defined callback function.
     * 
     * @param path  represnts the route
     * @param reqCb user defined callback function
     */
    public void addPath(String path, RequestCallback reqCb) {
        pathMapping.put(path, reqCb);
    }

    /**
     * Return the callback on the given route
     * 
     * @param path retprents the route
     * @return returns the user defined callback.
     */
    public RequestCallback getCallbackOnPath(String path) {
        return pathMapping.get(path);
    }

}
