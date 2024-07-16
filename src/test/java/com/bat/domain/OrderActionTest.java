package com.bat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OrderActionTest {

    @Test
    public void testOrderActionEnum() {
        assertEquals(2, OrderAction.values().length);

        assertSame(OrderAction.BUY, OrderAction.valueOf("BUY"));
        assertSame(OrderAction.SELL, OrderAction.valueOf("SELL"));

        assertThrows(IllegalArgumentException.class, () -> {
            OrderAction.valueOf("NONEXISTENT_ACTION");
        });
    }
}