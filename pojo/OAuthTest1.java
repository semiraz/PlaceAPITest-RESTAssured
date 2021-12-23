package com.pojo;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest1 {

    public static void main(String[] args) throws InterruptedException {

        String[] webAutomationCoursesTitle = {"Selenium Webdriver Java", "Protractor", "Rest Assured Automation using Java"};

        //getAuthorizationCode:

//            WebDriver driver =  new ChromeDriver();
//            driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//
//            driver.findElement(By.cssSelector("input[type='email']")).sendKeys("user@gmail.com");
//            driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//            Thread.sleep(3000);
//
//            driver.findElement(By.cssSelector("input[type='password']")).sendKeys("********");
//            driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//            Thread.sleep(3000);
//            String url = driver.getCurrentUrl();

        String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWjh6MzijnAC454iEopcvMzpU826VFc2mw-4k15n8oWgCg0LgtpGRkui4VUgM-5OQA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println("code = " + code);


        //getAccessTokenRequest:
        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");

        System.out.println("accessToken = " + accessToken);


        //actual request:
        GetCourse gc = given().queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)  //Content-Type is text/html, so we need to parse it to JSON
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

//        System.out.println("response: " + gc);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        // Price of SoapUI:
        List<Api> apiCourses = gc.getCourses().getApi();
//
//        for (int i=0; i<apiCourses.size(); i++) {
//
//           if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
//               System.out.println("Price of Soap: " + apiCourses.get(i).getPrice());
//           }
//        }


        for (Api apis : apiCourses) {
            if (apis.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println("Price of Soap: " + apis.getPrice());
            }
        }

        System.out.println("************************");

        //all title of webAutomation courses:
        ArrayList<String> a = new ArrayList<>();

        List<WebAutomation> w = gc.getCourses().getWebAutomation();

        for (WebAutomation webAutomation : w) {
            a.add(webAutomation.getCourseTitle());
        }

        //comparing arrays(courseTitle):
        List<String> expectedList = Arrays.asList(webAutomationCoursesTitle);

        Assert.assertTrue(a.equals(expectedList));





    }

}

















