package com.spec.web.expresso.message;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.http.Cookie;

/**
 * Modifies the response that will be send to the client.
 */
public interface Response {

    /**
     * Sets the http response body
     * 
     * @param response the response body
     * @return returns the current class instance for method chaining.
     */
    Response writeResponse(String response);

    /**
     * Sets the status code of the response.
     * 
     * @param statusCode http staus code ex: 200, 404
     * @return returns the current class instance for method chaining.
     */
    Response setStatusCode(Integer statusCode);

    /**
     * Sets the response headers.
     * 
     * @param name  the name of the header
     * @param value the value of the header
     * @return returns the current class instance for method chaining.
     */
    Response setResponseHeader(String name, String value);

    /**
     * Sets the content type header
     * 
     * @param contentType the content type the message body is made of
     * @return returns the current class instance for method chaining.
     */
    Response setContentTypeHeader(String contentType);

    /**
     * Sends html response
     * 
     * @param html The html response to send
     * @return returns the current class instance for method chaining.
     */
    Response setHtml(String html);

    /**
     * Resets the response
     * 
     * @return returns the current class instance for method chaining.
     */
    Response resetResponse();

    /**
     * Resets the body of the response
     * 
     * @return returns the current class instance for method chaining.
     */
    Response resetResponseBody();

    /**
     * Returns the output stream from response servlet.
     * 
     * @return OutputStream
     * @throws IOException IOException
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * Closes the output stream. Returns true if successfull
     * 
     * @return Returns true if successfull.
     */
    boolean _closeOutputStream();

    /**
     * Sets a cookie that will be sent to the client.
     * 
     * @param cookie      Cookie instance
     * @param moreCookies More cookies :)
     */
    void setCookies(Cookie cookie, Cookie... moreCookies);
}
