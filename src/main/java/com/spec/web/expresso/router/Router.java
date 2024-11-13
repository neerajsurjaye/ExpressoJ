package com.spec.web.expresso.router;

public interface Router {

    Mappings getHttpGetMappings();

    Mappings getHttpPostMappings();

    Mappings getHttpPutMappings();

    Mappings getHttpDeleteMappings();

}
