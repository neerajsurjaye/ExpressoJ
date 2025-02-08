package com.spec.web.expresso.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contnains utility methods related to url parsing.
 */
public class URLParser {

    /**
     * Instantiates the class
     */
    public URLParser() {
        // Empty for now
    }

    /**
     * Returns path varaiable registerd on a url.
     * 
     * @param urlPattern The url pattern
     * @param url        The http url pattern to match to
     * @return map of pathVariables name and its values
     */
    public static Map<String, String> getRouteParameters(String urlPattern, String url) {
        Map<String, String> pathVariables = new HashMap<>();
        /**
         * :(\\w) for matching all colons in the url pattern
         * (url pattern = /user/:id/order/:orderid)
         * 
         * replace the :id with the /user/(?<id>\w+)/order/(?<orderId>\w+) here ?<id> ,
         * ?<orderId> are the name capturing group
         */
        Pattern pattern = Pattern.compile(urlPattern.replaceAll(":(\\w+)", "(?<$1>\\\\w+)"));
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            for (String key : getRouteParamaeterNames(urlPattern)) {
                pathVariables.put(key, matcher.group(key));
            }
        }
        return pathVariables;
    }

    /**
     * Extracts the name of the route parameters
     * 
     * @param urlPattern Url pattern from which router parameter names should be
     *                   extracted.
     * @return List of names of router patemeters
     */
    private static List<String> getRouteParamaeterNames(String urlPattern) {
        List<String> pathVariables = new ArrayList<>();
        Pattern pattern = Pattern.compile(":(\\w+)");
        Matcher matcher = pattern.matcher(urlPattern);
        while (matcher.find()) {
            pathVariables.add(matcher.group(1));
        }
        return pathVariables;
    }
}
