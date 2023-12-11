package com.markswell.resource;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import com.markswell.dto.UserRequest;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.http.ContentType.JSON;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @Test
    @Order(1)
    @DisplayName("Should create an user successfully")
    public void createUserSuccessfully() {
        var userRequest = new UserRequest("user 1", 18);
        var response = given()
                .contentType(JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @Order(2)
    @DisplayName("should return an error when name isn't valid")
    public void createUserValidationNameNullErrorTest() {
        var response = given()
                .contentType(JSON)
                .body(new UserRequest(null, 18))
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode());
        assertEquals("[The name must not be null or empty]", response.jsonPath()
                .getString("parameterViolations.message"));
    }

    @Test
    @Order(3)
    @DisplayName("should return an error when age isn't valid")
    public void createUserValidationAgeNullErrorTest() {
        var response = given()
                .contentType(JSON)
                .body(new UserRequest("First user", null))
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode());
        assertEquals("[The age must not be null]", response.jsonPath()
                .getString("parameterViolations.message"));
    }

    @Test
    @Order(4)
    @DisplayName("should list all user registered")
    public void listAllSuccessfully() {
        given()
                .contentType(JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));

    }

}