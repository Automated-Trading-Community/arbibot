package com.bat.core.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class OrderStatusTest {

    @Test
    public void testOrderStatusValues() {
        OrderStatus executedStatus = OrderStatus.EXECUTED;
        OrderStatus canceledStatus = OrderStatus.CANCELED;
        OrderStatus openStatus = OrderStatus.OPEN;

        assertNotNull(executedStatus);
        assertNotNull(canceledStatus);
        assertNotNull(openStatus);

        assertEquals(OrderStatus.EXECUTED, executedStatus);
        assertEquals(OrderStatus.CANCELED, canceledStatus);
        assertEquals(OrderStatus.OPEN, openStatus);
    }

    @Test
    public void testOrderStatusValueOf() {
        assertEquals(OrderStatus.EXECUTED, OrderStatus.valueOf("EXECUTED"));
        assertEquals(OrderStatus.CANCELED, OrderStatus.valueOf("CANCELED"));
        assertEquals(OrderStatus.OPEN, OrderStatus.valueOf("OPEN"));
    }

    @Test
    public void testOrderStatusToString() {
        assertEquals("EXECUTED", OrderStatus.EXECUTED.toString());
        assertEquals("CANCELED", OrderStatus.CANCELED.toString());
        assertEquals("OPEN", OrderStatus.OPEN.toString());
    }

    @Test
    public void testOrderStatusEnumValues() {
        OrderStatus[] expectedValues = { OrderStatus.EXECUTED, OrderStatus.CANCELED, OrderStatus.OPEN };
        OrderStatus[] actualValues = OrderStatus.values();

        assertEquals(expectedValues.length, actualValues.length);

        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }
}