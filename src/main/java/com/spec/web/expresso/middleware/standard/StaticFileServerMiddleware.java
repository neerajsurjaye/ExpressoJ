package com.spec.web.expresso.middleware.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareContext;

/** The middleware for staticfileserver */
class StaticFileServerMiddleware implements Middleware {

    /** Path to folder containing static files */
    private String staticFolderPath;
    /** Url path on which this middleware would be invoked */
    private String httpUrlPath = "";

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
        String urlPathWithoutPattern = httpUrlPath.replace("*", "");

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
         * 
         * The file defaults to index.html in root directory
         */
        String filePath = "index.html";
        String[] filePathTokens = currentUrl.split(urlPathWithoutPattern, 2);
        if (!filePathTokens[1].isEmpty()) {
            filePath = filePathTokens[1];
        }

        /**
         * Resovles the full path from basefolder path and the file path
         * Appends the location of file to the full path.
         */
        Path fullPath = baseFolderpath.resolve(filePath).normalize();

        /**
         * Stops directory traversal attacks
         */
        if (!fullPath.startsWith(baseFolderpath)) {
            res.setStatusCode(403);
            res.writeResponse("Access Denied");
            return;
        }

        /**
         * Checks for symlink attacks
         */
        try {
            fullPath = fullPath.toRealPath();
            if (!fullPath.startsWith(baseFolderpath)) {
                res.setStatusCode(403);
                res.writeResponse("Access Denied");
                return;
            }
        } catch (IOException e) {
            res.setStatusCode(404);
            res.writeResponse("File Not Found");
            return;
        }

        String mimeType = req.getMimeType(fullPath.getFileName().toString());
        if (mimeType == null)
            mimeType = "application/octet-stream";
        res.setContentTypeHeader(mimeType);

        /**
         * Tries to read the data
         * 
         * if successfull returns the page, if not returns 404 HTTP error response
         */
        try {
            OutputStream outputStream = res.getOutputStream();
            Files.copy(fullPath, outputStream);
        } catch (IOException exception) {
            res.setStatusCode(404);
            res.writeResponse("File Not found");
        } catch (Exception e) {
            res.setStatusCode(500);
            res.writeResponse("Internal Server Error");
        }
    }

    /**
     * Sets the httpurlpath
     * 
     * @param urlPath the url on which the following middleware will execute
     */
    public void setHttpUrlPath(String urlPath) {
        this.httpUrlPath = urlPath;
    }

}