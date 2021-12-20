package com.griddynamics.cinema;

public class Cinema {
    
    private final Hall hall;
    
    public Cinema(final int rows, final int seatsInEachRow) {
        hall = new Hall(rows, seatsInEachRow);
    }
    
    public Hall getHall() {
        return hall;
    }
    
    @Override
    public String toString() {
        return getHall().toString();
    }
    
}
