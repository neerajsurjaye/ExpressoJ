package com.spec.web.expresso.message;

import java.io.IOException;

/**
 * Represent a Http request
 */
public interface Request {

    /**
     * Returns the body(payload) of the Http request
     * 
     * @return The body of the request
     */
    String body() throws IOException;

}
