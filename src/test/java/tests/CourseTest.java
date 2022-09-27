package tests;

import com.authorization.OAuthTest1;
import com.pojo.Api;
import com.pojo.GetCourse;
import com.pojo.WebAutomation;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CourseTest extends OAuthTest1 {
    String[] webAutomationCoursesTitle = {"Selenium Webdriver Java", "Cypress","Protractor"};


    @Test
    public void getCoursesDetails() {
        String at = getAccessToken();
        GetCourse gc =given().queryParam("access_token", at)
                .expect().defaultParser(Parser.JSON)  //Content-Type is text/html, so we need to parse it to JSON
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
        //all title of webAutomation courses:
        ArrayList<String> titleCoursesList = new ArrayList<>();

        List<WebAutomation> w = gc.getCourses().getWebAutomation();

        for (WebAutomation webAutomation : w) {
            titleCoursesList.add(webAutomation.getCourseTitle());

        }

        //comparing arrays(courseTitle):
        List<String> expectedList = Arrays.asList(webAutomationCoursesTitle);

        Assert.assertEquals(expectedList, titleCoursesList);

        Assert.assertEquals(gc.getLinkedIn(), "https://www.linkedin.com/in/rahul-shetty-trainer/");
        Assert.assertEquals(gc.getInstructor(), "RahulShetty");
//        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        String apiCoursePrice = "";
        List<Api> apiCourses = gc.getCourses().getApi();

        for (Api apis : apiCourses) {
            if (apis.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                apiCoursePrice=  apis.getPrice();
            }
        }
        Assert.assertEquals(apiCoursePrice, "40");
    }
}
