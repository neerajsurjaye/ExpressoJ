package com.spec.web.expresso.callback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * The method method will be called by specific routes in the implementation of expresso interface.
 */
@FunctionalInterface
public interface RequestCallback {

    void execute(HttpServletRequest req, HttpServletResponse res);

}
