package com.automated_trading_community.arbibot_infra.exchange.impl.binance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.ExecutionReport;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.PositionBinance;
import com.automated_trading_community.arbibot_infra.exchange.impl.binance.models.UserDataEventBinance;

public class BufferEventTest {
    private BufferEvent<UserDataEventBinance> buffer;

    @BeforeEach
    void setUp() {
        buffer = new BufferEvent<>(5);
    }

    @Test
    void testAddAndGet() {
        ExecutionReport report1 = new ExecutionReport();
        ExecutionReport report2 = new ExecutionReport();
        buffer.add(report1);
        buffer.add(report2);

        assertEquals(2, buffer.getSize());
        assertEquals(report1, buffer.get(0));
        assertEquals(report2, buffer.get(1));
    }

    @Test
    void testBufferOverflow() {
        for (int i = 0; i < 6; i++) {
            buffer.add(new ExecutionReport());
        }

        assertEquals(5, buffer.getSize());
    }

    @Test
    void testGetLast() {
        for (int i = 0; i < 3; i++) {
            buffer.add(new ExecutionReport());
        }
        PositionBinance position = new PositionBinance();
        buffer.add(position);

        List<PositionBinance> lastPositions = buffer.getLast(2,
                PositionBinance.class);

        assertEquals(1, lastPositions.size());
        assertEquals(position, lastPositions.get(0));
    }

    @Test
    void testGetLastWithMoreElements() {
        for (int i = 0; i < 3; i++) {
            buffer.add(new ExecutionReport());
        }
        PositionBinance position1 = new PositionBinance();
        PositionBinance position2 = new PositionBinance();
        buffer.add(position1);
        buffer.add(position2);

        List<PositionBinance> lastPositions = buffer.getLast(3,
                PositionBinance.class);

        assertEquals(2, lastPositions.size());
        assertTrue(lastPositions.contains(position1));
        assertTrue(lastPositions.contains(position2));
    }

    @Test
    void testGetLastWithMoreCountThanCapacity() {
        for (int i = 0; i < 3; i++) {
            buffer.add(new ExecutionReport());
        }
        PositionBinance position1 = new PositionBinance();
        PositionBinance position2 = new PositionBinance();
        buffer.add(position1);
        buffer.add(position2);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            buffer.getLast(10, PositionBinance.class);
        });

        String expectedMessage = "Count: 10, Capacity: 5";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testIsEmpty() {
        assertTrue(buffer.isEmpty());
        buffer.add(new ExecutionReport());
        assertFalse(buffer.isEmpty());
    }

    @Test
    void testIsFull() {
        for (int i = 0; i < 5; i++) {
            buffer.add(new ExecutionReport());
        }
        assertTrue(buffer.isFull());
    }

    @Test
    void testGetOutOfBounds() {
        buffer.add(new ExecutionReport());
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.get(2));
    }
}
