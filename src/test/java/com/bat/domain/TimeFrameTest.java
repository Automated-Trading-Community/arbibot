package com.bat.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TimeFrameTest {

    @Test
    public void testTimeFrameEnum() {
        assertEquals(10, TimeFrame.values().length);

        assertSame(TimeFrame.ONE_MINUTE, TimeFrame.valueOf("ONE_MINUTE"));
        assertSame(TimeFrame.THREE_MINUTES, TimeFrame.valueOf("THREE_MINUTES"));
        assertSame(TimeFrame.FIVE_MINUTES, TimeFrame.valueOf("FIVE_MINUTES"));
        assertSame(TimeFrame.FIFTEEN_MINUTES, TimeFrame.valueOf("FIFTEEN_MINUTES"));
        assertSame(TimeFrame.THIRTY_MINUTES, TimeFrame.valueOf("THIRTY_MINUTES"));
        assertSame(TimeFrame.ONE_HOUR, TimeFrame.valueOf("ONE_HOUR"));
        assertSame(TimeFrame.FOUR_HOURS, TimeFrame.valueOf("FOUR_HOURS"));
        assertSame(TimeFrame.ONE_DAY, TimeFrame.valueOf("ONE_DAY"));
        assertSame(TimeFrame.ONE_WEEK, TimeFrame.valueOf("ONE_WEEK"));
        assertSame(TimeFrame.ONE_MONTH, TimeFrame.valueOf("ONE_MONTH"));

        assertEquals(1, TimeFrame.ONE_MINUTE.getMinutes());
        assertEquals("1 minute", TimeFrame.ONE_MINUTE.getDescription());

        assertEquals(1440, TimeFrame.ONE_DAY.getMinutes());
        assertEquals("1 day", TimeFrame.ONE_DAY.getDescription());
    }
}