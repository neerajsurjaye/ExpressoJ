package com.spec.web.expresso;

import com.spec.web.expresso.router.PathRouter;
import com.spec.web.expresso.servlet.RequestHandlingServlet;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import lombok.Getter;

/**
 * The class the user will instantiate to work with the following framework.
 */
public class Expresso extends PathRouter {

    /** The single object that will exist for the whole program. */
    @Getter
    private static Expresso expressoObj = null;

    /** The http server */
    private Undertow server = null;
    /** used by undertow */
    private static DeploymentManager manager = null;

    /** The network port on which the server should run on */
    private int port = 8080;

    /** Private constructor for singleton pattern */
    private Expresso() {

    }

    /**
     * Instantiates the expresso framework.
     * 
     * If instance does not exist. It creates one and returns it.
     * 
     * @return returns the singleton instance of expresso
     */
    public static Expresso init() {

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

    /**
     * Starts the server over a network port.
     * 
     * @param port the port number on which the class should listen.
     */
    public void listen(int port) {
        this.port = port;
    }

    /**
     * Starts the server
     */
    public void start() {
        try {
            server = Undertow.builder()
                    .addHttpListener(this.port, "0.0.0.0")
                    .setHandler(manager.start())
                    .build();
            server.start();
            System.out.println("Started server :: " + server);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
