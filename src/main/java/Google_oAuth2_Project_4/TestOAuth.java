package Google_oAuth2_Project_4;


import E2E_Automation_Pojo_Project_5.Api;
import E2E_Automation_Pojo_Project_5.Planoldjavaobject;
import E2E_Automation_Pojo_Project_5.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestOAuth {


   @Test
   public void Testcase1() throws InterruptedException {

      //get authrization code
      //get accesstoken by using authrization code
      //get run actuak request


       //get authrization code from browser
//         System.setProperty("webdriver.chrome.driver","src/main/Drivers/chromedriver.exe");
//         ChromeOptions chrome_options = new ChromeOptions();
//         WebDriver driver = new ChromeDriver(chrome_options);
       //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//         driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//         driver.findElement(By.xpath("//input[@type='email']")).sendKeys("nnew57165@gmail.com",Keys.ENTER);
//         Thread.sleep(4000);
//         driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user123#",Keys.ENTER);
//         Thread.sleep(4000);
//         String url = driver.getCurrentUrl();
         String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXm6o_oklWWz__IxhN3WuHa4JagIlNM5D8razIq55ZOlaoJ4M57g6IjnB_sPjCse3Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=2&prompt=none";
         //driver.close();
         String partcode =url.split("code=")[1];
         System.out.println("partcode "+ partcode);
         String code=  partcode.split("&scope")[0];
         System.out.println("code  "+ code);




       String response1=  given()
               .urlEncodingEnabled(false)
               .queryParam("code",code)
               .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
               .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                       .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                               .queryParam("grant_type","authorization_code")
                                       .when().log().all()
             .post("https://www.googleapis.com/oauth2/v4/token").asString();



       JsonPath jsonPath = new JsonPath(response1);
       String myaccess = jsonPath.get("access_token");


       //old implmentation

//       String response =  given().queryParam("access_token",myaccess)
//               .when()
//               .log().all().get("https://rahulshettyacademy.com/getCourse.php")
//               .asString();
//       System.out.println(response);
//



       //deserlization covert responce to java object
       Planoldjavaobject obj=  given().queryParam("access_token",myaccess).expect().defaultParser(Parser.JSON)
               .when().get("https://rahulshettyacademy.com/getCourse.php").as(Planoldjavaobject.class);



       System.out.println(obj.getLinkedIn());
       //System.out.println(obj.getCourses().getWebAutomation().get(0).getCourseTitle());
    //print size of list
       List<WebAutomation> apisize = obj.getCourses().getWebAutomation();
       for (int i=0; i< apisize.size();i++){

           if (apisize.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java")) {
               System.out.println(apisize.get(i).getCourseTitle());
               System.out.println(apisize.get(i).getPrice());
               break;
           }


//           System.out.println(obj.getCourses().getMobile().get(i).getCourseTitle());
//           System.out.println(obj.getCourses().getMobile().get(i).getPrice());
//
//           System.out.println(obj.getCourses().getApi().get(i).getCourseTitle());
//           System.out.println(obj.getCourses().getApi().get(i).getPrice());
       }


       String [] expectedrray = {"Selenium Webdriver Java","Cypress","Protractor"};
       ArrayList<String> actual = new ArrayList<String>();

       //print all courses
       List<WebAutomation> apisize2 = obj.getCourses().getWebAutomation();
       for (int i=0; i<apisize2.size();i++)
       {
           actual.add(apisize2.get(i).getCourseTitle());
           System.out.println("All course : "+actual);
       }

       //convert array to list
        List<String> expectedrraylist = Arrays.asList(expectedrray);

       //compare two arrays
       Assert.assertTrue(actual.equals(expectedrraylist));










   }




}
