package model.order;

public class OrderGetResponse {
    public OrderResponse[] getOrders() {
        return orders;
    }

    public void setOrders(OrderResponse[] orders) {
        this.orders = orders;
    }

    private OrderResponse[] orders;
}
