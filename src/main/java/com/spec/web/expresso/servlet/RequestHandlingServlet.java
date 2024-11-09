package com.spec.web.expresso.servlet;

import java.io.IOException;
import java.util.HashMap;

import com.spec.web.expresso.Expresso;
import com.spec.web.expresso.callback.RequestCallback;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHandlingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, RequestCallback> router = Expresso.getRouter();
        RequestCallback callback = router.get(req.getPathInfo());

        if (callback == null) {
            resp.setStatus(400);
            resp.getWriter().println("Path Not found");
            return;
        }

        callback.execute(req, resp);

    }

}
