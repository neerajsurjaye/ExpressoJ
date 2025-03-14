package com.spec.web.expresso;

import com.spec.web.expresso.exceptions.ExpressoRuntimeException;
import com.spec.web.expresso.router.PathRouter;
import com.spec.web.expresso.servlet.RequestHandlingServlet;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

/**
 * User will instantiate the following class to interact with the framework.
 * Class follows singleton pattern.
 */
@Slf4j
public class Expresso extends PathRouter {

    /** The single Expresso object that will exist for the whole program. */
    private static Expresso expressoObj = null;

    /** The network port on which server will run on. Defaults to 8080 */
    private int port = 8080;

    /** The host on which the server would listen on */
    private String host = "0.0.0.0";

    /** Holds the base context path of the servlet */
    private String contextPath = "/";

    /** Holds the state of server */
    private boolean isServerStarted = false;

    Undertow server = null;

    /** Private constructor to enforce singelton pattern. */
    private Expresso() {

    }

    public static Expresso getExpressoObj() {
        return expressoObj;
    }

    /**
     * Instantiates the expresso framework.
     * 
     * If instance does not exist. It creates one and returns it.
     * 
     * Synchronized to prevent multiple threads from creating multiple instances of
     * expresso.
     * 
     * @return returns the singleton instance of expresso
     */
    public static synchronized Expresso init() {
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
    public Expresso listenOnPort(int port) {
        if (this.isServerStarted) {
            throw new IllegalStateException("Server has already started cannot set port");
        }

        this.port = port;
        return this;
    }

    public Expresso setHost(String host) {
        if (this.isServerStarted) {
            throw new IllegalStateException("Server has already started cannot set host");
        }

        this.host = host;
        return this;
    }

    public Expresso setContextPath(String contextPath) {
        if (this.isServerStarted) {
            throw new IllegalStateException("Server has already started cannot set contextPath");
        }

        this.contextPath = contextPath;
        return this;
    }

    /**
     * Starts the server
     */
    public void startServer() {
        try {

            ServletInfo servletInfo = Servlets.servlet("RequestHandlingServlet", RequestHandlingServlet.class)
                    .addMapping("/*");

            DeploymentInfo deploymentInfo = Servlets.deployment()
                    .setClassLoader(Expresso.class.getClassLoader())
                    .setContextPath(this.contextPath)
                    .setDeploymentName("expresso")
                    .addServlet(servletInfo);

            /** used by undertow */
            DeploymentManager manager = Servlets.defaultContainer().addDeployment(deploymentInfo);

            manager.deploy();

            server = Undertow.builder()
                    .addHttpListener(this.port, this.host)
                    .setHandler(manager.start())
                    .build();
            server.start();

            isServerStarted = true;

            log.warn("Server Started on port {} with host IP {} and context path \"{}\"", this.port,
                    this.host,
                    this.contextPath);
        } catch (ServletException | RuntimeException e) {
            throw new ExpressoRuntimeException(e);
        }
    }

    public synchronized void stopServer() {
        if (this.server != null) {
            server.stop();
            server = null;
            isServerStarted = false;
            log.warn("Server stopped");
        }
    }

}
