package com.spec.web.expresso.message;

import jakarta.servlet.http.HttpServletResponse;

public class HttpResponse implements Response {

    HttpServletResponse resp;

    HttpResponse(HttpServletResponse resp) {
        this.resp = resp;
    }

    @Override
    public HttpResponse setResponse(String response) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setResponse'");
    }

    @Override
    public HttpResponse setStatusCode(Integer statusCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatusCode'");
    }

    @Override
    public HttpResponse setResponseHeaders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setResponseHeaders'");
    }

}
