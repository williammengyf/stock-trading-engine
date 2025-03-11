import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StockOrder {
    public enum OrderType { BUY, SELL }
    private final OrderType orderType;
    private final int tickerSymbol;
    private final AtomicInteger quantity;
    private final double price;
    private final AtomicReference<StockOrder> next;

    public StockOrder(OrderType orderType, int tickerSymbol, int quantity, double price) {
        this.orderType = orderType;
        this.tickerSymbol = tickerSymbol;
        this.quantity = new AtomicInteger(quantity);
        this.price = price;
        this.next = new AtomicReference<>(null);
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public int getTickerSymbol() {
        return tickerSymbol;
    }

    public AtomicInteger getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public StockOrder getNext() {
        return next.get();
    }

    public boolean compareAndSetNext(StockOrder expected, StockOrder update) {
        // Atomic update for next node
        return next.compareAndSet(expected, update);
    }
}
