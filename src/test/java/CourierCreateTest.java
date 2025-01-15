import io.restassured.response.Response;
import model.courier.Courier;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Step;

public class CourierCreateTest extends CourierTestBase {
    @Test
    public void createNewCourierPositive() {
        Response response = sendPostRequestCreateCourier(positiveCourier);
        checkPositiveResponse(response);

        tearDownCourier(positiveCourier);
    }

    @Test
    public void createDublicateCourierNegative() {
        sendPostRequestCreateCourier(positiveCourier);
        try {
            Response response = sendPostRequestCreateCourier(positiveCourier);
            checkStatusCodeResponse(response, 409);
        }
        finally {
            tearDownCourier(positiveCourier);
        }
    }

    @Test
    public void createValidatePasswordNegative() {
        Courier courierNoPassword = new Courier("golub", null, "Алина");
        Response response = sendPostRequestCreateCourier(courierNoPassword);
        checkStatusCodeResponse(response, 400);
    }

    @Test
    public void createValidateLogindNegative() {
        Courier courierNoLogin = new Courier(null, "1234", "Алина");
        Response response = sendPostRequestCreateCourier(courierNoLogin);
        checkStatusCodeResponse(response, 400);
    }

    @Step("Check positive response on create courier")
    public void checkPositiveResponse(Response response){
        checkStatusCodeResponse(response, 201);
        response.then()
                .assertThat().body("ok", is(true));
    }

}

