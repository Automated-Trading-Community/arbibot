package com.bat.core.domain.entities;

/**
 * The {@code TimeFrame} enum represents different time frames for trading bars.
 * Each time frame indicates the duration of each bar in the trading chart.
 * 
 * Example time frames include:
 * <ul>
 * <li>{@link #ONE_MINUTE}: 1 minute</li>
 * <li>{@link #THREE_MINUTES}: 3 minutes</li>
 * <li>{@link #FIVE_MINUTES}: 5 minutes</li>
 * <li>{@link #FIFTEEN_MINUTES}: 15 minutes</li>
 * <li>{@link #THIRTY_MINUTES}: 30 minutes</li>
 * <li>{@link #ONE_HOUR}: 1 hour</li>
 * <li>{@link #FOUR_HOURS}: 4 hours</li>
 * <li>{@link #ONE_DAY}: 1 day</li>
 * <li>{@link #ONE_WEEK}: 1 week</li>
 * <li>{@link #ONE_MONTH}: 1 month</li>
 * </ul>
 * 
 * @author SChoumiloff
 * @since 1.0
 * 
 */
public enum TimeFrame {
    /**
     * Represents a 1-minute time frame.
     */
    ONE_MINUTE(1, "1 minute"),

    /**
     * Represents a 3-minute time frame.
     */
    THREE_MINUTES(3, "3 minutes"),

    /**
     * Represents a 5-minute time frame.
     */
    FIVE_MINUTES(5, "5 minutes"),

    /**
     * Represents a 15-minute time frame.
     */
    FIFTEEN_MINUTES(15, "15 minutes"),

    /**
     * Represents a 30-minute time frame.
     */
    THIRTY_MINUTES(30, "30 minutes"),

    /**
     * Represents a 1-hour time frame.
     */
    ONE_HOUR(60, "1 hour"),

    /**
     * Represents a 4-hour time frame.
     */
    FOUR_HOURS(240, "4 hours"),

    /**
     * Represents a 1-day time frame.
     */
    ONE_DAY(1440, "1 day"),

    /**
     * Represents a 1-week time frame.
     */
    ONE_WEEK(10080, "1 week"),

    /**
     * Represents a 1-month time frame.
     */
    ONE_MONTH(43200, "1 month");

    private final int minutes;
    private final String description;

    /**
     * Constructs a {@code TimeFrame} with the specified duration in minutes and description.
     *
     * @param minutes     the duration of the time frame in minutes
     * @param description a human-readable description of the time frame
     */
    TimeFrame(int minutes, String description) {
        this.minutes = minutes;
        this.description = description;
    }

    /**
     * Returns the duration of the time frame in minutes.
     *
     * @return the duration of the time frame in minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns the human-readable description of the time frame.
     *
     * @return the human-readable description of the time frame
     */
    public String getDescription() {
        return description;
    }
}