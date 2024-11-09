package com.spec.web.expresso;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Expresso app = Expresso.init();

        app.get("/home", (req, res) -> {
            try {
                res.getWriter().println("in home path :)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        app.listen(8080);

    }
}
