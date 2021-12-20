package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hall {
    
    private final List<HallRow> hallRows;
    
    public Hall(final int rowsAmount, final int seatsInEachRow) {
        hallRows = Stream.iterate(1, i -> i + 1).map(i -> new HallRow(i, rowsAmount)).limit(seatsInEachRow).toList();
    }
    
    public List<HallRow> getRows() {
        return hallRows;
    }
    
    @Override
    public String toString() {
        String seatsPositionsLine = Stream.iterate(1, i -> i + 1)
                                          .limit(getRows().get(0).getSeatsInEachRow().size())
                                          .map(String::valueOf)
                                          .collect(Collectors.joining(" ", "  ", "\n"));
        
        return seatsPositionsLine + getRows().stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }
    
}
