package com.spec.web.expresso;

import java.util.HashMap;

import com.spec.web.expresso.callback.RequestCallback;
import com.spec.web.expresso.servlet.RequestHandlingServlet;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import lombok.Getter;

//todo: write proper comments
public class Expresso implements IExpresso {

    @Getter
    private static Expresso expressoObj = null;

    private static Undertow server = null;
    private static DeploymentManager manager = null;

    @Getter
    private static HashMap<String, RequestCallback> router;

    private Expresso() {

        router = new HashMap<>();

        // todo: Understand what exactly is happening here
        DeploymentInfo serverBuilder = Servlets.deployment()
                .setClassLoader(Expresso.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("test.war")
                .addServlet(
                        Servlets.servlet("MyServlet", RequestHandlingServlet.class).addMapping("/*"));

        manager = Servlets.defaultContainer().addDeployment(serverBuilder);
        manager.deploy();

    }

    public static Expresso init() {
        if (expressoObj != null)
            return expressoObj;
        expressoObj = new Expresso();
        return expressoObj;
    }

    @Override
    public void get(String path, RequestCallback callback) {
        router.put(path, callback);
    }

    @Override
    public void listen(int port) {
        try {
            server = Undertow.builder()
                    .addHttpListener(port, "0.0.0.0")
                    .setHandler(manager.start())
                    .build();
            server.start();
            System.err.println("Server running on port " + port);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
