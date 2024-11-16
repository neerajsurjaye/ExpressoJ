package com.spec.web.expresso.message;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;


public class HttpRequest implements Request {

    HttpServletRequest req;

    private static final int BUFFER_SIZE = 1024;

    public HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public String payload() throws IOException {
            InputStream inputStream = req.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            byte[] requestBody = outputStream.toByteArray();
            // Get the character encoding from the request
            String encoding = req.getCharacterEncoding();
            System.err.println("encoding = " + encoding);
            if (encoding == null) {
                // If the request doesn't specify the encoding, use a default (e.g., UTF-8)
                encoding = "UTF-8";
            }
            // Convert the request body to a string using the specified encoding
            return new String(requestBody, encoding);
    }

    // usage MyCustomClass newObject = json(MyCustomClass.class);
    @Override
    public <T> T json(Class<T> type) throws Exception {
        String contentType = req.getContentType();
        
        if (!("application/json".equals(contentType))) {
            throw new Exception("Invalid content type");
        }

        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        T newObject = gson.fromJson(reader, type);

        return newObject;
    }

}
