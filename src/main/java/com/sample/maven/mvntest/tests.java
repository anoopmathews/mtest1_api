package com.sample.maven.mvntest;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.sample.maven.utilities.BrowserUtils;
import com.sample.maven.utilities.TimerUtils;

import static io.restassured.RestAssured.given;

public class tests{
	

	@Test
	public void testSpaceXEndpoint(){
		
		System.out.println("Thread::" + Thread.currentThread().getName()+" in testSpaceXEndpoint");
		Reporter.log("Inside testSpaceXEndpoint test");
		
		Assertion hardAssert = new Assertion();
		SoftAssert softAssert = new SoftAssert();
		
		String url="https://api.spacexdata.com/v4/launches/latest";
		
		Response response= given()		//relaxedHTTPSValidation("TLSv1.2")
		   		.contentType("application/json")
		   		.log()
		   		.all()					
		   		.when()
		   		.get(url)
		   		.then().log().all().extract().response();
		
		System.out.println("Status : "+response.getStatusCode());
		Reporter.log("Response Status - "+response.getStatusCode());
		hardAssert.assertEquals(response.getStatusCode(), 200, "Response code is not 200. Actual - "+response.getStatusCode());
		
		//System.out.println(response.asString());
		System.out.println("Response Time : " + response.getTime());
		int resTime=Integer.parseInt(response.getHeader("spacex-api-response-time").substring(0,response.getHeader("spacex-api-response-time").length()-2));
		Reporter.log("Response time - "+resTime);
		softAssert.assertTrue(resTime < 2000 , "Response took more than 2 seconds" );
		
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) jsonParser.parse(response.asString());
			
			softAssert.assertTrue(obj.containsKey("id"), "id field is present" );
			if(obj.containsKey("id")){
				System.out.println("id field present");
				
			}
			String id= (String)obj.get("id");
			softAssert.assertTrue(id!=null, "id field is not null" );
			if(id!=null)
				System.out.println("id field is not null");
            
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.log("Exception : "+e.getMessage());
		}
		 softAssert.assertAll();
		
	}
	
	
	
}