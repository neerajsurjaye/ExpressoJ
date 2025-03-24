package com.spec.web.expresso.message;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.spec.web.expresso.util.ExpressoOutputStream;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Wrapper class over HttpServletResponse.
 * 
 * Helps in handling http response
 */
@Slf4j
public class HttpResponse implements Response {

    HttpServletResponse resp;
    OutputStream responseOutputStream = null;

    /**
     * Constructs an instance of this class
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
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse writeResponse(String response) {
        try {
            responseOutputStream = this.getOutputStream();
            responseOutputStream.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ioE) {
            log.error("Error while writing response {}", ioE);
        }

        return this;
    }

    /**
     * Set the Http status code of the response
     * 
     * @param statusCode The status code of the message
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse setStatusCode(Integer statusCode) {
        resp.setStatus(statusCode);
        return this;
    }

    /**
     * Sets the Http response headers
     * 
     * @param name  the name of the header
     * @param value the value of the header
     * @return returns the current class instance for method chaining.
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
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse setContentTypeHeader(String contentType) {
        this.setResponseHeader("Content-Type", contentType);
        return this;
    }

    /**
     * Sends html
     * 
     * Note: Resets any data present in response object
     * 
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse setHtml(String html) {

        this.resetResponseBody();
        this.setContentTypeHeader("text/html");
        this.writeResponse(html);
        return this;
    }

    /**
     * Resets the response object
     * 
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse resetResponse() {
        this.resp.reset();
        return this;
    }

    /**
     * resturns the raw http servlet response
     * 
     * @return current http servlet response
     */
    public HttpServletResponse getRawHttpServletResponse() {
        return this.resp;
    }

    /**
     * Resets the body of the response
     * 
     * @return returns the current class instance for method chaining.
     */
    @Override
    public HttpResponse resetResponseBody() {
        this.resp.resetBuffer();
        return this;
    }

    /**
     * Returns the httpservletresponse used internally by Expresso.
     * 
     * @return HttpServletResponse
     */
    public HttpServletResponse getHttpServletResponse() {
        return resp;
    }

    /**
     * Returns the output stream from response servlet.
     * 
     * @return OutputStream
     * @throws IOException
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        OutputStream out = resp.getOutputStream();
        return new ExpressoOutputStream(out);
    }

    /**
     * Closes the output stream. Returns true if successfull
     */
    @Override
    public boolean _closeOutputStream() {
        if (this.responseOutputStream != null) {
            try {
                ExpressoOutputStream outStream = (ExpressoOutputStream) responseOutputStream;
                outStream._closeFlush();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets a cookie that will be sent to the client.
     * 
     * @param key
     * @param value
     */
    @Override
    public void setCookies(Cookie cookie, Cookie... moreCookies) {
        resp.addCookie(cookie);

        for (Cookie aCookie : moreCookies) {
            resp.addCookie(aCookie);
        }

    }

}
