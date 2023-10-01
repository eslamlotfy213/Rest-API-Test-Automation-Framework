package specBuildTest_Project_7;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

public class Serialzation_Test {

    public static void main(String[] args) throws IOException {

        //RestAssured.baseURI = "https://rahulshettyacademy.com";
        Addplace p = new Addplace();

        Location l = new Location();
        l.setLat( -38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

        List<String> t = new ArrayList<String>();
        t.add("shoe park");
        t.add("shop");
        p.setTypes(t);

        p.setAccuracy(50);
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setAddress("29, side layout, cohen 09");
        p.setWebsite("http://google.com");
        p.setLanguage("French-IN");


        //1 Build   -Request Spec Builder-
 RequestSpecification requestSpecification = new RequestSpecBuilder()
         .setBaseUri("https://rahulshettyacademy.com")
         .addQueryParam("key", "qaclick123")
         .setContentType(ContentType.JSON).build();



        //2 Build   -Responce Spec Builder-
 ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

      ///------------------------------------------------//
       //add spec() on request
    RequestSpecification res=  given().spec(requestSpecification).body(p);
        //add spec() on response
    Response response= res.when().post("maps/api/place/add/json").then().spec(responseSpecification).extract().response();


    String responcestring =response.asString();
        System.out.println(responcestring);

    }
}