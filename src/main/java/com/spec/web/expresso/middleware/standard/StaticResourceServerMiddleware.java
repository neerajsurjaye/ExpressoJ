package com.spec.web.expresso.middleware.standard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.spec.web.expresso.message.HttpRequest;
import com.spec.web.expresso.message.HttpResponse;
import com.spec.web.expresso.middleware.Middleware;
import com.spec.web.expresso.middleware.MiddlewareContext;

/**
 * The middleware for static resource server.
 * This is only used to serve resources bundled in the jar
 */
class StaticResourceServerMiddleware implements Middleware {

    /** Path to folder containing static files */
    private String staticResourcePath;

    private String httpUrlPath = "";

    public StaticResourceServerMiddleware(String staticResourcePath) {
        /** Converts the path to current platforms path */
        this.staticResourcePath = staticResourcePath;
    }

    @Override
    public void execute(HttpRequest req, HttpResponse res, MiddlewareContext ctx) {

        /**
         * The exact path on which the current middleware is invoked
         * Ex: localhost:5757/static/css/style.css
         */
        String currentUrl = req.getRequestPath();

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
         * If no file is provided default file index.html is loaded.
         */
        String filePath = "index.html";
        String[] filePathTokens = currentUrl.split(urlPathWithoutPattern, 2);
        if (filePathTokens.length > 1 && !filePathTokens[1].isEmpty()) {
            filePath = filePathTokens[1];
        }

        /**
         * Rejects requests which may contain path traversal attacks.
         */
        if (filePath.contains("..")) {
            res.setStatusCode(403);
            res.writeResponse("Forbidden");
            return;
        }

        /**
         * tries to read the data
         * 
         * if successfull returns the page if not returns 404
         */
        Path resourcePath = Paths.get(staticResourcePath, filePath).normalize();
        String currentFilepath = resourcePath.toString().replace(File.separator, "/");

        /*
         * Removes the first / or \ if it exists from the path as reading from resouces
         * directory the file
         * path should start from directory or file name
         * 
         */
        if (currentFilepath.charAt(0) == '\\' || currentFilepath.charAt(0) == '/')
            currentFilepath = currentFilepath.substring(1, currentFilepath.length());

        currentFilepath = currentFilepath.replace(File.separator, "/");

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(currentFilepath)) {

            OutputStream out = res.getOutputStream();
            if (inputStream == null) {
                // File not found should try to execute the next middleware.
                ctx.executeNextMiddleware();
                ctx.markMiddlewareNotExecuted();
                return;
            }

            String mimeType = req.getMimeType(filePath);
            res.setContentTypeHeader(mimeType);

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

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
        this.httpUrlPath = urlPath;
    }

}