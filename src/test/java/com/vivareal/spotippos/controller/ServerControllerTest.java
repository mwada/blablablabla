package com.vivareal.spotippos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.exception.ExceptionMessage;
import com.vivareal.spotippos.model.Property;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "province.file = /provincesTest.json", "property.file = /propertiesTest.json" })
public class ServerControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private JacksonTester<Property> propertyJson;

	private JacksonTester<PropertiesResponse> propertiesResponseJson;

	private JacksonTester<ExceptionMessage> errorJson;

	@Before
	public void before() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void testFindValidProperty() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties/1", String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertyJson.write(propertyJson.parse(entity.getBody()).getObject()))
				.isEqualToJson("/property.json");
	}

	@Test
	public void testFindNotFoundProperty() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties/0", String.class);
		String error = "{\"exception\":\"EntityNotFoundException\",\"errors\":[\"Could not find property[0]\"]}";
		assertThat(errorJson.write(errorJson.parse(entity.getBody()).getObject())).isEqualToJson(error);
		assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testFindInvalidProperty() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties/a", String.class);
		String error = "{\"exception\":\"MethodArgumentTypeMismatchException\","
				+ "\"errors\":[\"Failed to convert value of type [java.lang.String] to required type [java.lang.Long]; "
				+ "nested exception is java.lang.NumberFormatException: For input string: \\\"a\\\"\"]}";
		assertThat(errorJson.write(errorJson.parse(entity.getBody()).getObject())).isEqualToJson(error);
		assertThat(entity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	@Test
	public void testFindValidProperties() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties?ax=1257&ay=928&bx=1257&by=928", String.class);
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertiesResponseJson.write(propertiesResponseJson.parse(entity.getBody()).getObject()))
				.isEqualToJson("/propertiesResponse.json");
	}
	
	@Test
	public void testFindNotFoundProperties() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties?ax=1257&ay=928&bx=0&by=0", String.class);
		String json = "{\"foundProperties\": 0,\"properties\": []}"; 
		assertThat(entity.getStatusCode(), is(HttpStatus.OK));
		assertThat(propertiesResponseJson.write(propertiesResponseJson.parse(entity.getBody()).getObject()))
				.isEqualToJson(json);
	}
	
	@Test
	public void testFindInvalidProperties() throws IOException {
		ResponseEntity<String> entity = restTemplate.getForEntity("/properties?ax=1257&ay=928&bx=0&by=a", String.class);
		String error = "{\"exception\":\"MethodArgumentTypeMismatchException\","
				+ "\"errors\":[\"Failed to convert value of type [java.lang.String] to required type [java.lang.Integer]; "
				+ "nested exception is java.lang.NumberFormatException: For input string: \\\"a\\\"\"]}";
		assertThat(errorJson.write(errorJson.parse(entity.getBody()).getObject())).isEqualToJson(error);
		assertThat(entity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
}