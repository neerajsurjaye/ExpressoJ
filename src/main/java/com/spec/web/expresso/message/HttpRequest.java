package com.spec.web.expresso.message;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

/*
 * Wrapper class over HttpServletRequest. 
 * Helps in handling of Http request
 */
public class HttpRequest implements Request {

    HttpServletRequest req;

    private static final int BUFFER_SIZE = 1024;

    /**
     * 
     * @param req HttpServlet to wrap
     */
    public HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    /**
     * Returns the Http body of the HttpRequest in String format
     * 
     * @return payload in string format
     */
    @Override
    public String body() throws IOException {
        InputStream inputStream = req.getInputStream();
        if (inputStream == null)
            return "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        byte[] requestBody = outputStream.toByteArray();
        // Get the character encoding from the request
        String encoding = req.getCharacterEncoding();
        if (encoding == null) {
            // If the request doesn't specify the encoding, use a default (e.g., UTF-8)
            encoding = "UTF-8";
        }
        // Convert the request body to a string using the specified encoding
        return new String(requestBody, encoding);
    }

    /**
     * Deserializes a JSON string to an instance of specified class
     * 
     * pass the Class.class to the following function.
     * Ex: json(Myclass.class)
     * 
     * it will the object of Myclass
     * 
     * @throws IOException
     */
    @Override
    public <T> T json(Class<T> type) throws IllegalArgumentException, IOException {
        String contentType = req.getContentType();

        // todo: in future .err should handle this
        if (!("application/json".equals(contentType))) {
            throw new IllegalArgumentException("Invalid content type");
        }

        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        return gson.fromJson(reader, type);

    }

    /**
     * Retrives the path of the current request url.
     * 
     * @return the path of the current request
     */
    /** Todo: change name of the method */
    @Override
    public String getRequestPath() {
        return req.getPathInfo();
    }

}
