package com.spec.web.expresso.message;

/*
 * Modifies the response that will be send to the client.
 */
public interface Response {

    /*
     * Sets the reponse body that will be sent back to client.
     */
    HttpResponse setResponse(String response);

    /*
     * Sets the status code of the response.
     */
    HttpResponse setStatusCode(Integer statusCode);

    /*
     * Sets the response headers.
     */
    HttpResponse setResponseHeaders();

}
