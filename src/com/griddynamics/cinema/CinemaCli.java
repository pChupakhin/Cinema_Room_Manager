package com.griddynamics.cinema;

import java.util.Scanner;

public class CinemaCli {
    
    private static final int ONE_HUNDRED_PERCENT = 100;
    
    private static final int LARGE_CINEMA_HALL_MINIMUM_SEATS_AMOUNT = 60;
    private static final int TICKET_FULL_PRICE = 10;
    private static final int TICKET_DISCOUNT_PRICE = 8;
    
    private final Scanner scanner = new Scanner(System.in);
    
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
    
    public Cinema getCinema() {
        return cinema;
    }
    
    public void interact() {
        int userOption = 0;
        
        do {
            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            try {
                userOption = scanner.nextInt();
                if (userOption < 0 || userOption > 3) {
                    throw new RuntimeException();
                }
                switch (userOption) {
                    case 1 -> showSeats();
                    case 2 -> ticketPurchasingCLI();
                    case 3 -> statistics();
                }
            } catch (RuntimeException e) {
                System.out.println("\nInvalid input data. Please retry.");
            }
        } while (userOption != 0);
    }
    
    private void ticketPurchasingCLI() throws RuntimeException {
        int row;
        int seat;
        String errorMessage;
        
        do {
            System.out.print("\nEnter a row number:\n> ");
            row = scanner.nextInt();
            System.out.print("Enter a seat number in that row:\n> ");
            seat = scanner.nextInt();
            
            errorMessage = row > amountOfRows || seat > amountOfSeatsInEachRow ? "Wrong input!\n"
                    : getCinema().getHall().getRows().get(row - 1).getSeatsInEachRow().get(seat - 1).isReserved()
                            ? "That ticket has already been purchased!\n" : "";
            
            System.out.print('\n' + errorMessage);
        } while (!errorMessage.isEmpty());
        
        cinema.getHall().getRows().get(row - 1).getSeatsInEachRow().get(seat - 1).reserve();
        System.out.printf("Ticket price: $%d\n", getTicketPrice(row));
    }
    
    private void statistics() {
        System.out.println("\nNumber of purchased tickets: " + getPurchasedTicketsAmount());
        System.out.printf("Percentage: %.2f%%\n", getPercentageOfPurchasedTickets());
        System.out.println("Current income: $" + getCurrentIncome());
        System.out.println("Total income: $" + getTotalIncome());
    }
    
    private void showSeats() {
        System.out.println("\nCinema:\n" + getCinema());
    }
    
    private int getTicketPrice(final int row) {
        return isLargeCinemaHall && row > amountOfRowsWithFullPriceSeats ? TICKET_DISCOUNT_PRICE : TICKET_FULL_PRICE;
    }
    
    private int getPurchasedTicketsAmount() {
        return (int)getCinema().getHall()
                               .getRows()
                               .stream()
                               .mapToLong(row -> row.getSeatsInEachRow().stream().filter(Seat::isReserved).count())
                               .sum();
    }
    
    private double getPercentageOfPurchasedTickets() {
        return (double)getPurchasedTicketsAmount() / totalSeatsAmount * ONE_HUNDRED_PERCENT;
    }
    
    private int getCurrentIncome() {
        int purchasedDiscountTicketsAmount = !isLargeCinemaHall ? 0 : (int)getCinema().getHall()
                                                                                      .getRows()
                                                                                      .stream()
                                                                                      .skip(amountOfRowsWithFullPriceSeats)
                                                                                      .mapToLong(row -> row.getSeatsInEachRow()
                                                                                                           .stream()
                                                                                                           .filter(Seat::isReserved)
                                                                                                           .count())
                                                                                      .sum();
        return getPurchasedTicketsAmount() * TICKET_FULL_PRICE
                - purchasedDiscountTicketsAmount * (TICKET_FULL_PRICE - TICKET_DISCOUNT_PRICE);
    }
    
    private int getTotalIncome() {
        return totalSeatsAmount * TICKET_FULL_PRICE - (!isLargeCinemaHall ? 0
                : amountOfRowsWithDiscountPriceSeats * amountOfSeatsInEachRow * (TICKET_FULL_PRICE
                        - TICKET_DISCOUNT_PRICE));
    }
    
}
