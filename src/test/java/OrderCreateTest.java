import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

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
        checkPositiveResponse(response);
    }

    @Step("Check positive response on create order")
    public void checkPositiveResponse(Response response){
        checkStatusCodeResponse(response, 201);
        response.then()
                .assertThat().body("track", is(greaterThan(0)));
    }
}
