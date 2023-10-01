package CoursesAPI_Project_1;

import Files.PayLoad;
import Files.ReusableMethos;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Complex {


    int count;
    @Test
    public void Example1(){
        //create a method static rowTojson takes string
        // create inside payload function "courePrice" > return string ""
        // store data inside jscomplex
        JsonPath jscomplex = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        System.out.println("//Print No of courses returned by API");
        count= jscomplex.getInt("courses.size()") ;
        System.out.println(count);
    }

    @Test
    public void Example2(){
        //create a method static rowTojson takes string
        // create inside payload function "courePrice" > return string ""
        // store data inside jscomplex
        JsonPath jscomplex = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        System.out.println("//Print Purchase Amount");
        int Ammount= jscomplex.getInt("dashboard.purchaseAmount") ;
        System.out.println(Ammount);
    }


    @Test
    public void Example3(){
        //create a method static rowTojson takes string
        // create inside payload function "courePrice" > return string ""
        // store data inside jscomplex
        JsonPath jscomplex = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        System.out.println("//Print Title of the first course");
        String titleFirsrt = jscomplex.get("courses[0].title") ;
        System.out.println(titleFirsrt);
    }



    @Test
    public void Example4() {
        //create a method static rowTojson takes string
        // create inside payload function "courePrice" > return string ""
        // store data inside jscomplex
        JsonPath jscomplex = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        System.out.println("//Print All course titles and their respective Prices");
        for (int i = 0; i < count; i++) {


            String courseTitle = jscomplex.get("courses[" + i + "].title");
            int coursePrices = jscomplex.get("courses[" + i + "].price");

            System.out.println("courseTitle :" + courseTitle);
            System.out.println("coursePrices:" + coursePrices);
            System.out.println("______________________" );

        }
    }

    @Test
    public  void Example5(){
        //Print no of copies sold by RPA Course
        JsonPath jscomplex = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        System.out.println("Print no of copies sold by RPA Course");
        for(int i=0;i<count;i++)
        {
            String courseTitles=jscomplex.get("courses["+i+"].title");
            if(courseTitles.equalsIgnoreCase("RPA"))
            {
                int copies=jscomplex.get("courses["+i+"].copies");
                System.out.println(copies);
                break;

            }
        }
    }




    @Test
    public void sumOfCourses()
    {
        int sum = 0;
        JsonPath js = ReusableMethos.rowToJson(PayLoad.CoursePrice());
        int count=	js.getInt("courses.size()");
        for(int i=0;i<count;i++)
        {
            int price=js.getInt("courses["+i+"].price");
            int copies=js.getInt("courses["+i+"].copies");
            int amount = price * copies;
            System.out.println(amount);
            sum = sum + amount;

        }
        System.out.println("sumOfCourses :"+sum);
        int purchaseAmount =js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);

    }




}

