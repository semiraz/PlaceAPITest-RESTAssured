package com.company;

import io.restassured.path.json.JsonPath;

public class ReusableMethods1 {

    public static JsonPath rawToJson(String response) {
        JsonPath js1 = new JsonPath(response);
        return js1;
    }
}
