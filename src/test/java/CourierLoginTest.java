import io.restassured.response.Response;
import model.courier.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Step;

public class CourierLoginTest extends CourierTestBase {
    @Before
    @Override
    public void startUp() {
        super.startUp();
        Response response = sendPostRequestCreateCourier(positiveCourier);
        checkStatusCodeResponse(response, 201);
    }

    @After
    public void teardown() {
        tearDownCourier(positiveCourier);
    }

    @Test
    public void loginCourierPositive() {
        Response response = sendPostRequestLoginCourier(positiveCourier);
        checkPositiveResponse(response);
    }

    @Test
    public void loginCourierNotValidPasswordNegative() {
        Courier courierNotValidPassword = new Courier("golub", "1111", null);
        Response response = sendPostRequestLoginCourier(courierNotValidPassword);
        checkStatusCodeResponse(response, 404);
    }
    @Test
    public void loginCourierEmptyPasswordNegative() {
        Courier courierEmptyLogin = new Courier("golub", null, null);
        Response response = sendPostRequestLoginCourier(courierEmptyLogin);
        checkStatusCodeResponse(response, 400);
    }

    @Test
    public void loginCourierNotValidLoginNegative() {
        Courier courierNotValidLogin = new Courier("golub_111", "1234", null);
        Response response = sendPostRequestLoginCourier(courierNotValidLogin);
        checkStatusCodeResponse(response, 404);
    }

    @Test
    public void loginCourierEmptyLoginNegative() {
        Courier courierEmptyLogin = new Courier(null, "1234", null);
        Response response = sendPostRequestLoginCourier(courierEmptyLogin);
        checkStatusCodeResponse(response, 400);
    }

    @Step("Check positive response on create courier")
    public void checkPositiveResponse(Response response){
        checkStatusCodeResponse(response, 200);
        response.then()
                .assertThat().body("id",  is(greaterThan(0)));
    }
}
