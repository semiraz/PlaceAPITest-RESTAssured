package com.authorization;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8082";

        //Login Scenario:
        SessionFilter session = new SessionFilter();

        String response = given().header("Content-Type", "application/json")
                        .body("{ \"username\": \"username\", \"password\": \"*password*\" }")
                .log().all().filter(session).when().post("/rest/auth/1/session").then()
                .log().all().extract().response().asString();


        //Add Comment
        String expectedMessage = "Hi, This is just another message!";
        String addCommentResponse = given().pathParam("id","10204").log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                "    \"body\": \"" + expectedMessage + "\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then()
                .log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.getString("id");


        //Add Attachment
        given().header("X-Atlassian-Token","no-check").filter(session)
                .pathParam("id", "10204").header("Content-Type", "multipart/form-data")
                .multiPart("file", new File(System.getProperty("user.dir") + "/src/main/java/com/jira/jira.txt")).
                when().post("rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);


        //Get Issue
        String issueDetails = given().filter(session).pathParam("id", "10204")
                .queryParam("fields", "comment").when()
                .get("/rest/api/2/issue/{id}").then().log().all()
                .extract().response().asString();

        System.out.println("issueDetails: " + issueDetails);


        JsonPath js1 = new JsonPath(issueDetails);
        int commentCount = js1.getInt("fields.comment.comments.size()");

        for (int i=0; i<commentCount; i++) {
            String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
            if (commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = js1.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(message, expectedMessage);
            }
        }

    }

}



























