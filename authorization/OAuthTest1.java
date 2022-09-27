package com.authorization;
import com.company.ReusableMethods1;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;

public class OAuthTest1 {
    public String accessToken;

    public String getAccessToken() {
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJpZDRkWtMMv2-HaSN8yoW8g_VmSMVIQVY0LeL6heTBNjYKZk4JSECr783EAwLZ9Ig&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];

        //getAccessTokenRequest:
        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = ReusableMethods1.rawToJson(accessTokenResponse);
         accessToken = js.getString("access_token");

         return accessToken;
    }


}


