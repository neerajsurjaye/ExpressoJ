package com.spec.web.expresso.message;

import java.io.IOException;

/**
 * Represent a Http request
 */
public interface Request {
    /**
     * Gives the payload as json
     * 
     * @param <T>  The json response type
     * @param type The class type to deserialize to
     * @return The deserialized object
     * @throws IllegalArgumentException If payload is invalid
     * @throws IOException              If there is error reading the request body
     */
    public <T> T json(Class<T> type) throws IllegalArgumentException, IOException;

    /**
     * Returns the body(payload) of the Http request
     * 
     * @return The body of the request
     * @throws IOException May throw and IOException if it cannot read the body of
     *                     response.
     */
    String body() throws IOException;

    /* TODO: change its name */
    /**
     * Get the url parameter value against the given name.
     * 
     * @param name name of the parameter , this name should be same as the name of
     *             the parameter mentioned in the url pattern
     * 
     * @return value of the parameter
     */
    String getParams(String name);

    /**
     * Return the query parameter value against the name
     * 
     * @param name name of the query parameter
     * 
     * @return value of the query parameter
     */
    String getQuery(String name);

}
