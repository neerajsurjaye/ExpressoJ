package com.spec.web.expresso.message;

import java.io.IOException;

/**
 * Represent a Http request
 */
public interface Request {
    /*
     * Gives the payload as json
     */
    public <T> T json(Class<T> type) throws IllegalArgumentException, IOException;

    /**
     * Returns the body(payload) of the Http request
     * 
     * @return The body of the request
     */
    String body() throws IOException;


    String getParams(String name);

    String getQuery(String name);


}
