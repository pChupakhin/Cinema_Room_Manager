package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HallRow {
    
    private final List<Seat> seatsInEachRow;
    int rowPosition;
    
    public HallRow(final int rowPosition, final int seatsAmount) {
        this.rowPosition = rowPosition;
        seatsInEachRow = Stream.generate(Seat::new).limit(seatsAmount).toList();
    }
    
    public int getRowPosition() {
        return rowPosition;
    }
    
    public List<Seat> getSeatsInEachRow() {
        return seatsInEachRow;
    }
    
    @Override
    public String toString() {
        return getSeatsInEachRow().stream()
                                  .map(String::valueOf)
                                  .collect(Collectors.joining(" ", getRowPosition() + " ", ""));
    }
    
}
