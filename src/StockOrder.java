public class StockOrder {
    public enum OrderType { BUY, SELL }
    private final OrderType orderType;
    private final String tickerSymbol;
    private final int quantity;
    private final double price;

    public StockOrder(OrderType orderType, String tickerSymbol, int quantity, double price) {
        this.orderType = orderType;
        this.tickerSymbol = tickerSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
