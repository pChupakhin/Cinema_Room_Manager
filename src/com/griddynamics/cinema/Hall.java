package com.griddynamics.cinema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hall {

    private final List<Row> rows;

    public Hall(final int rowsAmount,final int seatsInEachRow) {
        rows = Stream.generate(() -> new Row(rowsAmount)).limit(seatsInEachRow).toList();
    }

    public List<Row> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        return Stream.iterate(1, i -> i + 1)
                .limit(getRows().get(0).getSeatsInEachRow().size())
                .map(String::valueOf)
                .collect(Collectors.joining(" ", "   ", "\n"))
                + Stream.iterate(1,num -> num + 1)
                .limit(rows.size())
                .map(rowNum -> " " + rowNum + " " + rows.get(rowNum - 1))
                .collect(Collectors.joining("\n"));
    }

}
