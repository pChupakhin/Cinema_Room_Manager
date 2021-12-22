package com.griddynamics.cinema;

public class Cinema {
    
    private final Hall hall;
    
    public Cinema(final int rows, final int seats) {
        hall = new Hall(rows, seats);
    }
    
    public Hall getHall() {
        return hall;
    }
    
    @Override
    public String toString() {
        return getHall().toString();
    }
    
}
