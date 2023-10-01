package libraryApi_Project_3;

import Files.PayLoad;
import Files.ReusableMethos;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddBook {
    @DataProvider(name = "BooksData")
    public Object[][] getData(){
       return new Object[][]{{"abcd2","155"},{"jgfg","566"},{"bmbn","876"}};
    }

    @Test (dataProvider = "BooksData")
    public  void addbook(String isbn, String aisle) {
     RestAssured.baseURI="http://216.10.245.166";
     Response  response= given().log().all()
                 .header("Content-Type","application/json")
                 .body(PayLoad.addbock(isbn,aisle))
                 .when().post("/Library/Addbook.php")
                 .then().log().all().assertThat().statusCode(200).extract().response();

        String responseString=response.asString();
        JsonPath jsonPath = ReusableMethos.rowToJson(responseString);

        String ID= jsonPath.get("ID");
        System.out.println(ID);
}


}
