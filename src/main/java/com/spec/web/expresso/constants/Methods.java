package com.spec.web.expresso.constants;

/**
 * Descibes the method a callback will use.
 * 
 * Mainly describes the HTTP methods but also contains custom methods.
 */
public class Methods {

    /** Hides the public constructor */
    private Methods() {

    }

    /**
     * Custom method : The callback defined on it will be called regardless of what
     * HTTP method is defined on it
     */
    public static final String METHOD_USE = "USE";

    /** Http Get */
    public static final String METHOD_GET = "GET";

    /** Http Post */
    public static final String METHOD_POST = "POST";

    /** Http PUT */
    public static final String METHOD_PUT = "PUT";

    /** Http DELETE */
    public static final String METHOD_DELETE = "DELETE";

}
