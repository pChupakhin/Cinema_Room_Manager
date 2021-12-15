
package com.griddynamics.cinema;

import java.util.Scanner;

public class CinemaMenu {
	
	private final Scanner scanner =  new Scanner(System.in);

	Cinema cinema;
	
	public CinemaMenu() {
		
		System.out.print("Enter the number of rows:\n> ");
        int rows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row:\n> ");
        int seatsInEachRow = scanner.nextInt();
        
        cinema = new Cinema(rows, seatsInEachRow);
        
	}
	
	private void ticketPurchasingMenu() {
    	
        int row;
        int seat;
        String errorMessage;
    	
        do {

            System.out.print("\nEnter a row number:\n> ");
            row = scanner.nextInt() - 1;
            
            System.out.print("Enter a seat number in that row:\n> ");
            seat = scanner.nextInt() - 1;
            
            errorMessage = row >= cinema.ROWS || seat >= cinema.SEATS_IN_EACH_ROW
            		? "Wrong input!\n": cinema.HALL[row][seat] == 'B'
                    ? "That ticket has already been purchased!\n" : "";

            System.out.print('\n' + errorMessage);

        } while(!errorMessage.isEmpty());
        
        System.out.println("Ticket price: $" + cinema.buyTicket(row, seat));
        
	}
	
	
	
	public void cinemaMainMenu() {
		
		int choice;

        do {

            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: cinema.showSeats(); break;
                case 2: this.ticketPurchasingMenu(); break;
                case 3: cinema.statistics(); break;
            }

        } while(choice != 0);
        
	}

}