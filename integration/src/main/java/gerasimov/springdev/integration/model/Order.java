package gerasimov.springdev.integration.model;

import java.util.List;
import java.util.StringJoiner;

public class Order {
    private List<OrderPosition> positions;

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("positions=" + positions)
                .toString();
    }
}
