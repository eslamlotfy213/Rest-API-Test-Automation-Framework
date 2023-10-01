package CoursesAPI_Project_1;

import Files.PayLoad;
import Files.ReusableMethos;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) throws IOException {

        RestAssured.baseURI ="https://rahulshettyacademy.com";

        //step 1 convert to string
      String response=  given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
               // .body(PayLoad.addBody())
              .body(new String(Files.readAllBytes(Paths.get("src/main/java/JsonFiles/Addbody.json"))))
        .when().post("maps/api/place/add/json")
        .then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)")
              .extract().response().asString();


   // step 2 convert string > json
        System.out.println(response);
        JsonPath jsonPath = ReusableMethos.rowToJson(response);
        String place_id =  jsonPath.getString("place_id");
        String status =  jsonPath.getString("status");
        String scope =  jsonPath.getString("scope");
        String reference =  jsonPath.getString("reference");
        String id =  jsonPath.getString("id");

        // print values
        System.out.println(place_id);
        System.out.println(status);
        System.out.println(scope);
        System.out.println(reference);
        System.out.println(id);



        //--------------------------------put-------------------------------------------------------
      String new_Address="New summer africa";

      given().log().all().
                queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoad.Updateplace(place_id,new_Address))
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));



        //--------------------------------get-------------------------------------------------------
       String getResponse =  given().log().all().
                queryParam("key","qaclick123")
                .queryParam("place_id",place_id)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();


         JsonPath jsonPath1 = ReusableMethos.rowToJson(getResponse);
         String Actualaddress= jsonPath1.getString("address");
         System.out.println(Actualaddress);

    }
}