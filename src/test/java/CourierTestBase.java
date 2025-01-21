import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.courier.Courier;
import model.courier.CourierLoginResponse;

import static io.restassured.RestAssured.given;

public abstract class CourierTestBase extends TestBase {
    String courierURL = "/api/v1/courier";
    String courierLoginURL = String.format("%s/login", courierURL);
    Courier positiveCourier = new Courier("golub", "1234", "Алина");

    @Step("Send POST request to create courier")
    public Response sendPostRequestCreateCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(courierURL);
    }

    @Step("Send POST request to login courier")
    public Response sendPostRequestLoginCourier(Courier courier){
        courier.setFirstName(null);

        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(courierLoginURL);
    }

    @Step("Send DELETE request to delete courier")
    public Response sendDeleteRequestCourier(int courierId){
        String url = String.format("%s/%d", courierURL, courierId);
        return given().delete(url);
    }

    @Step("Tear down courier data")
    public void tearDownCourier(Courier courier) {
        Response response = sendPostRequestLoginCourier(courier);
        checkStatusCodeResponse(response, 200);

        CourierLoginResponse courierLoginResponse = response.body().as(CourierLoginResponse.class);
        response = sendDeleteRequestCourier(courierLoginResponse.getId());
        checkStatusCodeResponse(response, 200);
    }
}
