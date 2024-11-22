package com.spec.web.expresso.router;

import java.util.List;

import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

public interface IPathRouter {

    void appendMiddlewareMetaData(MiddlewareMetaData middlewareMetaData);

    void use(Middleware middleware, Middleware... additionalMiddleware);

    void use(String path, Middleware middleware, Middleware... additionalMiddleware);

    void use(IPathRouter router, IPathRouter... addRouters);

    void use(String path, IPathRouter router, IPathRouter... addRouters);

    IPathRouter registerRouterOnPath(String path);

    List<MiddlewareMetaData> getMiddlewaresAsList();

}
