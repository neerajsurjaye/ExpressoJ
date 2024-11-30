package com.spec.web.expresso.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {
    public static Map<String, String> getPathVariables(String urlPattern, String url) {
        Map<String, String> pathVariables = new HashMap<>();
        // :(\\w) for matching all colons in the url pattern (url pattern = /user/:id/order/:orderid)
        // replace the :id with the /user/(?<id>\w+)/order/(?<orderId>\w+) here ?<id> , ?<orderId> are the name capturing group
        Pattern pattern = Pattern.compile(urlPattern.replaceAll(":(\\w+)", "(?<$1>\\\\w+)"));
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            for (String key : getPathVariableNames(urlPattern)) {
                pathVariables.put(key, matcher.group(key));
            }
        }
        return pathVariables; 
    }

    private static List<String> getPathVariableNames(String urlPattern) {
        List<String> pathVariables = new ArrayList<>();
        Pattern pattern = Pattern.compile(":(\\w+)");
        Matcher matcher = pattern.matcher(urlPattern);
        while (matcher.find()) {
            pathVariables.add(matcher.group(1));
        }
        return pathVariables;
    }
}
