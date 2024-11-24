package com.spec.web.expresso.message;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Wrapper class over HttpServletResponse.
 * 
 * Helps in handling http response
 */
public class HttpResponse implements Response {

    HttpServletResponse resp;

    /**
     * 
     * @param resp HttpServletResponse to wrap
     */
    public HttpResponse(HttpServletResponse resp) {
        this.resp = resp;
    }

    /**
     * Set the http response body
     * 
     * @param response The response send to the client.
     */
    @Override
    public HttpResponse writeResponse(String response) {

        try {
            resp.getWriter().write(response);
        } catch (IOException e) {
            try {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
            } catch (IOException e1) {
                // add logger
                e1.printStackTrace();
            }
        }

        return this;

    }

    /**
     * Set the Http status code of the response
     * 
     * @param statusCode The status code of the message
     */
    @Override
    public HttpResponse setStatusCode(Integer statusCode) {
        resp.setStatus(statusCode);
        return this;
    }

    /**
     * Sets the Http response headers
     * 
     * @param name the name of the header
     * @param vaue the value of the header
     */
    @Override
    public HttpResponse setResponseHeader(String name, String value) {
        resp.setHeader(name, value);
        return this;
    }

    /**
     * Sets the content type header
     * 
     * @param contentType the content type the message body is made of
     */
    @Override
    public HttpResponse setContentTypeHeader(String contentType) {
        this.setResponseHeader("Content-Type", contentType);
        return this;
    }

}
