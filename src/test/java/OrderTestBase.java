import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.Order;

import static io.restassured.RestAssured.given;

public abstract class OrderTestBase extends TestBase {
    String orderURL = "/api/v1/orders";
    String orderCancelURL = String.format("%s/cancel", orderURL);;

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
                .get(orderURL);
    }

    @Step("Send PUT request to cancel order")
    public Response sendPutRequestCancelOrder(int track){
        return given()
                .queryParam("track", track)
                .when()
                .put(orderCancelURL);
    }
}
