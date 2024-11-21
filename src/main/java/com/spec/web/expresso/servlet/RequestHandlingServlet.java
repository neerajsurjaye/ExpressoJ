package com.spec.web.expresso.servlet;

import java.io.IOException;

import com.spec.web.expresso.Expresso;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.RequestCallback;
import com.spec.web.expresso.router.ExpressoRouter;

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

        RequestCallback callback = expressoRouter.getHttpPutMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExpressoRouter expressoRouter = Expresso.getExpressoRouter();

        RequestCallback callback = expressoRouter.getHttpDeleteMappings().getCallbackOnPath(req.getPathInfo());

        executeCallback(req, resp, callback);
    }

    /**
     * Common function to execute callback
     * 
     * @param req      The user request
     * @param resp     The user response
     * @param callback Callback function
     * @throws IOException
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
