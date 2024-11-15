package com.spec.web.expresso.message;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

public class HttpResponse implements Response {

    HttpServletResponse resp;

    HttpResponse(HttpServletResponse resp) {
        this.resp = resp;
    }

    @Override
    public HttpResponse setResponse(String response) {

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

    @Override
    public HttpResponse setStatusCode(Integer statusCode) {
        resp.setStatus(statusCode);
        return this;
    }

    @Override
    public HttpResponse setResponseHeaders(String name, String value) {
        resp.setHeader(name, value);
        return this;
    }

}
