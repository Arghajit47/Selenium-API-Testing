import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class APITesting {
    static CommonMethods main = new CommonMethods();
    static APIMethods api = new APIMethods();

    @Test
    public static void listUsers() throws InterruptedException {
        api.getAPICall("listUsers", 200, "ListUsersResponse.json");
    }

    @Test
    public static void singleUsers() throws InterruptedException {
        api.getAPICall("singleUser", 200, "SingleUserResponse.json");
    }

    @Test
    public static void userNotFound() throws InterruptedException {
        api.getAPICall("userNotFound", 404, "UserNotFoundResponse.json");

    }

    @Test
    public static void listResource() throws InterruptedException {
        api.getAPICall("listResource", "ListResourceResponse.json");
    }

    @Test
    public static void singleResource() throws InterruptedException {
        api.getAPICall("singleResource", "SingleResourceResponse.json");

    }

    @Test
    public static void singleResourceNotFound() throws InterruptedException {
        api.getAPICall("singleResourceNotFound", "SingleResourceNotFoundResponse.json");
    }

    @Test
    public static void userAction() throws InterruptedException {
        int id = api.postAPICall("createUser", "CreateUserResponse.json", "createUser.json", "id");

        api.putAPICall("updateUser.json", "UpdateUserByPutResponse.json", main.readPropertiesFile("createUser") + "/" + id);

        api.patchAPICall("updateUser.json", "UpdateUserByPutResponse.json", main.readPropertiesFile("createUser") + "/" + id);

        api.deleteAPICall(main.readPropertiesFile("createUser") + "/" + id, 204);

    }

    @Test
    public static void successfulRegister() throws InterruptedException {
        api.postAPICallWithReqFile("register", "successfulRegister.json", 200, "SuccessfulRegisterResponse.json");

    }

    @Test
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

    @Test
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

    @Test
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

}
