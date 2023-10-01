package E2E_Ecommerce_Project_8;

import Files.ReusableMethos;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class E2E {

    public static void main(String[] args) {

        //create class serlization
        Loginrequest login = new Loginrequest();
        login.setUserEmail("dexcomnew98@gmail.com");
        login.setUserPassword("User123#");


        //create Requestsepcbuilder
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();


        //add spec
        RequestSpecification reqlogin=  given().relaxedHTTPSValidation().log().all().spec(requestSpecification).body(login);


       Loginresponse loginresponse = reqlogin.when().post("/api/ecom/auth/login")
               .then().extract().response().as(Loginresponse.class);
        System.out.println(loginresponse.getToken());
        System.out.println(loginresponse.getUserId());
        System.out.println(loginresponse.getMessage());


        //------------------------------add product----------------------------------------------------//
        RequestSpecification CreateProductContract = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", loginresponse.getToken())
                .build();


        RequestSpecification reqProductContract= given().log().all().spec(CreateProductContract)
                .params("productName","Laptop")
                .params("productAddedBy",loginresponse.getUserId())
                .params("productCategory","fashion")
                .params("productSubCategory","shirts")
                .params("productPrice","11500")
                .params("productDescription","Addias Originals")
                .params("productFor","men")
                .multiPart("productImage", new File("src/main/java/Files/avatar.png"));



        AddProductresponse addProductresponse  =  reqProductContract.when().post("/api/ecom/product/add-product")
                .then().extract().response().as(AddProductresponse.class);


        System.out.println(addProductresponse.getProductId());
        System.out.println(addProductresponse.getMessage());

        //------------------------------add order----------------------------------------------------//

        RequestSpecification Createorder= new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", loginresponse.getToken())
                .build();


       ordersrequestDetails ordersdetails = new ordersrequestDetails();
        ordersdetails.setCountry("Egypt");
        ordersdetails.setProductOrderedId(addProductresponse.getProductId());


        List<ordersrequestDetails> orderlist = new ArrayList<ordersrequestDetails>();
        orderlist.add(ordersdetails);


        RequestSpecification reqCreateorder = given().log().all().spec(Createorder).body(orderlist);


     String response =  reqCreateorder.log().all().when().post("/api/ecom/order/create-order").then().extract().response().asString();
        System.out.println(response);


        //------------------------------delete order----------------------------------------------------//

        RequestSpecification deleteorder = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", loginresponse.getToken())
                .setContentType(ContentType.JSON)
                .build();



        RequestSpecification reqDeleteeorder = given().log().all().spec(deleteorder).pathParam("productId",addProductresponse.getProductId());


     String deleteoutput =   reqDeleteeorder.log().all().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();


        //System.out.println(deleteoutput);
        JsonPath jsonPath = ReusableMethos.rowToJson(deleteoutput);
          String actualmessage =  jsonPath.get("message");
          String expectedmessage = "Product Deleted Successfully";
        Assert.assertEquals(actualmessage,expectedmessage);



    }

}
