package com.spec.web.expresso.message;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequest implements Request {

    HttpServletRequest req;

    HttpRequest(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public String payload() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payload'");
    }

    @Override
    public Object json() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'json'");
    }

}
