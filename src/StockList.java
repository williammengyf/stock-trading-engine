import java.util.concurrent.atomic.AtomicReference;

public class StockList {
    private final AtomicReference<StockOrder> head = new AtomicReference<>();

    public void add(StockOrder order, boolean isBuy) {
        while (true) {
            StockOrder prev = null;
            StockOrder curr = head.get();

            // Find insertion point based on price priority
            while (curr != null && isOrdered(curr, order, isBuy)) {
                prev = curr;
                curr = curr.getNext();
            }

            order.compareAndSetNext(null, curr);

            // Attempt to update the list
            if (prev == null) {
                if (head.compareAndSet(curr, order)) break;
            } else {
                if (prev.compareAndSetNext(curr, order)) break;
            }
            // Retry traversal due to concurrent modification
        }
    }

    private boolean isOrdered(StockOrder curr, StockOrder newOrder, boolean isBuy) {
        return (isBuy && curr.getPrice() >= newOrder.getPrice()) ||
                (!isBuy && curr.getPrice() <= newOrder.getPrice());
    }

    public StockOrder getHead() {
        return head.get();
    }
}
