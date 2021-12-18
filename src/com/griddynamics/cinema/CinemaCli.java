package com.griddynamics.cinema;

import java.util.Scanner;

public class CinemaCli {
    
    private final Scanner scanner =  new Scanner(System.in);
    
    private static final int ONE_HUNDRED_PERCENT = 100;
    
    private static final int LARGE_CINEMA_HALL_MINIMUM_SEATS_AMOUNT = 60;
    private static final int TICKET_FULL_PRICE = 10;
    private static final int TICKET_DISCOUNT_PRICE = 8;
    
    private final boolean isLargeCinemaHall;
    
    private final int amountOfRows;
    private final int amountOfSeatsInEachRow;
    private final int totalSeatsAmount;
    
    private final int amountOfRowsWithFullPriceSeats;
    private final int amountOfRowsWithDiscountPriceSeats;
    
    private final Cinema cinema;
    
    public CinemaCli(final Cinema cinema) {
        this.cinema = cinema;
        
        amountOfRows = this.cinema.getHall().getRows().size();
        amountOfSeatsInEachRow = this.cinema.getHall().getRows().get(0).getSeatsInEachRow().size();
        totalSeatsAmount = amountOfRows * amountOfSeatsInEachRow;
        
        amountOfRowsWithFullPriceSeats = amountOfRows / 2;
        amountOfRowsWithDiscountPriceSeats = (amountOfRows + 1) / 2;
        
        isLargeCinemaHall = totalSeatsAmount >= LARGE_CINEMA_HALL_MINIMUM_SEATS_AMOUNT;
    }
    
    private int getTotalIncome() {
        return totalSeatsAmount * TICKET_FULL_PRICE -
                (isLargeCinemaHall ? amountOfRowsWithDiscountPriceSeats * amountOfSeatsInEachRow
                        * (TICKET_FULL_PRICE - TICKET_DISCOUNT_PRICE) : 0);
    }
    
    private int getCurrentIncome() {
        int purchasedDiscountTicketsAmount = isLargeCinemaHall ?
                (int)cinema.getHall().getRows()
                        .stream().skip(amountOfRowsWithFullPriceSeats)
                        .mapToLong(row -> row.getSeatsInEachRow()
                                .stream().filter(Seat::isReserved).count())
                        .sum() : 0;
        return getPurchasedTicketsAmount() * TICKET_FULL_PRICE
                - purchasedDiscountTicketsAmount * (TICKET_FULL_PRICE - TICKET_DISCOUNT_PRICE);
    }
    
    private int getTicketPrice(final int row) {
        return isLargeCinemaHall && row > amountOfRowsWithFullPriceSeats
                ? TICKET_DISCOUNT_PRICE : TICKET_FULL_PRICE;
    }
    
    private int getPurchasedTicketsAmount(){
        return (int)cinema.getHall().getRows().stream()
                .mapToLong(row -> row.getSeatsInEachRow().stream()
                        .filter(Seat::isReserved)
                        .count())
                .sum();
    }
    
    private double getPercentageOfPurchasedTickets() {
        return (double)getPurchasedTicketsAmount() / totalSeatsAmount * ONE_HUNDRED_PERCENT;
    }
    
    void statistics() {
        System.out.println("\nNumber of purchased tickets: " + getPurchasedTicketsAmount());
        System.out.printf("Percentage: %.2f%%\n", getPercentageOfPurchasedTickets());
        System.out.println("Current income: $" + getCurrentIncome());
        System.out.println("Total income: $" + getTotalIncome());
    }
    
    public void showSeats() {
        System.out.println("\nCinema\n" + cinema);
    }
    
    private void ticketPurchasingCLI() throws RuntimeException{
        int row;
        int seat;
        String errorMessage;
        
        do {
            System.out.print("\nEnter a row number:\n> ");
            row = scanner.nextInt();
            System.out.print("Enter a seat number in that row:\n> ");
            seat = scanner.nextInt();
            System.out.println("row = " + row + "\tseat = " + seat);
            System.out.println("amuntOfRows = " + amountOfRows + "\tamountOfSeatsINEachRow = " + amountOfSeatsInEachRow);
            errorMessage = row > amountOfRows || seat > amountOfSeatsInEachRow
                    ? "Wrong input!\n"
                    : cinema.getHall().getRows().get(row - 1).getSeatsInEachRow().get(seat - 1).isReserved()
                    ? "That ticket has already been purchased!\n" : "";
            
            System.out.print('\n' + errorMessage);
        } while(!errorMessage.isEmpty());
        
        System.out.printf("Ticket price: $%d\n", getTicketPrice(row));
        cinema.getHall().getRows().get(row - 1).getSeatsInEachRow().get(seat - 1).reserve();
    }
    
    public void interact() {
        int userOption = 0;
        
        do {
            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            try {
                userOption = scanner.nextInt();
                if(userOption < 0 || userOption > 3) {
                    throw new RuntimeException();
                }
                switch (userOption) {
                    case 1 -> showSeats();
                    case 2 -> ticketPurchasingCLI();
                    case 3 -> statistics();
                }
            } catch (RuntimeException e){
                System.out.println("\nInvalid input data. Please retry.");
            }
        } while(userOption != 0);
    }
    
}
