# Stock Trading Engine

## Overview

This project implements a real-time stock trading engine designed to match buy and sell orders for stocksbased on specific criteria such as price and quantity.

## Key Components

The system is composed of several core modules, each responsible for a distinct aspect of stock trading:

- **StockOrder**: Represents an individual stock order, encapsulating details such as:
	- Order Type (Buy or Sell)
	- Ticker Symbol
	- Quantity
	- Price
- **StockList**: Manages a collection of orders for a specific ticker and order type (buy or sell). It uses a lock-free linked list to maintain orders in a sorted sequence.
- **StockTradingEngine**: The central component that maintains the trading process, with functionalities including:
	- Adds new orders to the appropriate order book
	- Matches buy and sell orders based on predefined rules
	- Manages separate order books for multiple tickers
- **StockTradingEngineSimulator**: A testing utility that simulates real-world trading by generating random orders, allowing developers to evaluate the engine's functionality and performance under load.

## Concurrency Handling

The `StockList` employs `AtomicReference` and `AtomicInteger` from Java’s concurrency package to manage the linked list without traditional locks. This minimizes contention and enhances throughput. Compare-And-Swap (CAS) operations are used during executions, preventing race conditions without the overhead of locking.

## Matching Mechanism

The system employs a straightforward yet effective matching mechanism:

- **Price Priority**:
	- Buy orders are matched with the lowest-priced sell order available.
	- Sell orders are matched with the highest-priced buy order available.
- **Sorted List**: The pre-sorted `StockList` ensures that the best-priced order is always at the head of the list, enabling efficient matching.

## Performance

Performance is a critical focus given the real-time nature of the system:

- **Time Complexity**: The matching process runs in O(n) time, where n is the number of orders in the relevant `StockList`.
- **Lock-Free Design**: Eliminating locks reduces synchronization overhead, making the system highly responsive even under heavy concurrent load.