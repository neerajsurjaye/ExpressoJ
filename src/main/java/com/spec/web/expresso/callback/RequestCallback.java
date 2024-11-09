package com.spec.web.expresso.callback;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

/*
 * The method method will be called by specific routes in the implementation of expresso interface.
 */
@FunctionalInterface
public interface RequestCallback {

    void execute(HttpRequest req, HttpResponse res);

}
