package com.spec.web.expresso.middleware.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.spec.web.expresso.constants.Methods;
import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareContext;
import com.spec.web.expresso.middleware.MiddlewareMetaData;

import jakarta.servlet.http.HttpServlet;

public class StaticResouceServer extends MiddlewareMetaData {

    /** Path to the resouce folder containing static files in resources folder */
    private String staticResoucePath;

    /**
     * Create an instance of class with a staticResoucePath
     * 
     * @param staticResoucePath Path to the folder containing static files
     */
    public StaticResouceServer(String staticResoucePath) {

        /* instantiates the parent class with StaticFileServerMiddleware. */
        super(new StaticResourceServerMiddleware(staticResoucePath), Methods.METHOD_USE);
        this.staticResoucePath = staticResoucePath;
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
    public StaticResourceServerMiddleware getMiddleware() {
        return (StaticResourceServerMiddleware) super.getMiddleware();
    }

}

/** The middleware for staticfileserver */
class StaticResourceServerMiddleware implements Middleware {

    /** Path to folder containing static files */
    private String staticResoucePath;
    /** Url path on which this middleware would be invoked */
    private String HttpUrlPath;

    public StaticResourceServerMiddleware(String staticResoucePath) {
        /** Converts the path to current platforms path */
        this.staticResoucePath = staticResoucePath;
    }

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareContext ctx) {

        /**
         * The exact path on which the current middleware is invoked
         * Ex: localhost:5757/static/css/style.css
         */
        String currentUrl = req.getRequestPath();

        /**
         * Will hold path to the static file in resource folder
         * /resources/static/
         */
        Path baseFolderpath = Paths.get(staticResoucePath);

        /**
         * Gets the URL path on which the current middleware is registered and remvoes
         * the special character "*".
         * /static/* -> /static/
         */
        String urlPathWithoutPattern = HttpUrlPath.replace("*", "");

        System.out.println(String.format("\n" + //
                "========\n" + //
                "currentUrl : %s \nBaseFolderPath : %s\nurlPathWAparams : %s \n========\n", currentUrl,
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
         * Appends the location of file to the full path.
         * 
         * /resources/static/ + css/style => /resources/static/ css/style
         */
        Path fullPath = baseFolderpath.resolve(filePath).normalize();

        System.out.println("FullPath : " + fullPath + " " + fullPath.toString());

        /**
         * tries to read the data
         * 
         * if successfull returns the page if not returns 404
         */
        InputStream inputStream = null;
        try {

            String currentFilepath = fullPath.toString();
            currentFilepath = currentFilepath.substring(1, currentFilepath.length());
            currentFilepath = currentFilepath.replace("\\", "/");

            System.out.println("CurrentFilePath : " + currentFilepath);

            inputStream = this.getClass().getClassLoader().getResourceAsStream(currentFilepath);

            if (inputStream == null) {
                res.setStatusCode(404);
                res.writeResponse("File Not found");
                return;
            }

            String mimeType = req.getMimeType(fullPath.getFileName().toString());

            OutputStream out = res.getOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            res.setContentTypeHeader(mimeType);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException exception) {
            res.setStatusCode(404);
            res.writeResponse("File Not found");
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                res.setStatusCode(500);
                res.writeResponse("Internal server error");
            }
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