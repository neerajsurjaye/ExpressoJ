package com.spec.web.expresso.router;

/**
 * The class maps http methods and path to user defined callbacks.
 * 
 * @deprecated replaced by pathRouter
 */
@Deprecated
public class ExpressoRouter implements IRouter {

    // ** Stores path mappings of http get request method */
    private Mappings httpGetMapping;
    // ** Stores path mappings of http post request method */
    private Mappings httpPostMapping;
    // ** Stores path mappings of http put request method */
    private Mappings putMapping;
    // ** Stores path mappings of http delete request method */
    private Mappings deleteMappings;

    // ** Instantiates the class */
    public ExpressoRouter() {
        httpGetMapping = new Mappings();
        httpPostMapping = new Mappings();
        putMapping = new Mappings();
        deleteMappings = new Mappings();
    }

    /**
     * Returns mappings for Http get requests.
     * 
     * @return mapping object for get request
     */
    @Override
    public Mappings getHttpGetMappings() {
        return httpGetMapping;
    }

    /**
     * Returns mappings for Http post requests.
     * 
     * @return mapping object for post request
     */
    @Override
    public Mappings getHttpPostMappings() {
        return putMapping;
    }

    /**
     * Returns mappings for Http put requests.
     * 
     * @return mapping object for put request
     */
    @Override
    public Mappings getHttpPutMappings() {
        return httpPostMapping;
    }

    /**
     * Returns mappings for Http delete requests.
     * 
     * @return mapping object for delete request
     */
    @Override
    public Mappings getHttpDeleteMappings() {
        return deleteMappings;
    }

}
