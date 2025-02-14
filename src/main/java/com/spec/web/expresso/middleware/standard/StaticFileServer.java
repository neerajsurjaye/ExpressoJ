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
import com.spec.web.expresso.middleware.MiddlewareContext;
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
     * The middleware needs to know on what path(URL path on web) it is registered.
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
        this.staticFolderPath = Paths.get(staticFolderPath).toAbsolutePath().toString();
    }

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareContext ctx) {

        /**
         * The exact path on which the current middleware is invoked
         * Ex: localhost:5757/static/css/style.css
         */
        String currentUrl = req.getRequestPath();

        /**
         * Will hold path to the static file
         * C:/home/user/expresso/static/
         */
        Path baseFolderpath = Paths.get(staticFolderPath);

        /**
         * Gets the URL path on which the current middleware is registered and remvoes
         * the special character "*".
         * /static/* -> /static/
         */
        String urlPathWithoutPattern = HttpUrlPath.replace("*", "");

        System.out.println(String.format("currentUrl : %s \nBaseFolderPath : %s\nurlPathWAparams : %s", currentUrl,
                baseFolderpath, urlPathWithoutPattern));

        /**
         * The url contains the path on which the middleware is registered and the path
         * to the file.
         * The method removes the data before the path to the file.
         * URL = localhost:5757/static/css/style.css
         * Splits the url at urlPathWithoutPattern.
         * localhost:5757/static/css/style.css
         * 
         * localhost:5757 , css/style.css
         * Takes the second token.
         */
        String filePath = null;
        String[] filePathTokens = currentUrl.split(urlPathWithoutPattern);
        if (filePathTokens.length > 1) {
            filePath = filePathTokens[1];
        }

        if (filePath == null) {
            return;
        }

        /**
         * Resovles the full path from basefolder path and the file path
         * Appens the location of file to the full path.
         */
        Path fullPath = baseFolderpath.resolve(filePath).normalize();

        System.out.println("FullPath : " + fullPath);

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
            res.writeResponse("File Not found");
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