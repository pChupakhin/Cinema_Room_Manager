package com.griddynamics.cinema;

import java.util.Scanner;

public class CinemaOperator {
    
    private static final int TICKET_FULL_PRICE = 10;
    private static final int TICKET_DISCOUNT_PRICE = 8;
    
    private final Scanner scanner = new Scanner(System.in);
    
    private final Cinema cinema;
    
    public CinemaOperator(final Cinema cinema) {
        this.cinema = cinema;
    }
    
    public Cinema getCinema() {
        return cinema;
    }
    
    public void interact() {
        int userOption;
        
        do {
            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            userOption = scanner.nextInt();
            
            if (userOption < 0 || userOption > 3) {
                System.out.println("input value must be greater than 0 and less than 4");
                continue;
            }
            
            switch (userOption) {
                case 1 -> showSeats();
                case 2 -> ticketPurchasingCLI();
                case 3 -> statistics();
            }
        } while (userOption != 0);
    }
    
    private void ticketPurchasingCLI() {
        int row;
        int seat;
        String errorMessage;
        
        do {
            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            
            System.out.print("\nEnter a row number:\n> ");
            row = scanner.nextInt();
            System.out.print("Enter a seat number in that row:\n> ");
            seat = scanner.nextInt();
    
            errorMessage = row > getCinema().getHall().getRowsAmount()
                    || seat > getCinema().getHall().getRowSeatsAmount()
                    ? "Wrong input!\n": getCinema().getHall().getRows().get(row - 1).getSeats().get(seat - 1).isReserved()
                    ? "That ticket has already been purchased!\n" : "";
            
            System.out.print('\n' + errorMessage);
        } while (!errorMessage.isEmpty());
        
        cinema.getHall().getRows().get(row - 1).getSeats().get(seat - 1).reserve();
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
        return row < getCinema().getHall().getLastFullPriceSeatsRowPosition()
                ? TICKET_FULL_PRICE : TICKET_DISCOUNT_PRICE;
    }
    
    private int getPurchasedTicketsAmount() {
        return (int)getCinema().getHall()
                .getRows()
                .stream()
                .mapToLong(row -> row.getSeats().stream().filter(Seat::isReserved).count())
                .sum();
    }
    
    private double getPercentageOfPurchasedTickets() {
        return (double)getPurchasedTicketsAmount() / getCinema().getHall().getTotalSeatsAmount() * 100;
    }
    
    private int getCurrentIncome() {
        int purchasedFullPriceTicketsAmount = (int)getCinema().getHall()
                .getRows()
                .stream()
                .limit(getCinema().getHall().getLastFullPriceSeatsRowPosition())
                .mapToLong(row -> row.getSeats().stream().filter(Seat::isReserved).count())
                .sum();
        int purchasedDiscountPriceTicketsAmount = (int)getCinema().getHall()
                .getRows()
                .stream()
                .skip(getCinema().getHall().getLastFullPriceSeatsRowPosition())
                .mapToLong(row -> row.getSeats().stream().filter(Seat::isReserved).count())
                .sum();
        return purchasedFullPriceTicketsAmount * TICKET_FULL_PRICE
                + purchasedDiscountPriceTicketsAmount * TICKET_DISCOUNT_PRICE;
    }
    
    private int getTotalIncome() {
        int fullPriceSeatsRowsAmount = getCinema().getHall().getLastFullPriceSeatsRowPosition();
        int discountPriceSeatsRowsAmount = getCinema().getHall().getRowsAmount() - getCinema().getHall().getLastFullPriceSeatsRowPosition();
        return (fullPriceSeatsRowsAmount * TICKET_FULL_PRICE + discountPriceSeatsRowsAmount * TICKET_DISCOUNT_PRICE)
                * getCinema().getHall().getRowSeatsAmount();
    }
    
}
