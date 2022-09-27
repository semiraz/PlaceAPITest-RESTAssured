package tests;

import com.company.Payload1;
import com.company.ReusableMethods1;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddDeleteBookTest {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";

        //addBook
        String response = given().log().all().header("Content-Type", "application/json").
                body(Payload1.AddBook(isbn, aisle)).
                when().post("/Library/Addbook.php").then()
                .assertThat().statusCode(200).
                extract().response().asString();

        JsonPath js = ReusableMethods1.rawToJson(response);
        String id = js.get("ID");
        System.out.println("ID: " + id);
        Assert.assertEquals(id, isbn+aisle);
    }

    //deleteBook
    @Test(dataProvider = "BooksData")
    public void deleteBook(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";

        given().log().all().header("Content-Type", "application/json").
                body(Payload1.DeleteBook(isbn, aisle)).
                when().delete("/Library/DeleteBook.php").then()
                .log().all()
                .assertThat().statusCode(200);


    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {

        return new Object[][] {
                {"jdkdfjp", "2258"},
                {"ojffusk", "889995"},
                {"ffkrdh", "784585"}
        };
    }



}




















