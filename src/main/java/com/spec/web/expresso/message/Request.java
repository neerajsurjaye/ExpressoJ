package com.spec.web.expresso.message;

import java.io.IOException;
import java.io.Reader;

import jakarta.servlet.http.Cookie;

/**
 * Represent a Http request
 */
public interface Request {

    /**
     * Returns the body(payload) of the Http request
     * 
     * @return The body of the request
     * @throws IOException May throw and IOException if it cannot read the body of
     *                     response.
     */
    String body() throws IOException;

    /**
     * Get the route parameter value against the given name.
     * 
     * @param name name of the parameter , this name should be same as the name of
     *             the parameter mentioned in the url pattern
     * 
     * @return value of the parameter
     */
    String getRouteParams(String name);

    /**
     * Return the query/search parameter value against the name
     * 
     * @param name name of the query parameter
     * 
     * @return value of the query parameter
     */
    String getUrlParams(String name);

    /**
     * Retrives the path of the current request url.
     * 
     * @return the path of the current request
     */
    String getRequestPath();

    /**
     * Returns the mime type of file passed to it.
     * 
     * @param fileName The file for which the mime type should be found out.
     * @return MIME type of file
     */
    String getMimeType(String fileName);

    /**
     * Returns list of cookies sent by the client
     * 
     * @return List of cookies
     */
    Cookie[] getCookies();

    /**
     * Returns a reader object to read request body.
     * 
     * @return Reader instance to read request body.
     * @throws IOException IOException
     */
    Reader getReader() throws IOException;

    /**
     * Returns the value of header for the specified header name.
     * 
     * @param headerName The name of the header.
     * @return Value of the header.
     */
    String getHeader(String headerName);

}
