package com.realstaq.interview.houses.steps;

import com.realstaq.interview.houses.api.Houses;
import com.realstaq.interview.houses.utils.Load;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.net.URI;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HousesSteps implements En {

    private static final String CITY = "Austin";
    private static final Integer PRICE_GTE = 300000;
    private static final Integer PRICE_LTE = 500000;
    private static final Integer LIMIT_PRICE = 600000;
    private static final URI EXPECTED_HOUSES = URI.create("responses/Austin_between_300000_and_500000.json");
    private static final URI EXPECTED_LOWER_LIMIT_HOUSES = URI.create("responses/Austin_lower_limit_600000.json");
    private static final URI EXPECTED_UPPER_LIMIT_HOUSES = URI.create("responses/Austin_upper_limit_600000.json");


    private final Houses houses;

    private Response response;

    public HousesSteps(Houses houses) {
        this.houses = houses;

        When("^user tries to filter houses in price range by city$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", PRICE_GTE.toString());
            queryParams.put("price_lte", PRICE_LTE.toString());
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        Then("^all houses in price range for that city will be shown$", () -> {
            assertThat(response.statusCode()).as("Http status code is not correct!").isEqualTo(200);
            String expectedHouses = Load.aFile(EXPECTED_HOUSES);
            JSONAssert.assertEquals(expectedHouses, response.asString(), JSONCompareMode.LENIENT);
        });

        When("^user tries to filter house with invalid price parameter$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", PRICE_GTE.toString());
            queryParams.put("price_lte", "abcdef");
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        Then("^an error response should be returned with status code (.*)$", (Integer statusCode) -> {
            assertThat(response.statusCode()).as("Http status code is not correct!").isEqualTo(statusCode);
        });

        And("^an error message (.*)$", (String message) -> {
            assertThat(response.jsonPath().getString("message")).as("An error message is not correct!").isEqualTo(message);
        });

        When("^user tries to filter house with negative price parameter$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", "-100000");
            queryParams.put("price_lte", PRICE_LTE.toString());
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        When("^user tries to filter houses with out range prices$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", "8000000");
            queryParams.put("price_lte", "9000000");
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        Then("^an empty list should be returned$", () -> {
            assertThat(response.statusCode()).as("Http status code is not correct!").isEqualTo(200);
            assertThat(response.jsonPath().getList("").size()).as("The response payload is not empty!").isEqualTo(0);
        });

        When("^user tries to filter house with an empty string parameter$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", "");
            queryParams.put("price_lte", "500000");
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        When("^user tries to filter houses with not existing city$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", "Novi Sad");
            queryParams.put("price_gte", PRICE_GTE.toString());
            queryParams.put("price_lte", PRICE_LTE.toString());
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        When("^user tries to filter houses with lower limit of price range by city$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_gte", LIMIT_PRICE.toString());
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        Then("^all houses in lower limit price range for that city will be shown$", () -> {
            assertThat(response.statusCode()).as("Http status code is not correct!").isEqualTo(200);
            String expectedHouses = Load.aFile(EXPECTED_LOWER_LIMIT_HOUSES);
            JSONAssert.assertEquals(expectedHouses, response.asString(), JSONCompareMode.LENIENT);
        });

        When("^user tries to filter houses with upper limit of price range by city$", () -> {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("city", CITY);
            queryParams.put("price_lte", LIMIT_PRICE.toString());
            response = houses.fetchByCityAndPriceRange(queryParams);
        });

        Then("^all houses in upper limit price range for that city will be shown$", () -> {
            assertThat(response.statusCode()).as("Http status code is not correct!").isEqualTo(200);
            String expectedHouses = Load.aFile(EXPECTED_UPPER_LIMIT_HOUSES);
            JSONAssert.assertEquals(expectedHouses, response.asString(), JSONCompareMode.LENIENT);
        });
    }
}
