import org.example.APIMethods;
import org.example.CommonMethods;
import org.testng.annotations.Test;


public class Login {
    static CommonMethods main = new CommonMethods();
    static APIMethods api = new APIMethods();

    @Test
    public static void successfulLogin() throws InterruptedException {
        api.postAPICallWithReqFile("login", "successfulLogin.json", 200, "SuccessfulLoginResponse.json");

    }

}
