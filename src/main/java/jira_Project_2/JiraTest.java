package jira_Project_2;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static  io.restassured.RestAssured.*;

public class JiraTest {


    @Test
    public void AddCommit(){
        RestAssured.baseURI = "http://localhost:8080";
        SessionFilter sessionFilter = new SessionFilter();
         //authenticate
   String res=   given().log().all()
                .header("Content-Type","application/json")
                .body("{ \"username\": \"eslamlotfy213\", \"password\": \"User123#\" }")
                .log().all()
                .filter(sessionFilter)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();


String Expectdcomment="My first comment";

        given().pathParam("id","10004").log().all()
                .header("Content-Type","application/json")
                .body("{\n" +
                "    \"body\": \" "+Expectdcomment+" \",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}")
                .filter(sessionFilter).when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201);



        //add attachments
        given().pathParam("id","10004").header("X-Atlassian-Token","no-check").filter(sessionFilter)
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("src/main/java/jira/file.txt"))
                .when().post("/rest/api/2/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);


        //get issues
       String issueDetails= given().pathParam("id","10004").filter(sessionFilter)
                .queryParam("fields","comment")
                .log().all()
                .when().get("/rest/api/2/issue/{id}")
                .then().log().all()
                .extract().response().asString();
        System.out.println(issueDetails);


        JsonPath js1 = new JsonPath(issueDetails);
        int commentscount =js1.get("fields.comment.comments.size()");
        for (int i =0; i < commentscount ; i++){
            String commentIssue = js1.get("fields.comment.comments["+i+"].id").toString();
          if (commentIssue.equalsIgnoreCase(Expectdcomment)){
              String message = js1.get("fields.comment.comments["+i+"].body").toString();
              System.out.println("message"+message);
              Assert.assertEquals(message,Expectdcomment);
          }

        }




    }
}
