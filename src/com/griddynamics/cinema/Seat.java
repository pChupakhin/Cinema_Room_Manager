package com.griddynamics.cinema;

public class Seat {

    private boolean isReserved = false;
    
    void reserve() {
        isReserved = true;
    }
    
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public String toString() {
        return isReserved ? "B" : "S";
    }
}
