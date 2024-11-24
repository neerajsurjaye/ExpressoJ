package com.spec.web.expresso.servlet;

import java.io.IOException;

import com.spec.web.expresso.Expresso;
import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.MiddlewareExecutor;
import com.spec.web.expresso.middleware.RequestCallback;
import com.spec.web.expresso.router.PathRouter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet to handle HTTP request and route them to appropriate callback
 * function.
 */
// todo: handle executecallback exceptions
public class RequestHandlingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeMiddlwares(req, resp, Methods.METHOD_GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeMiddlwares(req, resp, Methods.METHOD_POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeMiddlwares(req, resp, Methods.METHOD_PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeMiddlwares(req, resp, Methods.METHOD_DELETE);
    }

    /**
     * Wraps the servlet req and resp object. Then execuets the middlewares based on
     * path and the http method.
     * 
     * @param req
     * @param resp
     * @param method
     */
    private void executeMiddlwares(HttpServletRequest req, HttpServletResponse resp, String method) {
        PathRouter router = Expresso.getExpressoObj();
        MiddlewareExecutor executor = new MiddlewareExecutor(router.getMiddlewareMetadataAsList());

        HttpRequest request = new HttpRequest(req);
        HttpResponse response = new HttpResponse(resp);
        String path = req.getPathInfo();

        executor.execute(request, response, path, method);
    }

    /**
     * Common function to execute callback
     * 
     * @param req      The user request
     * @param resp     The user response
     * @param callback Callback function
     * @throws IOException
     * 
     * @Deprecated
     */
    private void executeCallback(HttpServletRequest req, HttpServletResponse resp, RequestCallback callback)
            throws IOException {

        if (callback == null) {
            resp.setStatus(404);
            resp.getWriter().println("Path Not found");
            return;
        }

        HttpRequest request = new HttpRequest(req);
        HttpResponse response = new HttpResponse(resp);

        callback.execute(request, response);

    }

}
