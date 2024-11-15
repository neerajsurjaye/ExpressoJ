package com.spec.web.expresso.message;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequest implements Request {

    HttpServletRequest req;

    HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public String payload() throws IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();

    }

}
