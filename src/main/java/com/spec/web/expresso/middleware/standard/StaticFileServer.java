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

/**
 * TODO: checks to ensure fullPath stays within staticFolderPath
 * Use filestream to read files
 */
public class StaticFileServer extends MiddlewareMetaData {

    /** Path to the folder containing static files */
    private String staticFolderPath;

    /**
     * Create an instance of class with a staticFolderPath
     * 
     * @param staticFolderPath Path to the folder containing static files
     */
    public StaticFileServer(String staticFolderPath) {

        /* instantiates the parent class with StaticFileServerMiddleware. */
        super(new StaticFileServerMiddleware(staticFolderPath), Methods.METHOD_USE);
        this.staticFolderPath = staticFolderPath;
    }

    /**
     * The middleware needs to know on what path it is registered.
     * The following methods passes the registered path to the middleware.
     */
    @Override
    public void setPath(String pathString) {
        /** Sets the path in the middleware */
        this.getMiddleware().setHttpUrlPath(pathString);
        /** Sets the path to itself */
        super.setPath(pathString);
    }

    /**
     * Returns the middleware registered on the following StaticFileServer.
     */
    @Override
    public StaticFileServerMiddleware getMiddleware() {
        return (StaticFileServerMiddleware) super.getMiddleware();
    }

}

/** The middleware for staticfileserver */
class StaticFileServerMiddleware implements Middleware {

    /** Path to folder containing static files */
    private String staticFolderPath;
    /** Url path on which this middleware would be invoked */
    private String HttpUrlPath;

    public StaticFileServerMiddleware(String staticFolderPath) {
        /** Converts the path to current platforms path */
        // TODO: fix this comment
        this.staticFolderPath = Paths.get(staticFolderPath).toAbsolutePath().toString();
    }

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareFlowController next) {

        /** The exact path on which the current middleware is invoked */
        String url = req.getRequestPath();

        /** Will hold the path to the file. Which will be resolved from the URL */
        StringBuilder filePathBuilder = new StringBuilder(staticFolderPath);

        /**
         * Path to the folder containing staticfile
         */
        Path baseFolderpath = Paths.get(staticFolderPath);

        /**
         * TODO: temporary implementation
         * Gets the path on which the current middleware is registered and remvoes the
         * special character
         */
        String urlPathWithoutPattern = HttpUrlPath.replace("*", "");

        /**
         * The url contains the path on which the middleware is registered and the path
         * to the file.
         * The method removes the data before the path to the file.
         */
        String filePath = url.replace(urlPathWithoutPattern, "");

        /**
         * Appends the file path to the file path builder.
         */
        filePathBuilder.append(filePath);

        /**
         * Resovles the full path from basefolder path and the file path
         */
        Path fullPath = baseFolderpath.resolve(filePath).normalize();

        /**
         * tries to read the data
         * 
         * if successfull returns the page if not returns 404
         */
        try {
            String fileData = Files.readString(fullPath);
            res.writeResponse(fileData);
        } catch (IOException exception) {
            res.setStatusCode(404);
            res.writeResponse("Not found");
        }
    }

    /**
     * Sets the httpurlpath
     * 
     * @param urlPath the url on which the following middleware will execute
     */
    public void setHttpUrlPath(String urlPath) {
        this.HttpUrlPath = urlPath;
    }

}