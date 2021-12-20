package com.griddynamics.cinema;

public class Seat {
    
    private boolean isReserved = false;
    
    public boolean isReserved() {
        return isReserved;
    }
    
    public void reserve() {
        isReserved = true;
    }
    
    @Override
    public String toString() {
        return isReserved() ? "B" : "S";
    }
    
}
