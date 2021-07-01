package get;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Get {

	@Test
	public void getRequest() {

		RequestSpecification reqSpec = RestAssured.given().baseUri("https://gorest.co.in/public-api")
				.contentType(ContentType.ANY).queryParam("id", "13");
		System.out.println("Request header : ====== " + reqSpec.log().all());

		Response response = reqSpec.when().get("/users");

		/*
		 * byte[] responseinByteArray = response.asByteArray(); // Creating a target
		 * file File targetFileForByteArray = new
		 * File("src/test/resources/targetFileForByteArray.json"); // Writing into files
		 * try { Files.write(responseinByteArray, targetFileForByteArray); } catch
		 * (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 */

		// validation,assertion
		response.then().log().all();
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("data.gender", hasItem("Female"));
		response.then().assertThat().body("data[0].gender", equalTo("Female"));
		response.then().assertThat().body("data[0].id", equalTo(13));

	}

}
