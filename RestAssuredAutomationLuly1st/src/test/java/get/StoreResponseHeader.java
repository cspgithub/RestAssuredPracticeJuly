package get;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StoreResponseHeader {

	@Test
	public void storeResponseHeader() {

		// Add header as a Map
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Content-Type", "application/json");
		// Add query as a Map
		Map<String, String> queryParam = new HashMap<>();
		requestHeaders.put("id", "13");

		RequestSpecification reqSpec = RestAssured.given().baseUri("https://gorest.co.in/public-api")
				.headers(requestHeaders).queryParams(queryParam);

		System.out.println("Request header : ====== " + reqSpec.log().all());

		Response response = reqSpec.when().get("/users");
		// write to file : "responseHeader.json"
		Headers allHeaders = response.headers();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Header header : allHeaders) {

			map.put(header.getName(), header.getValue());
		}
		ObjectMapper mapper = new ObjectMapper();
		// write JSON to a file
		try {
			mapper.writeValue(new File("src/test/resources/responseHeader.json"), map);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// write to file : "responseHeader"
		try {
			File responseHeaderFile = new File("src/test/resources/responseHeader.txt");
			FileOutputStream fos = new FileOutputStream(responseHeaderFile);
			PrintWriter pw = new PrintWriter(fos);

			for (Map.Entry<String, Object> m : map.entrySet()) {
				pw.println(m.getKey() + "=" + m.getValue());
			}

			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
		}
	}

}
