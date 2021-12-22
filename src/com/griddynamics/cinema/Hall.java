package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hall {
    
    private static final int LARGE_CINEMA_HALL_MINIMUM_SEATS_AMOUNT = 60;
    
    private final List<HallRow> hallRows;
    
    public Hall(final int rowsAmount, final int seats) {
        hallRows = Stream.iterate(1, i -> i + 1).map(i -> new HallRow(i, rowsAmount)).limit(seats).toList();
    }
    
    public List<HallRow> getRows() {
        return hallRows;
    }
    
    public boolean isLargeCinemaHall() {
        return  getTotalSeatsAmount() >= LARGE_CINEMA_HALL_MINIMUM_SEATS_AMOUNT;
    }
    
    public int getRowsAmount(){
        return hallRows.size();
    }
    
    public int getRowSeatsAmount() {
        return hallRows.get(0).getSeatsAmount();
    }
    
    public int getTotalSeatsAmount() {
        return getRowsAmount() * getRowSeatsAmount();
    }
    
    public int getLastFullPriceSeatsRowPosition() {
        return isLargeCinemaHall() ? getRowSeatsAmount() / 2 : getRowsAmount();
    }
    
    @Override
    public String toString() {
        String seatsPositions = Stream.iterate(1, i -> i + 1)
                .limit(getRows().get(0).getSeats().size())
                .map(String::valueOf)
                .collect(Collectors.joining(" ", "  ", "\n"));
        return seatsPositions + getRows().stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }
    
}
