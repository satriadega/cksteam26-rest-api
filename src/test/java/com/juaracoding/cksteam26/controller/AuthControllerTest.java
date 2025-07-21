package com.juaracoding.cksteam26.controller;

import com.juaracoding.cksteam26.utils.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    public static final String AUTH_HEADER = "Authorization";
    public static String authorization;
    private JSONObject req;
    private Random rand;
    private DataGenerator dataGenerator;
    private String username;
    private String password;
    private String email;
    private String name;
    private Boolean isOk = true;
    private String otp;

    @BeforeClass
    void init() {
        RestAssured.baseURI = "http://localhost:8081";
        dataGenerator = new DataGenerator();
        rand = new Random();
        req = new JSONObject();
    }

    @Test(priority = 0)
    void regis() {
        Response response;
        try {
            isOk = false;
            username = dataGenerator.dataUsername();
            password = dataGenerator.dataPassword();
            email = dataGenerator.dataEmail();
            name = dataGenerator.dataNamaLengkap();

            req.put("name", name);
            req.put("username", username);
            req.put("email", email);
            req.put("password", password);
            req.put("confirmPassword", password);

            response = given().
                    header("Content-Type", "application/json").
                    header("accept", "*/*").
                    body(req).
                    request(Method.POST, "auth/regis");
            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            otp = jsonPath.getString("data.otp");

            System.out.println("Isi Response Body : " + response.getBody().asPrettyString());

            if (otp != null && intResponse == 200 && email != null) {
                isOk = true;
            }

            Assert.assertEquals(intResponse, 200);
            Assert.assertEquals(jsonPath.getString("data.email"), email);
            Assert.assertEquals(jsonPath.getString("message"), "OTP Terkirim, Cek Email !!");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));

        } catch (Exception e) {
            isOk = false;
        }
    }
}
