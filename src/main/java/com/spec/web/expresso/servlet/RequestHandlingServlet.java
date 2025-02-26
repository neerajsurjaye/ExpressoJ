package com.spec.web.expresso.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spec.web.expresso.Expresso;
import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.MiddlewareExecutor;
import com.spec.web.expresso.router.PathRouter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet to handle HTTP request and route them to appropriate callback
 * function.
 */
public class RequestHandlingServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(RequestHandlingServlet.class);

    /**
     * Default constructor. Instantiates the class and then delegates to parent
     * constructor.
     */
    public RequestHandlingServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        executeMiddlewares(req, resp, Methods.METHOD_GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        executeMiddlewares(req, resp, Methods.METHOD_POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        executeMiddlewares(req, resp, Methods.METHOD_PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        executeMiddlewares(req, resp, Methods.METHOD_DELETE);
    }

    /**
     * Wraps the servlet req and resp object. Then execuets the middlewares based on
     * path and the http method.
     * 
     * @param req
     * @param resp
     * @param method
     */
    private void executeMiddlewares(HttpServletRequest req, HttpServletResponse resp, String method) {
        PathRouter router = Expresso.getExpressoObj();
        MiddlewareExecutor executor = new MiddlewareExecutor(router.getMiddlewareMetadataAsList());

        HttpRequest request = new HttpRequest(req);
        HttpResponse response = new HttpResponse(resp);
        String path = req.getPathInfo();

        logger.info("{} request on path : \"{}\"", method, path);

        executor.execute(request, response, path, method);
    }

}
