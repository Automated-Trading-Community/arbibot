package com.bat.core.domain.ports.driven;

import java.time.Instant;
import java.util.List;

import com.bat.core.domain.entities.Bar;
import com.bat.core.domain.entities.Pair;
import com.bat.core.domain.entities.TimeFrame;

/**
 * 
 * Port Driven interface for CRUD bar operations
 * 
 * @author SChoumiloff
 * @since 1.0
 */
public interface BarPersistencePort {

    /**
     * Adds a new trading bar to the persistence layer.
     *
     * @param bar the trading bar to add
     * @return the added trading bar
     * 
     */
    Bar addBar(Bar bar);

    /**
     * Removes an existing trading bar from the persistence layer.
     *
     * @param bar the trading bar to remove
     */
    void removeBar(Bar bar);

    /**
     * Retrieves a trading bar based on the specified trading pair, time frame, and timestamp.
     *
     * @param pair      the trading pair to which the bar belongs
     * @param timeFrame the time frame of the bar
     * @param timestamp the timestamp of the bar
     * @return the trading bar that matches the specified criteria, or {@code null} if no such bar exists
     */
    Bar getBar(Pair pair, TimeFrame timeFrame, Instant timestamp);

    /**
     * Retrieves all trading bars for the specified trading pair and time frame.
     *
     * @param pair      the trading pair to which the bars belong
     * @param timeFrame the time frame of the bars
     * @return a list of trading bars that match the specified criteria. Empty list if no such bars exist
     */
    List<Bar> getBars(Pair pair, TimeFrame timeFrame);

    /**
     * Searches for trading bars based on the specified trading pair, time frame, and time range.
     *
     * @param pair      the trading pair to which the bars belong
     * @param timeFrame the time frame of the bars
     * @param startTime the start time for the search period
     * @param endTime   the end time for the search period
     * @return a list of trading bars that match the specified criteria
     * 
     */
    List<Bar> searchBars(Pair pair, TimeFrame timeFrame, Instant startTime, Instant endTime);
}
