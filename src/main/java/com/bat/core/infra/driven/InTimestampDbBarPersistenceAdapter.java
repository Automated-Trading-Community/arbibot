package com.bat.core.infra.driven;

import java.time.Instant;
import java.util.List;

import com.bat.core.domain.entities.Bar;
import com.bat.core.domain.entities.Pair;
import com.bat.core.domain.entities.TimeFrame;
import com.bat.core.domain.ports.driven.BarPersistencePort;

public class InTimestampDbBarPersistenceAdapter implements BarPersistencePort {

    @Override
    public Bar addBar(Bar bar) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBar'");
    }

    @Override
    public void removeBar(Bar bar) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeBar'");
    }

    @Override
    public Bar getBar(Pair pair, TimeFrame timeFrame, Instant timestamp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBar'");
    }

    @Override
    public List<Bar> getBars(Pair pair, TimeFrame timeFrame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBars'");
    }

    @Override
    public List<Bar> searchBars(Pair pair, TimeFrame timeFrame, Instant startTime, Instant endTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchBars'");
    }
    
}
