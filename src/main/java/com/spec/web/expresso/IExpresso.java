package com.spec.web.expresso;

import com.spec.web.expresso.callback.RequestCallback;

/*
 * Provides the user facing methods.
 */
public interface IExpresso {

    void get(String path, RequestCallback callback);

    void listen(int port);

}
