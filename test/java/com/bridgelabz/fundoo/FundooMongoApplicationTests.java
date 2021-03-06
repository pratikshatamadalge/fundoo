
package com.bridgelabz.fundoo;

import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FundooMongoApplicationTests {

	// *************************Note Junit******************************

	@Test
	public void createNote() throws JSONException {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://localhost:8080/note";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "7 wonders");
		jsonObject.put("description", "taj mahal"); // Get the RequestSpecification of the request that you want to sent
		// to the server.The server is specified by the BaseURI that we have
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.body(jsonObject.toString()).contentType("application/json");

		// Make a request to the server by specifying the method Type and the method
		// URL. This will return the Response from the server. Store the response
//  in a variable.
		Response response = httpRequest.request(Method.POST, "/createNote");
		response.then().log().all().statusCode(200);
		// Now let us print the body of the message to see what response // we have
		// recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(200,response.getStatusCode());
	}

	@Test
	public void getNotes() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko");

		Response response = httpRequest.request(Method.GET, "/getNote");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
	}

	@Test
	public void updateNote() throws JSONException {
		RestAssured.baseURI = "http://localhost:8080/note";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title", "world wonders");

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5db9971e3f437640a9537b76").body(jsonObject.toString())
				.contentType("application/json");

		Response response = httpRequest.request(Method.PUT, "/updateNote");
		response.then().log().all().statusCode(200);
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
	}

	@Test
	public void deleteNote() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5db9971e3f437640a9537b76");

		Response response = httpRequest.request(Method.DELETE, "/deleteNote");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void pinNote() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5dba69b03f43761e31622cbe");

		Response response = httpRequest.request(Method.PUT, "/pin");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void archiveNote() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5dba69b03f43761e31622cbe");

		Response response = httpRequest.request(Method.PUT, "/archive");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void trashNote() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5dba69b03f43761e31622cbe");

		Response response = httpRequest.request(Method.PUT, "/trash");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void addLabel() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5db9971e3f437640a9537b76").param("labelId", "5db987153f4376351b21d16a");

		Response response = httpRequest.request(Method.PUT, "/addLabel");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void sortByDate() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko");

		Response response = httpRequest.request(Method.GET, "/sortByDate");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void sortByTitle() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko");

		Response response = httpRequest.request(Method.GET, "/sortByTitle");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is => " + responseBody);
	}

	@Test
	public void addRemainder() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("header",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.header("noteId", "5dba69b03f43761e31622cbe").param("remainder", "2019-11-13T17:09:42.411")
				.param("repeat", "DAILY");

		Response response = httpRequest.request(Method.PUT, "/addRemainder");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}

	@Test
	public void updateRemainder() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("header",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5dba69b03f43761e31622cbe").param("remainder", "2019-11-13T17:09:42.411")
				.param("repeat", "DAILY");

		Response response = httpRequest.request(Method.PUT, "/updateRemainder");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}

	@Test
	public void deleteRemainder() {
		RestAssured.baseURI = "http://localhost:8080/note";

		RequestSpecification httpRequest = RestAssured.given().header("header",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("noteId", "5dba69b03f43761e31622cbe");

		Response response = httpRequest.request(Method.DELETE, "/deleteRemainder");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}
	//////// *****************User Junit*************************

	@Test
	public void register() throws JSONException {

		RestAssured.baseURI = "http://localhost:8080/user";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", "Pratiksha123");
		jsonObject.put("password", "12345678");
		jsonObject.put("emailId", "pratikshatamadalge21@gmail.com");
		jsonObject.put("mobileNo", "7875680359");

		RequestSpecification httpRequest = RestAssured.given().body(jsonObject.toString())
				.contentType("application/json");
		Response response = httpRequest.request(Method.POST, "/register");
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>" + responseBody);
		response.then().log().all().statusCode(200);
		Assert.assertEquals(200,response.getStatusCode());
	}

	@Test
	public void login() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("emailId", "pratikshatamadalge21@gmail.com");
		jsonObject.put("password", "12345678");

		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().body(jsonObject.toString())
				.contentType("application/json");
		Response response = httpRequest.request(Method.GET, "/login");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is=> " + responseBody);
	}

	@Test
	public void getdata() {
		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/getdata");

		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is=>" + responseBody);
	}

	@Test
	public void updateUser() {
		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().param("oldEmail", "poojamandloi55@gmail.com")
				.param("newEmail", "poojamandloi55@gmail.com");
		Response response = httpRequest.request(Method.PUT, "/update");

		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is=>" + responseBody);
	}

	@Test
	public void DeleteUser() {
		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().param("emailId", "pratikshatamadalge21@gmail.com");
		Response response = httpRequest.request(Method.DELETE, "/delete");

		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is=>" + responseBody);

	}

	@Test
	public void forgot() {
		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().param("emailId", "pratikshatamadalge21@gmail.com");
		Response response = httpRequest.request(Method.GET, "/forgot");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is=>" + responseBody);
	}

	@Test
	public void reset() {

		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.header("newPassword", "87654321");

		Response response = httpRequest.request(Method.PUT, "/reset");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}

	@Test
	public void validateUser() {

		RestAssured.baseURI = "http://localhost:8080/user";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko");

		Response response = httpRequest.request(Method.GET, "/validate");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}

	// *****************Label Junit**************

	@Test
	public void createLabel() throws JSONException {

		RestAssured.baseURI = "http://localhost:8080/label";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("labelName", "Junit");

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.body(jsonObject.toString()).contentType("application/json");

		Response response = httpRequest.request(Method.POST, "/createLabel");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is " + responseBody);
	}

	@Test
	public void getLabel() {
		RestAssured.baseURI = "http://localhost:8080/label";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko");

		Response response = httpRequest.request(Method.GET, "/getLabel");
		response.then().log().all().statusCode(200);

		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is" + responseBody);
	}

	@Test
	public void deleteLabel() {

		RestAssured.baseURI = "http://localhost:8080/label";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("labelId", "5db987153f4376351b21d16a");

		Response response = httpRequest.request(Method.DELETE, "/deleteLabel");
		response.then().log().all().statusCode(200);
		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is" + responseBody);
	}

	@Test
	public void updateLabel() {
		RestAssured.baseURI = "http://localhost:8080/label";

		RequestSpecification httpRequest = RestAssured.given().header("token",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoic2hlbGtldmFAZ21haWwuY29tIn0.g9I6ggLKCBuk5sRBhzjWixZTU7IDy-A3E1zSo7gOiko")
				.param("labelId", "5dbbb78b3f437619227398f0").param("labelName", "Junit5");

		Response response = httpRequest.request(Method.PUT, "/updateLabel");
		response.then().log().all().statusCode(200);
		String responseBody = response.getBody().asString();
		System.out.println("ResponseBody is" + responseBody);
	}

}
