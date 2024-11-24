package com.spec.web.expresso.message;

/**
 * Modifies the response that will be send to the client.
 */
public interface Response {

    /**
     * Sets the http response body
     * 
     * @param response the response body
     */
    HttpResponse writeResponse(String response);

    /**
     * Sets the status code of the response.
     * 
     * @param statusCode http staus code ex: 200, 404
     */
    HttpResponse setStatusCode(Integer statusCode);

    /**
     * Sets the response headers.
     * 
     * @param name  the name of the header
     * @param value the value of the header
     */
    HttpResponse setResponseHeader(String name, String value);

    /**
     * Sets the content type header
     * 
     * @param contentType the content type the message body is made of
     */
    HttpResponse setContentTypeHeader(String contentType);

}
