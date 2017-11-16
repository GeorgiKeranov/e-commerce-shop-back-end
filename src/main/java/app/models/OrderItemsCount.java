package app.models;

public class OrderItemsCount {

    private Long count;

    public OrderItemsCount(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
