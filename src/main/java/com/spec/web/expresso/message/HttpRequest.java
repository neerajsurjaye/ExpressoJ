package com.spec.web.expresso.message;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

/*
 * Wrapper class over HttpServletRequest. 
 * Helps in handling of Http request
 */
public class HttpRequest implements Request {

    HttpServletRequest req;

    /**
     * 
     * @param req HttpServlet to wrap
     */
    HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    /**
     * Returns the Http body of the HttpRequest in String format
     * 
     * @return payload in string format
     */
    @Override
    public String body() throws IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();

    }

}
