package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HallRow {

    private final List<Seat> seats;
    private final int rowPosition;

    public HallRow(final int rowPosition, final int seatsAmount) {
        this.rowPosition = rowPosition;
        seats = Stream.generate(Seat::new).limit(seatsAmount).toList();
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getSeatsAmount() {
        return seats.size();
    }

    @Override
    public String toString() {
        return getSeats().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ", getRowPosition() + " ", ""));
    }

}
