package com.realstaq.interview.houses.api;

import com.realstaq.interview.houses.utils.Load;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class Houses {

    private static final String PATH = "/houses";

    private final BaseRequest baseRequest;

    public Houses(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public Response fetchByCityAndPriceRange(final HashMap<String, String> params) {
        return baseRequest.doGetRequest(defaultRequestSpecification(PATH)
                .queryParams(params));
    }

    private RequestSpecification defaultRequestSpecification(final String path) {
        return new RequestSpecBuilder()
                .setBaseUri(Load.aProperty("application.base.url"))
                .setBasePath(path)
                .setContentType(ContentType.JSON)
                .build();
    }
}
