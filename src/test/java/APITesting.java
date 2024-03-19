import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.CommonMethods;
import java.io.File;
import static io.restassured.RestAssured.*;


public class APITesting {
    static CommonMethods main = new CommonMethods();

    public static void listUsers() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String listUsersPath = main.readPropertiesFile("listUsers");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(listUsersPath)
                .then()
                .statusCode(200)
                .extract().response();
        main.writeFile(response, "responses/ListUsers.json");

    }

    public static void singleUsers() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String singleUserPath = main.readPropertiesFile("singleUser");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(singleUserPath)
                .then()
                .statusCode(200)
                .extract().response();
        main.writeFile(response, "responses/SingleUser.json");

    }

    public static void userNotFound() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String userNotFoundPath = main.readPropertiesFile("userNotFound");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(userNotFoundPath)
                .then()
                .statusCode(404)
                .extract().response();
        main.writeFile(response, "responses/UserNotFound.json");

    }

    public static void listResource() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String listResourcePath = main.readPropertiesFile("listResource");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(listResourcePath)
                .then()
                .extract().response();
        main.writeFile(response, "responses/ListResource.json");

    }

    public static void singleResource() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String singleResourcePath = main.readPropertiesFile("singleResource");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(singleResourcePath)
                .then()
                .extract().response();
        main.writeFile(response, "responses/SingleResource.json");

    }

    public static void singleResourceNotFound() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String singleResourceNotFoundPath = main.readPropertiesFile("singleResourceNotFound");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(singleResourceNotFoundPath)
                .then()
                .extract().response();
        main.writeFile(response, "responses/SingleResourceNotFound.json");

    }

    public static void userAction() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String createUserPath = main.readPropertiesFile("createUser");
        File requestBodyFile = new File("src/test/resources/jsonFolder/createUser.json");
        Response createResponse = given()
                .baseUri(baseURL)
                .body(requestBodyFile)
                .when()
                .post(createUserPath)
                .then()
                .extract().response();
        int id = createResponse.jsonPath().getInt("id");
        main.writeFile(createResponse, "responses/CreateUserResponse.json");



        File updatePutBodyFile = new File("src/test/resources/jsonFolder/updateUser.json");
        Response updatePutResponse = given()
                .baseUri(baseURL)
                .body(updatePutBodyFile)
                .when()
                .put(createUserPath + "/" + id)
                .then()
                .extract().response();
        main.writeFile(updatePutResponse, "responses/UpdateUserByPutResponse.json");



        File updatePatchBodyFile = new File("src/test/resources/jsonFolder/updateUser.json");
        Response updatePatchResponse = given()
                .baseUri(baseURL)
                .body(updatePatchBodyFile)
                .when()
                .put(createUserPath + "/" + id)
                .then()
                .extract().response();
        main.writeFile(updatePatchResponse, "responses/UpdateUserByPatchResponse.json");



        Response deleteResponse = given()
                .baseUri(baseURL)
                .when()
                .delete(createUserPath + "/" + id)
                .then()
                .statusCode(204)
                .extract().response();
        System.out.println("API Response Status Code is : " + deleteResponse.statusCode());
        System.out.println(deleteResponse.getBody().asString());

    }

    public static void successfulRegister() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String registerPath = main.readPropertiesFile("register");
        File successfulRegisterBodyFile = new File("src/test/resources/jsonFolder/successfulRegister.json");
        Response response = given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(successfulRegisterBodyFile)
                .when()
                .post(registerPath)
                .then()
                .statusCode(200)
                .extract().response();
        main.writeFile(response, "responses/SuccessfulRegisterResponse.json");

    }

    public static void unsuccessfulRegister() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String registerPath = main.readPropertiesFile("register");
        String email = "eve.holt@reqres.in";
        String requestBody = "{\"email\": \"" + email + "}";
        Response response = given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(registerPath)
                .then()
                .statusCode(400)
                .extract().response();
        main.writeFile(response, "responses/UnsuccessfulRegisterResponse.json");
    }

    public static void unsuccessfulLogin() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String loginPath = main.readPropertiesFile("login");
        String email = "peter@klaven";
        String requestBody = "{\"email\": \"" + email + "}";
        Response response = given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(loginPath)
                .then()
                .statusCode(400)
                .extract().response();
        main.writeFile(response, "responses/UnsuccessfulLoginResponse.json");

    }

    public static void delayedResponse() throws Exception {
        String baseURL = main.readPropertiesFile("baseUrl");
        String delayedResponsePath = main.readPropertiesFile("delayedResponse");
        Response response = given()
                .baseUri(baseURL)
                .when()
                .get(delayedResponsePath)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
        Thread.sleep(3000);
        main.writeFile(response, "responses/DelayedResponse.json");
    }

    public static void main(String[] args) throws Exception {
        listUsers();
        singleUsers();
        userNotFound();
        listResource();
        singleResource();
        singleResourceNotFound();
        userAction();
        successfulRegister();
        unsuccessfulRegister();
        unsuccessfulLogin();
        delayedResponse();
    }
}
