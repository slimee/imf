package com.eigaas.imf;

import com.eigaas.imf.web.AuthenticationRequest;
import com.eigaas.imf.web.MissionForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class IntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    private String tokenSlim;
    private String tokenCedric;

    @Before
    public void setup() {
        RestAssured.port = this.port;
        tokenSlim = given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest.builder().username("slim").password("password").build())
                .when().post("/auth/signin")
                .andReturn().jsonPath().getString("token");
        log.debug("Got tokenSlim:" + tokenSlim);

        tokenCedric = given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest.builder().username("cédric").password("password").build())
                .when().post("/auth/signin")
                .andReturn().jsonPath().getString("token");
        log.debug("Got tokenCedric:" + tokenSlim);
    }

    @Test
    public void getMyMissions() throws Exception {
        given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenSlim)

                .when()
                .get("/v1/missions")

                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testSaveNoAuth() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(MissionForm.builder().missionCodeName("test").build())

                .when()
                .post("/v1/missions")

                .then()
                .statusCode(403);
    }

    @Test
    public void testSaveSlim() throws Exception {
        given()
                .header("Authorization", "Bearer " + tokenSlim)
                .contentType(ContentType.JSON)
                .body(MissionForm.builder().missionCodeName("fail unauthorized").spyCodeName("slim").build())

                .when()
                .post("/v1/missions")

                .then()
                .statusCode(403);
    }

    @Test
    public void testSaveCedric() throws Exception {
        given()
                .header("Authorization", "Bearer " + tokenCedric)
                .contentType(ContentType.JSON)
                .body(MissionForm.builder().missionCodeName("pass the test slim!!").spyCodeName("slim").build())

                .when()
                .post("/v1/missions")

                .then()
                .statusCode(201);
    }

}
