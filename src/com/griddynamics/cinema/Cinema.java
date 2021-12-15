package com.griddynamics.cinema;

import java.util.stream.Stream;

public class Cinema {
	
    final char[][] HALL;
    final int ROWS;
    final int SEATS_IN_EACH_ROW;
    
    private final int SEATS_COUNT;
    private final int TOTAL_INCOME;

    private int ticketsPurchased = 0;
    private int currentIncome = 0;
    
    
    
    Cinema(int rows, int seatsInEachRow) {
    	
    	ROWS = rows;
    	SEATS_IN_EACH_ROW = seatsInEachRow;

        SEATS_COUNT = ROWS * SEATS_IN_EACH_ROW;
        TOTAL_INCOME = SEATS_COUNT * 10
                - (SEATS_COUNT < 60 ? 0 : (rows + 1) / 2 * seatsInEachRow * 2);

        HALL = Stream.generate(() -> "S".repeat(SEATS_IN_EACH_ROW).toCharArray())
                .limit(ROWS).toArray(x -> new char[ROWS][]);
    }
    
    
    
    private void increaseSoldTicketsAmount() {
    	++ticketsPurchased;
    }
    private void increaseCurrentIncome(int ticketPrice) {
    	currentIncome += ticketPrice;
    }
    private void reserveTheSeat(int row, int seat){
    	HALL[row][seat] = 'B';
    }
    
    
    
    void showSeats() {

        System.out.println("\nCinema:\n"
                + "  1 2 3 4 5 6 7 8 9".substring(0, SEATS_IN_EACH_ROW * 2 + 1));
        for(int i = 0; i < ROWS; ++i)
            System.out.println((i + 1) + " "
                    + String.valueOf(HALL[i]).replaceAll(".", "$0 "));
        System.out.println();

    }



    int buyTicket(int row, int seat) {
    	
       int ticketPrice = SEATS_COUNT < 60 || row < ROWS / 2 ? 10 : 8;
       
       increaseSoldTicketsAmount();
       increaseCurrentIncome(ticketPrice);
       reserveTheSeat(row, seat);
        
       return ticketPrice;
        
    }



    void statistics() {

        System.out.println("\nNumber of purchased tickets: " + ticketsPurchased);
        System.out.printf("Percentage: %.2f%%\n", (float)ticketsPurchased / SEATS_COUNT * 100);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + TOTAL_INCOME);

    }
    
}
