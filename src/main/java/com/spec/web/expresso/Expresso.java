package com.spec.web.expresso;

import java.net.HttpURLConnection;
import java.util.HashMap;

import com.spec.web.expresso.callback.RequestCallback;
import com.spec.web.expresso.router.ExpressoRouter;
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

    // @Getter
    // private static HashMap<String, RequestCallback> router
    @Getter
    private static ExpressoRouter expressoRouter = null;

    private Expresso() {

    }

    public static Expresso init() {

        // router = new HashMap<>();
        expressoRouter = new ExpressoRouter();

        // todo: Understand what exactly is happening here
        DeploymentInfo serverBuilder = Servlets.deployment()
                .setClassLoader(Expresso.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("test.war")
                .addServlet(
                        Servlets.servlet("MyServlet", RequestHandlingServlet.class).addMapping("/*"));

        manager = Servlets.defaultContainer().addDeployment(serverBuilder);
        manager.deploy();

        if (expressoObj != null)
            return expressoObj;
        expressoObj = new Expresso();
        return expressoObj;
    }

    @Override
    public void get(String path, RequestCallback callback) {
        expressoRouter.getHttpGetMappings().addPath(path, callback);
    }

    @Override
    public void post(String path, RequestCallback callback) {
        expressoRouter.getHttpPostMappings().addPath(path, callback);
    }

    @Override
    public void put(String path, RequestCallback callback) {
        expressoRouter.getHttpPutMappings().addPath(path, callback);
    }

    @Override
    public void delete(String path, RequestCallback callback) {
        expressoRouter.getHttpDeleteMappings().addPath(path, callback);
    }

    @Override
    public void listen(int port) {
        try {
            server = Undertow.builder()
                    .addHttpListener(port, "0.0.0.0")
                    .setHandler(manager.start())
                    .build();
            server.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
