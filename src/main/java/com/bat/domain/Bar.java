package com.bat.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.bat.domain.exceptions.BarException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing a bar in a trading system.
 * <p>
 * The {@code Bar} class models a single bar in a candlestick chart, which is
 * used
 * in technical analysis to represent price movements over a specific time
 * frame.
 * Each bar has an open, close, high, and low price, as well as a time frame and
 * time period defined by an open and close time.
 * </p>
 * <p>
 * There are two constructors:
 * </p>
 * <ul>
 * <li>The first constructor initializes a bar with all attributes
 * specified.</li>
 * <li>The second constructor initializes a bar with the same open, close, high,
 * and low prices.</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * {@code
 * Bar completeBar = new Bar(openPrice, closePrice, highPrice, lowPrice, timeFrame, openTime, closeTime, barPair);
 * Bar initialBar = new Bar(openPrice, barPair, timeFrame, openTime, closeTime);
 * }
 * </pre>
 * 
 * @since 1.0
 * @author SChoumiloff
 */
@Setter
@Getter
@ToString
public class Bar {

    private final UUID id = UUID.randomUUID();

    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private TimeFrame timeFrame;
    private Instant openTime;
    private Instant closeTime;
    private Pair barPair;

    /**
     * Constructor with all attributes.
     *
     * @param open      Opening price.
     * @param close     Closing price.
     * @param high      Highest price.
     * @param low       Lowest price.
     * @param timeFrame Time frame of the bar.
     * @param openTime  Open time of the bar.
     * @param closeTime Close time of the bar.
     * @param barPair   Trading pair associated with the bar.
     */
    public Bar(
            BigDecimal open,
            BigDecimal close,
            BigDecimal high,
            BigDecimal low,
            TimeFrame timeFrame,
            Instant openTime,
            Instant closeTime,
            Pair barPair) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.timeFrame = timeFrame;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.barPair = barPair;
    }

    /**
     * Constructor initializing a bar with the same open, close, high, and low
     * prices.
     *
     * @param open      Opening price.
     * @param pair      Trading pair associated with the bar.
     * @param timeFrame Time frame of the bar.
     * @param openTime  Open time of the bar.
     * @param closeTime Close time of the bar.
     */
    public Bar(
            BigDecimal open,
            Pair pair,
            TimeFrame timeFrame,
            Instant openTime,
            Instant closeTime) {
        this.open = open;
        this.close = open;
        this.high = open;
        this.low = open;
        this.timeFrame = timeFrame;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.barPair = pair;
    }

    /**
     * Validates the bar by checking that the open time is not after the close time,
     * and that the low price is not greater than the high price.
     *
     * @throws BarException if the bar is invalid.
     */
    public void validateBar() throws BarException {
        if (this.openTime.isAfter(this.closeTime))
            throw new BarException("Open time cannot be after close time");
        if (this.low.compareTo(this.high) > 0)
            throw new BarException("Low cannot be greater than high");
        long durationInMinutes = Duration.between(this.openTime, this.closeTime).toMinutes();
        if (durationInMinutes > this.timeFrame.getMinutes())
            throw new BarException(
                    "Time duration cannot exceed time frame (" + this.timeFrame.getMinutes() + " minutes)");
    }
}
