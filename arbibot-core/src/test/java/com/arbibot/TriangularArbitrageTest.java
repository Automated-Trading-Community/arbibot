// package com.arbibot;

// import static org.junit.Assert.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// import java.math.BigDecimal;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;

// import com.arbibot.entities.Asset;
// import com.arbibot.entities.Exchange;
// import com.arbibot.entities.ExchangeType;
// import com.arbibot.entities.Order;
// import com.arbibot.entities.OrderType;
// import com.arbibot.entities.Pair;
// import com.arbibot.ports.output.ForExchangeCommunication;
// import com.arbibot.usecases.arbitrage.TriangularArbitrage;
// import com.arbibot.usecases.arbitrage.exceptions.TriangularArbitragingException;

// public class TriangularArbitrageTest {
//     /**
//      * Stub used to perform test independently of the exchange.
//      */
//     private class ForExchangeCommunicationStub implements ForExchangeCommunication {
//         private Map<Pair, BigDecimal> prices = new HashMap<>();
//         private Map<Pair, Order> passedOrders = new HashMap<>();

//         @Override
//         public void getPriceForPair(Pair pair, Exchange exchange) {
//             if (prices.containsKey(pair)) {
//                 pair.setPrice(prices.get(pair));
//             } else {
//                 pair.setPrice(null);
//             }
//         }

//         @Override
//         public void passOrders(Order[] orders) {
//             for (Order order : orders) {
//                 passedOrders.put(order.getPair(), order);
//             }
//         }

//         public void setPrice(Pair pair, BigDecimal price) {
//             prices.put(pair, price);
//         }

//         public Map<Pair, Order> getPassedOrders() {
//             return passedOrders;
//         }

//         @Override
//         public BigDecimal getBalanceForAsset(Asset asset, Exchange exchange) {
//             // TODO Auto-generated method stub
//             throw new UnsupportedOperationException("Unimplemented method 'getBalanceForAsset'");
//         }

//         @Override
//         public Order getOrderInfo(String orderId, Exchange exchange) {
//             // TODO Auto-generated method stub
//             throw new UnsupportedOperationException("Unimplemented method 'getOrderInfo'");
//         }

//         @Override
//         public List<Pair> getAvailableTradingPairs(Exchange exchange) {
//             // TODO Auto-generated method stub
//             throw new UnsupportedOperationException("Unimplemented method 'getAvailableTradingPairs'");
//         }
//     }

//     private ForExchangeCommunicationStub forExchangeCommunicationStub;
//     private Exchange exchange;
//     private TriangularArbitrage triangularArbitrage;

//     private Pair pair1;
//     private Pair pair2;
//     private Pair pair3;

//     private Asset btc;
//     private Asset eth;
//     private Asset usdt;

//     private List<Pair> cexPairs;

//     @BeforeEach
//     public void setUp() {
//         this.forExchangeCommunicationStub = new ForExchangeCommunicationStub();
//         btc = Asset.create("BTC");
//         eth = Asset.create("ETH");
//         usdt = Asset.create("USDT");

//         pair1 = new Pair(eth, usdt);
//         pair2 = new Pair(eth, btc);
//         pair3 = new Pair(btc, usdt);
//         cexPairs = Arrays.asList(pair1, pair2, pair3);
//         this.exchange = new Exchange("Binance", "https://binance.com", cexPairs, BigDecimal.valueOf(0.1), usdt,
//                 ExchangeType.CEX);

//         triangularArbitrage = new TriangularArbitrage(this.forExchangeCommunicationStub);
//     }

//     @Test
//     public void testValidTriangle() {

//         assertDoesNotThrow(() -> triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3, exchange,
//                 BigDecimal.valueOf(25)));
//     }

//     @Test
//     public void testNotValidTriangle() {
//         TriangularArbitragingException error = assertThrows(TriangularArbitragingException.class,
//                 () -> triangularArbitrage.performTriangualarArbitrage(pair2,
//                         pair1, pair3, exchange, BigDecimal.valueOf(20)));
//         assertTrue(error.getMessage().equals("Triangle or asset buy is not valid"));
//     }

//     @Test
//     public void testPricesNullAssert() {
//         AssertionError error = assertThrows(AssertionError.class,
//                 () -> triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
//                         exchange, BigDecimal.valueOf(20)));
//         assertTrue(error.getMessage().equals(pair1.toString() + " price is null"));
//     }

//     @Test
//     public void testPricesPair2NullAssert() {
//         this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(2503.4));
//         AssertionError error = assertThrows(AssertionError.class,
//                 () -> triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
//                         exchange, BigDecimal.valueOf(20)));
//         assertTrue(error.getMessage().equals(pair2.toString() + " price is null"));
//     }

//     @Test
//     public void testPricesPair3NullAssert() {
//         this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(2503.4));
//         this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
//         AssertionError error = assertThrows(AssertionError.class,
//                 () -> triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
//                         exchange, BigDecimal.valueOf(20)));
//         assertTrue(error.getMessage().equals(pair3.toString() + " price is null"));
//     }

//     @Test
//     public void testPerformTriangularArbitrageNoOpportunities() throws TriangularArbitragingException {
//         this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(2070.4));
//         this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
//         this.forExchangeCommunicationStub.setPrice(pair3, BigDecimal.valueOf(40000.3));

//         triangularArbitrage.performTriangualarArbitrage(pair1, pair2, pair3,
//                 exchange, BigDecimal.valueOf(20));
//         assertTrue(this.forExchangeCommunicationStub.getPassedOrders().size() == 0);
//     }

//     @Test
//     public void testPerformTriangularArbitrageOpportunitie() throws TriangularArbitragingException {
//         this.forExchangeCommunicationStub.setPrice(pair1, BigDecimal.valueOf(1980));
//         this.forExchangeCommunicationStub.setPrice(pair2, BigDecimal.valueOf(0.05));
//         this.forExchangeCommunicationStub.setPrice(pair3, BigDecimal.valueOf(40000));

//         triangularArbitrage.performTriangualarArbitrage(pair3, pair2, pair1, exchange, BigDecimal.valueOf(20));

//         assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair1).getType().equals(OrderType.BUY));
//         assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair1).getType().equals(OrderType.SELL));
//         assertTrue(this.forExchangeCommunicationStub.getPassedOrders().get(pair1).getType().equals(OrderType.SELL));
//         assertTrue(this.forExchangeCommunicationStub.getPassedOrders().size() == 3);

//         // TODO tester si les ordres ont été rempli mais je ne vois pas trop comment
//         // tester cette fonctionnalité...
//     }
// }
