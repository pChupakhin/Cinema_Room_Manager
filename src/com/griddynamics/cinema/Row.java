package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Row {

    private final List<Seat> seatsInEachRow;

    public Row(final int seatsAmount) {
        seatsInEachRow = Stream.generate(Seat::new).limit(seatsAmount).toList();
    }

    public List<Seat> getSeatsInEachRow() {
        return seatsInEachRow;
    }

    @Override
    public String toString() {
        return seatsInEachRow.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }

}
