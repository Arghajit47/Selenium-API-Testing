package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class APIMethods {
    CommonMethods main = new CommonMethods();
    public void getAPICall(String propName, Integer expectedStatusCode, String outputFileName) {
        String baseURL = main.readPropertiesFile("baseUrl");
        String listUsersPath = main.readPropertiesFile(propName);
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(listUsersPath)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
        main.writeFile(response, "responses/" + outputFileName);
    }

    public void getAPICall(String propName, String outputFileName) {
        String baseURL = main.readPropertiesFile("baseUrl");
        String listUsersPath = main.readPropertiesFile(propName);
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(listUsersPath)
                .then()
                .extract().response();
        main.writeFile(response, "responses/" + outputFileName);
    }

    public int postAPICall(String propName, String outputFileName, String reqBody, String var) {
        String baseURL = main.readPropertiesFile("baseUrl");
        String path = main.readPropertiesFile(propName);
        File requestBodyFile = new File("src/test/resources/jsonFolder/" + reqBody);
        Response response = given()
                .baseUri(baseURL)
                .body(requestBodyFile)
                .when()
                .post(path)
                .then()
                .extract().response();
        int variable = response.jsonPath().getInt(var);
        main.writeFile(response, "responses/" + outputFileName);
        return variable;
    }

    public void postAPICall(String propName, String outputFileName, String reqBody) {
        String baseURL = main.readPropertiesFile("baseUrl");
        String path = main.readPropertiesFile(propName);
        File requestBodyFile = new File("src/test/resources/jsonFolder/" + reqBody);
        Response response = given()
                .baseUri(baseURL)
                .body(requestBodyFile)
                .when()
                .post(path)
                .then()
                .extract().response();
        main.writeFile(response, "responses/" + outputFileName);
    }

    public void putAPICall(String reqBody, String outputFileName, String path) {
        String baseURL = main.readPropertiesFile("baseUrl");
        File reqBodyFile = new File("src/test/resources/jsonFolder/" + reqBody);
        Response updatePutResponse = given()
                .baseUri(baseURL)
                .body(reqBodyFile)
                .when()
                .put(path)
                .then()
                .extract().response();
        main.writeFile(updatePutResponse, "responses/" + outputFileName);
    }

    public void patchAPICall(String reqBody, String outputFileName, String path) {
        String baseURL = main.readPropertiesFile("baseUrl");
        File reqBodyFile = new File("src/test/resources/jsonFolder/" + reqBody);
        Response updatePutResponse = given()
                .baseUri(baseURL)
                .body(reqBodyFile)
                .when()
                .patch(path)
                .then()
                .extract().response();
        main.writeFile(updatePutResponse, "responses/" + outputFileName);
    }

    public void deleteAPICall(String path, Integer expectedStatusCode) {
        String baseURL = main.readPropertiesFile("baseUrl");
        Response deleteResponse = given()
                .baseUri(baseURL)
                .when()
                .delete(path)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
        System.out.println("API Response Status Code is : " + deleteResponse.statusCode());
        System.out.println(deleteResponse.getBody().asString());
    }

    public void postAPICallWithReqFile(String propName, String reqBody,Integer expectedStatusCode, String outputFileName) {
        String baseURL = main.readPropertiesFile("baseUrl");
        String registerPath = main.readPropertiesFile(propName);
        File reqBodyFile = new File("src/test/resources/jsonFolder/" + reqBody);
        Response response = given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(reqBodyFile)
                .when()
                .post(registerPath)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
        main.writeFile(response, "responses/" + outputFileName);
    }

}
