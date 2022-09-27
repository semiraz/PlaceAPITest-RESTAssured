package com.company;

public class Payload1 {

    public static String AddPLace() {
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Minie house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"55, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"dogs park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String AddBook(String isbn, String aisle) {
        String payload = "{\n" +
                "\"name\":\"Learn Appium Automation with Java12\",\n" +
                "\"isbn\":\"" + isbn + "\",\n" +
                "\"aisle\":\"" + aisle + "\",\n" +
                "\"author\":\"John Foe\"\n" +
                "}";
        return payload;
    }

    public  static String DeleteBook(String isbn, String aisle) {
        String payload = "{\n" +
                "\"ID\" : \"" + isbn + aisle + "\"\n" +
                "}";
        return payload;
    }


}





















