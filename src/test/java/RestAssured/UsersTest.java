package RestAssured;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import  org.testng.annotations.Test;

import java.security.PublicKey;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsersTest {

    ExtentReports extent;
    ExtentTest test;

//Set the report before the execution
    @BeforeTest
    public void setup() {
        extent = ReportManager.getReporter();

    }

    RequestSpecification request;

    @BeforeClass
            public void beforeClass() {
         request = given().baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON);
    }

    //This test case check the pagination of users page
    @Test(priority = 1)
    public void getAllUsers(){
        test = extent.createTest("Test Get all users request");
        test.info("Request URL: " + "/users");
        test.info("Request Method: GET " );
        HashMap <String , String > params = new HashMap<>();

        params.put("page","1");
        params.put("per_page","10");

            Response response =    given()
                .spec(request)
                .queryParams(params)
                .when().get("users")
                .then().log().all().assertThat()
                .assertThat().statusCode(200)
                .assertThat().body("page", equalTo(1))
                .assertThat().body("per_page",equalTo(10))
                .assertThat().body("data", hasSize(10))
                .extract().response();

        test.info("Response: " + response.asString());

    }

// This test case check that create users working and return the creation date

    @Test(priority = 2)
    public void  createNewUser (){
        test = extent.createTest("Test Create new user request");
    //Send the request body as pojo way
        UsersPojo body = new UsersPojo();
        body.setEmail("george.bluth@reqres.in");
        body.setFirst_name("George9");
        body.setLast_name("Blutq");

        // Log the request details
        test.info("Request URL: " + "/users");
        test.info("Request Body:  " +"Email: " + body.getEmail() +", First_name: "+ body.getFirst_name() + ", Last_name: " + body.getLast_name());
        test.info("Request Method: POST");

             Response response  =  given()
                .spec(request)
                .body(body)
                .when().post("users/")
                .then().log().body()
                .assertThat().statusCode(201)
                .assertThat().body("email", equalTo(body.getEmail()))
                .assertThat().body("first_name",equalTo(body.getFirst_name()))
                .assertThat().body("last_name",equalTo(body.getLast_name()))
                .assertThat().body("createdAt" ,notNullValue())
                .extract().response();


        test.info("Response: " + response.asString());//Display the get users response in the report
        test.info("This request send the request body as pojo and verify that the status code is 201");
    }

    // This test case check that the user update and the modification
    @Test(priority = 3)
    public void  updateNewUser (){
        test = extent.createTest("Test Update user request");
        //Send the request body as pojo way
        UsersPojo body = new UsersPojo();
        body.setEmail("sam.bluth@reqres.in");
        body.setFirst_name("George10");
        body.setLast_name("jon");
        body.setId("10");

        // Log the request details
        test.info("Request URL: " + "/users");
        test.info("Request Body:  " +"Email: " + body.getEmail() +", First_name: "+ body.getFirst_name() + ", Last_name: " + body.getLast_name());
        test.info("Request Method: PUT");

        Response response  =  given()
                .spec(request)
                .body(body)
                .when().put("users/")
                .then().log().body()
                .assertThat().statusCode(200)
                .assertThat().body("email", equalTo(body.getEmail()))
                .assertThat().body("first_name",equalTo(body.getFirst_name()))
                .assertThat().body("last_name",equalTo(body.getLast_name()))
                .assertThat().body("updatedAt" ,notNullValue())
                .extract().response();

        test.info("Response: " + response.asString());//Display the get users response in the report
        test.info("This request send the request body as pojo and verify that the status code is 200");
    }

    //This test case check the delete user request return status code 204
    @Test(priority = 4)
    public void deleteUser(){
        test = extent.createTest("Test Delete user request");
        //Send the request body as pojo way
        HashMap <String , String> query = new HashMap<>();
        query.put("id", "10");

        // Log the request details
        test.info("Request URL: " + "/users");
        test.info("Request Method: DELETE");

        Response response  =  given()
                .spec(request)
                .queryParams(query)
                .when().delete("users/")
                .then().log().body()
                .assertThat().statusCode(204)
                .extract().response();

        test.info("Response: " + response.asString());//Display the get users response in the report
        test.info("This request send the request body as HasMap way and verify that the status code is 204");
    }

    @AfterTest
    public void tearDown() {
        extent.flush(); // Close and save the report
    }
}
