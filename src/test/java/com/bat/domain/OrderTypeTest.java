package com.bat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OrderTypeTest {

    @Test
    public void testOrderTypeEnum() {
        assertEquals(5, OrderType.values().length);

        assertSame(OrderType.LIMIT, OrderType.valueOf("LIMIT"));
        assertSame(OrderType.MARKET, OrderType.valueOf("MARKET"));
        assertSame(OrderType.OCO, OrderType.valueOf("OCO"));
        assertSame(OrderType.TRAILING_STOP, OrderType.valueOf("TRAILING_STOP"));
        assertSame(OrderType.STOP_LIMIT, OrderType.valueOf("STOP_LIMIT"));

        assertThrows(IllegalArgumentException.class, () -> {
            OrderType.valueOf("NONEXISTENT_ORDER");
        });
    }
}