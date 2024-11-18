package com.spec.web.expresso;

import com.spec.web.expresso.callback.RequestCallback;
import com.spec.web.expresso.router.ExpressoRouter;
import com.spec.web.expresso.servlet.RequestHandlingServlet;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import lombok.Getter;

/**
 * Represnts expresso the web framework.
 * 
 * The Class is singleton only one object will exist for the whole program.
 */
public class Expresso implements IExpresso {

    /** The single object that will exist for the whole program. */
    @Getter
    private static Expresso expressoObj = null;

    /** The http server */
    private Undertow server = null;
    /** used by undertow */
    private static DeploymentManager manager = null;

    /** Router object for managing http routes */
    @Getter
    private static ExpressoRouter expressoRouter = null;

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

    /**
     * Creates get mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    @Override
    public void get(String path, RequestCallback callback) {
        expressoRouter.getHttpGetMappings().addPath(path, callback);
    }

    /**
     * Creates post mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    @Override
    public void post(String path, RequestCallback callback) {
        expressoRouter.getHttpPostMappings().addPath(path, callback);
    }

    /**
     * Creates put mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    @Override
    public void put(String path, RequestCallback callback) {
        expressoRouter.getHttpPutMappings().addPath(path, callback);
    }

    /**
     * Creates delete mappings for a user defined callback
     * 
     * @param path     defines the http path
     * @param callback the user defined callback
     */
    @Override
    public void delete(String path, RequestCallback callback) {
        expressoRouter.getHttpDeleteMappings().addPath(path, callback);
    }

    /**
     * Starts the server over a network port.
     * 
     * @param port the port number on which the class should listen.
     */
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
