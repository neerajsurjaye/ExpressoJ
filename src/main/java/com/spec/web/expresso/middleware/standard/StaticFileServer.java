package com.spec.web.expresso.middleware.standard;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareFlowController;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

/**
 * Hosts static folder
 * Where every server hit will read from the disk
 * 
 * TODO: it should be called like app.use("/static" ,
 * new StaticFileServer("C://home/data"));
 * TODO: IMP: Cleanup this code
 */
public class StaticFileServer extends MiddlewareMetaData {

    private String staticFolderPath;

    public StaticFileServer(String staticFolderPath) {
        this(new StaticFileServerMiddleware(staticFolderPath));
        this.staticFolderPath = staticFolderPath;
    }

    private StaticFileServer(Middleware middleware) {
        super(middleware, Methods.METHOD_USE);
    }

    @Override
    public void setPath(String pathString) {
        /** Sets the path in the middleware */
        ((StaticFileServerMiddleware) this.getMiddleware()).setUrlPath(pathString);
        super.setPath(pathString);
    }

}

/** The middleware for staticfileserver */
class StaticFileServerMiddleware implements Middleware {

    /** Path to folder containing static files */
    private String staticFolderPath;
    /** Url path on which this middleware would be invoked */
    private String urlPath;

    public StaticFileServerMiddleware(String staticFolderPath) {
        this.staticFolderPath = staticFolderPath;
    }

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareFlowController next) {

        String url = req.getRequestPath();
        System.out.println("static file server url : " + url + " set on path " + urlPath);

        StringBuilder filePathBuilder = new StringBuilder(staticFolderPath);
        Path baseFolderpath = Paths.get(staticFolderPath);

        String urlPathWithoutPattern = urlPath.replace("*", "");

        String filePath = url.replace(urlPathWithoutPattern, "");

        filePathBuilder.append(filePath);

        Path fullPath = baseFolderpath.resolve(filePath).normalize();

        System.out.println("FIlepath and filepathbuilder " + filePath + " :: " + filePathBuilder);
        System.out.println("Correct path " + fullPath);

        try {
            String fileData = Files.readString(fullPath);
            res.writeResponse(fileData);
        } catch (IOException exception) {
            res.setStatusCode(404);
            res.writeResponse("Not found");
        }
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}