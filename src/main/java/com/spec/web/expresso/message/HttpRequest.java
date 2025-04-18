package com.spec.web.expresso.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import com.spec.web.expresso.util.URLParser;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Wrapper class over HttpServletRequest.
 * Helps in handling of Http request.
 */
public class HttpRequest implements Request {

    HttpServletRequest req;
    String currentUrlPattern = null;

    /**
     * Constructs an instance of HttpRequest wrapping an HttpServletRequest.
     * 
     * @param req HttpServlet to wrap
     */
    public HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    /**
     * Returns the Http body of the HttpRequest as String.
     * 
     * @return payload in string format
     */
    @Override
    public String body() throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader bfr = req.getReader();
        String line;

        while ((line = bfr.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();

    }

    /**
     * Get the route parameter value against the given name
     * 
     * @param name name of the route parameter , this name should be same as the
     *             name of
     *             the parameter mentioned in the url pattern. Ex: /user/:id so
     *             parameter name will be 'id'.
     * 
     * @return value of the parameter
     */
    @Override
    public String getRouteParams(String name) {
        String currentPath = req.getPathInfo();

        Map<String, String> urlParameter = URLParser.getRouteParameters(this.currentUrlPattern, currentPath);
        return urlParameter.get(name) != null ? urlParameter.get(name) : "";
    }

    /**
     * Return the query parameter value against the name
     * 
     * @param name name of the query parameter
     * 
     * @return value of the query parameter
     */
    @Override
    public String getUrlParams(String name) {
        return req.getParameter(name);
    }

    /**
     * Sets the current url pattern
     * 
     * @param currentUrlPattern Url pattern on which the current middlware is
     *                          executing on.
     */
    public void setCurrentUrlPattern(String currentUrlPattern) {
        this.currentUrlPattern = currentUrlPattern;
    }

    /**
     * Return http servlet request which is used internally by Expresso.
     * 
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpServletRequest() {
        return req;
    }

    /**
     * Retrives the path of the current request url.
     * 
     * @return the path of the current request
     */
    @Override
    public String getRequestPath() {
        return req.getRequestURI();
    }

    /**
     * Returns the mime type of file name passed to it.
     * 
     * @return MIME type of the file
     */
    @Override
    public String getMimeType(String fileName) {
        return req.getServletContext().getMimeType(fileName);
    }

    /**
     * Returns list of cookies sent by the client
     * 
     * @return List of cookies
     */
    @Override
    public Cookie[] getCookies() {
        Cookie[] resp = req.getCookies();
        if (resp == null) {
            resp = new Cookie[0];
        }
        return resp;
    }

    /**
     * Returns a reader object to read request body.
     * 
     * @return Reader instance to read request body.
     */
    @Override
    public Reader getReader() throws IOException {
        return req.getReader();
    }

    /**
     * Returns the value of header for the specified header name.
     * 
     * @param headerName The name of the header.
     * @return Value of the header.
     */
    @Override
    public String getHeader(String headerName) {
        return req.getHeader(headerName);
    }

}
