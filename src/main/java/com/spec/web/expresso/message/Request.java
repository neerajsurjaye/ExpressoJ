package com.spec.web.expresso.message;

import java.io.IOException;

/*
 * Empty for now 
 */
public interface Request {

    /*
     * Gives the payload of the request
     */
    String payload() throws IOException;

    /*
     * Gives the payload as json
     */
    public <T> T json(Class<T> type) throws Exception;

}
