package com.griddynamics.cinema;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        boolean isValidRowsAndSeatsAmount;
        int rows;
        int seatsInEachRow;

        do {
            System.out.print("Enter the number of rows:\n> ");
            rows = scanner.nextInt();
            System.out.print("Enter the number of seats in each row:\n> ");
            seatsInEachRow = scanner.nextInt();

            if (rows > 9 || seatsInEachRow > 9 || rows < 1 || seatsInEachRow < 1) {
                System.out.println("rows amount and seats amount must be greater than 0 and less than 10");
                isValidRowsAndSeatsAmount = false;
            } else {
                isValidRowsAndSeatsAmount = true;
            }
        } while (!isValidRowsAndSeatsAmount);

        final Cinema cinema = new Cinema(rows, seatsInEachRow);
        final CinemaOperator cli = new CinemaOperator(cinema);
        cli.interact();
    }

}
