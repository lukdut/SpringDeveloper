package gerasimov.springdev.integration.model;

import java.util.StringJoiner;

public class OrderPosition {
    private double price;
    private String item;
    private int count;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderPosition.class.getSimpleName() + "[", "]")
                .add("price=" + price)
                .add("item='" + item + "'")
                .add("count=" + count)
                .toString();
    }
}
