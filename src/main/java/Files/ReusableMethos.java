package Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethos {

    public static JsonPath rowToJson(String response){
         JsonPath jsonPath1 = new JsonPath(response);
        return  jsonPath1;
    }


}
