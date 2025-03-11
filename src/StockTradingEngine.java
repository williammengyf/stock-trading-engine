public class StockTradingEngine {
    private static final int NUM_TICKERS = 1024;
    private final StockList[] buyOrderBook = new StockList[NUM_TICKERS];
    private final StockList[] sellOrderBook = new StockList[NUM_TICKERS];

    public StockTradingEngine() {
        for (int i = 0; i < NUM_TICKERS; i++) {
            buyOrderBook[i] = new StockList();
            sellOrderBook[i] = new StockList();
        }
    }

    public void addOrder(String type, int ticker, int quantity, double price) {
        StockOrder.OrderType orderType = StockOrder.OrderType.valueOf(type.toUpperCase());
        StockOrder order = new StockOrder(orderType, ticker, quantity, price);
        matchOrder(order, ticker);

        // Add unmatched quantity to the order book
        if (order.getQuantity().get() > 0) {
            StockList targetList = (orderType == StockOrder.OrderType.BUY)
                    ? buyOrderBook[ticker] : sellOrderBook[ticker];
            targetList.add(order, orderType == StockOrder.OrderType.BUY);
        }
        System.out.println("Added " + type + " Order: " + "Ticker " + ticker + " " + quantity + " @ $" + price);
    }

    public void matchOrder(StockOrder order, int ticker) {
        StockList oppositeList = (order.getOrderType() == StockOrder.OrderType.BUY)
                ? sellOrderBook[ticker] : buyOrderBook[ticker];
        StockOrder curr = oppositeList.getHead();
        StockOrder prev = null;

        while (curr != null && order.getQuantity().get() > 0) {
            if (!isPriceMatched(order, curr)) break;

            int available = curr.getQuantity().get();
            if (available <= 0) {
                curr = curr.getNext();
                continue;
            }

            int matchedQty = Math.min(order.getQuantity().get(), available);
            if (curr.getQuantity().compareAndSet(available, available - matchedQty)) {
                order.getQuantity().addAndGet(-matchedQty);
                if (curr.getQuantity().get() == 0 && prev != null) {
                    prev.compareAndSetNext(curr, curr.getNext());
                }
            }

            prev = curr;
            curr = curr.getNext();
        }
    }

    private boolean isPriceMatched(StockOrder a, StockOrder b) {
        return (a.getOrderType() == StockOrder.OrderType.BUY && a.getPrice() >= b.getPrice()) ||
                (a.getOrderType() == StockOrder.OrderType.SELL && a.getPrice() <= b.getPrice());
    }
}
