package com.spec.web.expresso;

import com.spec.web.expresso.callback.RequestCallback;

/*
 * Provides the user facing methods.
 */
public interface Expresso {

    void get(String path, RequestCallback callback);

}
