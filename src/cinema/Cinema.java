package cinema;

import java.util.Scanner;
import java.util.stream.Stream;

public class Cinema {




    private static final Scanner s = new Scanner(System.in);

    private static final char[][] hall;
    private static final int rows;
    private static final int seatsInEachRow;
    private static final int seatsCount;
    private static final int totalIncome;

    private static int ticketsPurchased = 0;
    private static int currentIncome = 0;



    static {

        System.out.print("Enter the number of rows:\n> ");
        rows = s.nextInt();
        System.out.print("Enter the number of seats in each row:\n> ");
        seatsInEachRow = s.nextInt();

        seatsCount = rows * seatsInEachRow;
        totalIncome = seatsCount * 10
                - (seatsCount < 60 ? 0 : (rows + 1) / 2 * seatsInEachRow * 2);

        hall = Stream.generate(() -> "S".repeat(seatsInEachRow).toCharArray())
                .limit(rows).toArray(x -> new char[rows][]);

    }



    private static void showSeats() {

        System.out.println("\nCinema:\n"
                + "  1 2 3 4 5 6 7 8 9".substring(0, seatsInEachRow * 2 + 1));
        for(int i = 0; i < rows; ++i)
            System.out.println((i + 1) + " "
                    + String.valueOf(hall[i]).replaceAll(".", "$0 "));
        System.out.println();

    }



    private static void buyTicket() {

        int row;
        int seat;
        int ticketPrice;
        String errorMessage;

        do {

            System.out.print("\nEnter a row number:\n> ");
            row = s.nextInt() - 1;
            System.out.print("Enter a seat number in that row:\n> ");
            seat = s.nextInt() - 1;

            errorMessage = row >= rows || seat >= seatsInEachRow
                    ? "Wrong input!\n" : hall[row][seat] == 'B'
                    ? "That ticket has already been purchased!\n" : "";

            System.out.print('\n' + errorMessage);

        } while(!errorMessage.isEmpty());

        ticketPrice = seatsCount < 60 || row < rows / 2 ? 10 : 8;
        currentIncome += ticketPrice;
        ++ticketsPurchased;
        hall[row][seat] = 'B';

        System.out.println("Ticket price: $" + ticketPrice);

    }



    private static void statistics() {

        System.out.println("\nNumber of purchased tickets: " + ticketsPurchased);
        System.out.printf("Percentage: %.2f%%\n", (float)ticketsPurchased / seatsCount * 100);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);

    }



    public static void main(String[] args) {

        int choice;

        do {

            System.out.print("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n> ");
            choice = s.nextInt();

            switch (choice) {
                case 1: showSeats(); break;
                case 2: buyTicket(); break;
                case 3: statistics(); break;
            }

        } while(choice != 0);

    }



}
