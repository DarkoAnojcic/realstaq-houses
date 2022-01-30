package com.realstaq.interview.houses.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRequest {

    public Response doGetRequest(final RequestSpecification requestSpecification) {
        return given()
                    .spec(requestSpecification)
                    .log().all()
                    .relaxedHTTPSValidation()
                .when()
                    .get()
                .then()
                    .log().all()
                    .extract()
                    .response();
    }
}
