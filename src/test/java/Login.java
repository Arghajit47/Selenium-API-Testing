import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.CommonMethods;

import java.io.File;

import static io.restassured.RestAssured.*;

public class Login {
    static CommonMethods main = new CommonMethods();

    public static void successfulLogin() throws InterruptedException {
        String baseURL = main.readPropertiesFile("baseUrl");
        String LoginPath = main.readPropertiesFile("login");
        File successfulLoginBodyFile = new File("src/test/resources/jsonFolder/successfulLogin.json");
        Response response = given()
                .baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(successfulLoginBodyFile)
                .when()
                .post(LoginPath)
                .then()
                .statusCode(200)
                .extract().response();
        main.writeFile(response, "responses/SuccessfulLoginResponse.json");
    }

    public static void main(String[] args) throws InterruptedException {
        successfulLogin();
    }
}
