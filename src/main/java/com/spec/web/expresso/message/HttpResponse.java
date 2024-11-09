package com.spec.web.expresso.message;

/*
 * Modifies the response that will be send to the client.
 */
public interface HttpResponse {

    /*
     * Sets the reponse body that will be sent back to client.
     */
    void setResponse(String response);

    /*
     * Sets the status code of the response.
     */
    void setStatusCode(Integer statusCode);

    /*
     * Sets the response headers.
     */
    void setResponseHeaders();

}
