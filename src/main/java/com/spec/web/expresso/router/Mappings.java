package com.spec.web.expresso.router;

import java.util.HashMap;

import com.spec.web.expresso.callback.RequestCallback;

public class Mappings {

    private HashMap<String, RequestCallback> pathMapping;

    Mappings() {
        pathMapping = new HashMap<>();
    }

    public void addPath(String path, RequestCallback reqCb) {
        pathMapping.put(path, reqCb);
    }

    public RequestCallback getCallbackOnPath(String path) {
        return pathMapping.get(path);
    }

}
