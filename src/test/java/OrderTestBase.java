import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.Order;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public abstract class OrderTestBase extends TestBase {
    String orderURL = "/api/v1/orders";

    @Step("Send POST request to create order")
    public Response sendPostRequestCreateOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(orderURL);
    }

    @Step("Send GET request to get order list")
    public Response sendGetRequestOrder(){
        return given()
                //.queryParam("nearestStation", "1", "2")
                //.queryParam("limit", "5")
                .get(orderURL + "?nearestStation=[\"1\", \"2\"]&limit=5");
    }
}
