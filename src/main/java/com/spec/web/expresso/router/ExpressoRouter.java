package com.spec.web.expresso.router;

public class ExpressoRouter implements Router {

    // todo: rename these
    private Mappings getMapping;
    private Mappings postMapping;
    private Mappings putMapping;
    private Mappings deleteMappings;

    public ExpressoRouter() {
        getMapping = new Mappings();
        postMapping = new Mappings();
        putMapping = new Mappings();
        deleteMappings = new Mappings();
    }

    @Override
    public Mappings getHttpGetMappings() {
        return getMapping;
    }

    @Override
    public Mappings getHttpPostMappings() {
        return putMapping;
    }

    @Override
    public Mappings getHttpPutMappings() {
        return postMapping;
    }

    @Override
    public Mappings getHttpDeleteMappings() {
        return deleteMappings;
    }

}
