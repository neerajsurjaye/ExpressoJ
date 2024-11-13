package com.spec.web.expresso.servlet;

import java.io.IOException;

import com.spec.web.expresso.Expresso;
import com.spec.web.expresso.callback.RequestCallback;
import com.spec.web.expresso.router.ExpressoRouter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHandlingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExpressoRouter expressoRouter = Expresso.getExpressoRouter();

        RequestCallback callback = expressoRouter.getHttpGetMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExpressoRouter expressoRouter = Expresso.getExpressoRouter();

        RequestCallback callback = expressoRouter.getHttpPostMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExpressoRouter expressoRouter = Expresso.getExpressoRouter();

        RequestCallback callback = expressoRouter.getHttpPostMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExpressoRouter expressoRouter = Expresso.getExpressoRouter();

        RequestCallback callback = expressoRouter.getHttpDeleteMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);
    }

    private void executeCallback(HttpServletRequest req, HttpServletResponse resp, RequestCallback callback)
            throws IOException {

        if (callback == null) {
            resp.setStatus(404);
            resp.getWriter().println("Path Not found");
            return;
        }

        callback.execute(req, resp);

    }

}
