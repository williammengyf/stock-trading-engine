public class StockList {
    private Node head;

    private class Node {
        StockOrder order;
        Node next;

        Node(StockOrder order) {
            this.order = order;
            this.next = null;
        }
    }

    public void add(StockOrder order) {
        Node node = new Node(order);
        if (head == null) {
            head = node;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
    }

    public StockOrder peek() {
        return head.order;
    }

    public void remove() {
        if (head != null) {
            head = head.next;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }
}
