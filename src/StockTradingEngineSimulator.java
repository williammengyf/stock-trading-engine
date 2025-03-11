import java.util.Random;

public class StockTradingEngineSimulator {
    private static final StockTradingEngine engine = new StockTradingEngine();
    private static final int NUM_TICKERS = 1024;

    public static void simulateTrading(int iterations) {
        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            int ticker = random.nextInt(NUM_TICKERS);
            int quantity = random.nextInt(10) + 1;
            int price = random.nextInt(50) + 100;
            String type = random.nextBoolean() ? "BUY" : "SELL";

            engine.addOrder(type, ticker, quantity, price);
        }
    }

    public static void main(String[] args) {
        simulateTrading(1000);
    }
}
