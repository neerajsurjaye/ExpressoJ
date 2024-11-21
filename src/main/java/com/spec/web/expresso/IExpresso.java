package com.spec.web.expresso;

import com.spec.web.expresso.middleware.RequestCallback;

/*
 * Provides the user facing methods.
 */
public interface IExpresso {

    /**
     * Creates get mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    void get(String path, RequestCallback callback);

    /**
     * Creates post mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    void post(String path, RequestCallback callback);

    /**
     * Creates put mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    void put(String path, RequestCallback callback);

    /**
     * Creates delete mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    void delete(String path, RequestCallback callback);

    /**
     * Starts the server over a network port.
     * 
     * @param port the port number on which the class should listen.
     */
    void listen(int port);

}
