package Serialization_Project_6;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Serialzation_Test {


    public static void main(String[] args) throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
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





    Response res=  given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(p)
                .when().post("maps/api/place/add/json")
               .then().assertThat().statusCode(200).extract().response();


    String responcestring =res.asString();
        System.out.println(responcestring);

    }
}