package com.company;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //creating body(serialization)
        AddPlace ap = new AddPlace();

        ap.setAccuracy(50);
        ap.setAddress("55, side layout, cohen 09");
        ap.setLanguage("French-IN");
        ap.setName("Minie house");
        ap.setPhone_number("(+91) 983 893 3937");
        ap.setWebsite("http://google.com");

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        ap.setLocation(location);

        List<String> t = new ArrayList<>();
        t.add("dogs park");
        t.add("shop");

        ap.setTypes(t);

        //build RequestSpecBuilder
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        //build ResponseSpecBuilder
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req)
                .body(ap);

        //our request:
        Response response = res.when().post("/maps/api/place/add/json").then().spec(resSpec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

    }
}