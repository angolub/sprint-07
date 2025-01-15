import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

public abstract class TestBase {
    @Before
    public void startUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru/";
    }

    @Step("Check response satus code")
    public void checkStatusCodeResponse(Response response, int code){
        response.then()
                .statusCode(code);
    }
}
