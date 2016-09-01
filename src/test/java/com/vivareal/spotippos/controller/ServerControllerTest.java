package com.vivareal.spotippos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.exception.ExceptionMessage;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServerControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private JacksonTester<Property> propertyJson;

	private JacksonTester<ExceptionMessage> errorJson;

	private JacksonTester<PropertiesResponse> propertiesResponseJson;

	private JacksonTester<PropertiesRequest> propertiesRequestJson;

	private JacksonTester<Map<String,Province>> provincesRequestJson;


	@Before
	public void before() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void testFindValidProperty() throws IOException {
		ResponseEntity<Property> entity = restTemplate.getForEntity("/properties/1", Property.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertyJson.write(entity.getBody())).isEqualToJson("/property.json");
	}

	@Test
	public void testFindNotFoundProperty() throws IOException {
		ResponseEntity<ExceptionMessage> entity = restTemplate.getForEntity("/properties/0", ExceptionMessage.class);
		String error = "{\"exception\":\"EntityNotFoundException\",\"errors\":[\"Could not find property[0]\"]}";
		assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
		assertThat(errorJson.write(entity.getBody())).isEqualToJson(error);
	}

	@Test
	public void testFindInvalidProperty() throws IOException {
		ResponseEntity<ExceptionMessage> entity = restTemplate.getForEntity("/properties/a", ExceptionMessage.class);
		String error = "{\"exception\":\"MethodArgumentTypeMismatchException\","
				+ "\"errors\":[\"Failed to convert value of type [java.lang.String] to required type [java.lang.Long]; "
				+ "nested exception is java.lang.NumberFormatException: For input string: \\\"a\\\"\"]}";
		assertThat(entity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		assertThat(errorJson.write(entity.getBody())).isEqualToJson(error);
	}

	@Test
	public void testFindValidProperties() throws IOException {
		ResponseEntity<PropertiesResponse> entity = restTemplate.getForEntity("/properties?ax=679&ay=680&bx=679&by=680", PropertiesResponse.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertiesResponseJson.write(entity.getBody())).isEqualToJson("/propertiesresponse.json");
	}
	
	@Test
	public void testFindNotFoundProperties() throws IOException {
		ResponseEntity<PropertiesResponse> entity = restTemplate.getForEntity("/properties?ax=1257&ay=928&bx=0&by=0", PropertiesResponse.class);
		String json = "{\"foundProperties\": 0,\"properties\": []}"; 
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertiesResponseJson.write(entity.getBody())).isEqualToJson(json);
	}
	
	@Test
	public void testFindInvalidProperties() throws IOException {
		ResponseEntity<ExceptionMessage> entity = restTemplate.getForEntity("/properties?ax=1257&ay=928&bx=0&by=a", ExceptionMessage.class);
		String error = "{\"exception\":\"MethodArgumentTypeMismatchException\","
				+ "\"errors\":[\"Failed to convert value of type [java.lang.String] to required type [java.lang.Integer]; "
				+ "nested exception is java.lang.NumberFormatException: For input string: \\\"a\\\"\"]}";
		assertThat(entity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		assertThat(errorJson.write(entity.getBody())).isEqualToJson(error);
	}
	
	@Test
	public void testAddValidProperty() throws IOException {
		Property property = propertyJson.readObject("/property.json");
		ResponseEntity<Property> entity = restTemplate.postForEntity("/properties", property, Property.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void testLoadProperties() throws IOException {
		PropertiesRequest request = propertiesRequestJson.readObject("/propertiesrequest.json");
		ResponseEntity<String> entity = restTemplate.postForEntity("/loadProperties", request, String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void testLoadProvinces() throws IOException {
		Map<String,Province> request = provincesRequestJson.readObject("/provincesrequest.json");
		ResponseEntity<String> entity = restTemplate.postForEntity("/loadProvinces", request, String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.CREATED));
	}

}