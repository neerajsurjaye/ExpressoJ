package com.spec.web.expresso;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Expresso app = Expresso.init();

        /*
         * Basic text in response uses HttpServletRequst and HttpServletResponse in
         * parameters
         */
        app.get("/home", (req, res) -> {
            try {
                res.setContentType("text/html");
                res.getWriter().println("in home path :)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*
         * Sends json in response
         */
        app.get("/about", (req, res) -> {
            try {
                res.setContentType("application/json");
                res.getWriter().println("{\"inside\" : \"about\" , \"dataType\" : \"json\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        app.listen(8080);

    }
}
