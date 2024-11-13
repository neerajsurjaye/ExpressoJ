package com.spec.web.expresso.message;

/*
 * Empty for now 
 */
public interface Request {

    /*
     * Gives the payload of the request
     */
    String payload();

    /*
     * Gives the payload as json
     */
    Object json();

}
