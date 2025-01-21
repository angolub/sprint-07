import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.Order;
import model.order.OrderCreateResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class OrderCreateTest extends OrderTestBase {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    private int track;

    public OrderCreateTest(
            String firstName,
            String lastName,
            String address,
            int metroStation,
            String phone,
            int rentTime,
            String deliveryDate,
            String comment,
            List<String> color
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.rentTime = rentTime;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {
                    "Naruto",
                    "Uchiha",
                    "Konoha, 142 apt.",
                    2,
                    "+7 800 355 35 35",
                    5,
                    "2020-06-06",
                    "Saske, come back to Konoha",
                        Arrays.asList("BLACK")
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        2,
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        Arrays.asList("GREY")
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        2,
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        Arrays.asList("BLACK", "GREY")
                },
                {
                        "Naruto",
                        "Uchiha",
                        "Konoha, 142 apt.",
                        2,
                        "+7 800 355 35 35",
                        5,
                        "2020-06-06",
                        "Saske, come back to Konoha",
                        null
                }
        };
    }

    @Test
    public void createNewOrderPositive() {
        String[] color = (this.color != null) ? this.color.toArray(new String[0]) : null;
        Order order = new Order(this.firstName, this.lastName, this.address, this.metroStation, this.phone, this.rentTime, this.deliveryDate, this.comment, color);
        Response response = sendPostRequestCreateOrder(order);
        checkStatusCodeResponse(response, 201);
        OrderCreateResponse orderCreateResponse = response.body().as(OrderCreateResponse.class);
        checkOrderCreateResponse(orderCreateResponse);
        this.track = orderCreateResponse.getTrack();
    }

    @Step("Check positive response on create order")
    public void checkOrderCreateResponse(OrderCreateResponse orderCreateResponse){
        Assert.assertNotNull(orderCreateResponse);
        Assert.assertThat(orderCreateResponse.getTrack(), greaterThan(0));
    }

    @After
    public void teardown() {
        if (this.track > 0) {
            Response response = sendPutRequestCancelOrder(this.track);
            checkStatusCodeResponse(response, 200);
        }
    }
}
