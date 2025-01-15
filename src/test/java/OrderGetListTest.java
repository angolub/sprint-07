import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.OrderGetResponse;
import model.order.OrderResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderGetListTest extends  OrderTestBase{
    @Test
    public void getOrders() {
        Response response = sendGetRequestOrder();
        checkPositiveResponse(response);
    }

    @Step("Check positive response on create order")
    public void checkPositiveResponse(Response response){
        checkStatusCodeResponse(response, 200);
        OrderGetResponse orderGetResponse = response.body().as(OrderGetResponse.class);
        Assert.assertNotNull(orderGetResponse);
        Assert.assertNotNull(orderGetResponse.getOrders());
        Assert.assertThat(orderGetResponse.getOrders().length, greaterThan(0));
        OrderResponse order = orderGetResponse.getOrders()[0];
        Assert.assertThat(order.getId(), greaterThan(0));
    }
}
