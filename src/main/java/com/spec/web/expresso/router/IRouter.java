package com.spec.web.expresso.router;

/**
 * @deprecated
 */
@Deprecated
public interface IRouter {

    /**
     * Returns mappings for Http get requests.
     * 
     * @return mapping object for get request
     */
    Mappings getHttpGetMappings();

    /**
     * Returns mappings for Http post requests.
     * 
     * @return mapping object for post request
     */
    Mappings getHttpPostMappings();

    /**
     * Returns mappings for Http put requests.
     * 
     * @return mapping object for put request
     */
    Mappings getHttpPutMappings();

    /**
     * Returns mappings for Http delete requests.
     * 
     * @return mapping object for delete request
     */
    Mappings getHttpDeleteMappings();

}
