package com.spec.web.expresso.callback;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;

/**
 * The following class will be used to call the next callback on the callback
 * chain.
 */
public class MiddlewareExecutor {

    Queue<Middleware> middlewareQueue;
    // The next caller

    /**
     * Constructs a instance of the class
     */
    public MiddlewareExecutor() {
        middlewareQueue = new LinkedList<>();
    }

    /**
     * Creates an instance with a single middleware.
     * 
     * @param middleware the middlware function that needs to be called
     */
    public MiddlewareExecutor(Middleware middleware) {
        middlewareQueue = new LinkedList<>();
        middlewareQueue.add(middleware);
    }

    /**
     * Creates and instance with a list of middlewares
     * 
     * @param middlewares List of middlewares to add
     */
    public MiddlewareExecutor(List<Middleware> middlewares) {
        middlewareQueue = new LinkedList<>();
        middlewareQueue.addAll(middlewares);
    }

    /**
     * The following method will execute request and response on the defined
     * middleware list.
     * 
     * @param req The Http request object
     * @param res The Http response object
     */
    public void execute(HttpRequest req, HttpResponse res) {

        MiddlewareFlowController mfc = new MiddlewareFlowController();

        while (mfc.isFlowAllowed() && !middlewareQueue.isEmpty()) {

            /**
             * Resets the flow controller. If flow controller state is set to true in the
             * iteration the loop will run again
             */
            mfc.reset();
            Middleware currentMiddleware = middlewareQueue.poll();
            currentMiddleware.execute(req, res, mfc);

        }
    }

}
