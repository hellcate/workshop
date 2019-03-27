package com.jsonplaceholder.test;

import com.jsonplaceholder.pojo.Comment;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;




public class JsonPlaceHolderTest {

    @Test
    public void verifyJsonPlaceHolderAPI(){

        String domain ="http://jsonplaceholder.typicode.com";
        String endPoint = "comments";

        // Do GET Request and Validate Response code is 200
        ValidatableResponse response = given().when().get(domain+"/"+endPoint).then().statusCode(200);

        // Validate Response has more than 0 records
        assertThat(response.extract().jsonPath().getList("$").size(), greaterThan(0));

        //Validate response contains one of the email is equal to Jayne_Kuhic@sydney.com
        assertThat(response.extract().body().jsonPath().get("email").toString(), containsString("Jayne_Kuhic@sydney.com"));




        List<Comment> comment = new ArrayList<Comment>(response.extract().jsonPath().getList("",Comment.class));

        System.out.println("Number of object in Json before Filtering----" +comment.size());

        // Filter the data based on the condition where we should only keep records which contains  non in the body and postId =1
        for(int i =0; i<comment.size();i++){

          if (comment.get(i).getPostId()!=1||!comment.get(i).getBody().contains("non") ) {


              comment.remove(i);
              i--;


          }

        }

        System.out.println("Number of objects in json after Filtering---"+comment.size());
        System.out.println("Result-"+comment);

    }

}
