package com.griddynamics.cinema;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        Cinema cinema;
        CinemaCli cli;
        
        String errorMessage = "";
        int rows;
        int seatsInEachRow;
        
        do {
            System.out.print(errorMessage);
            
            errorMessage = "";
            rows = seatsInEachRow = 0;
            
            try {
                System.out.print("Enter the number of rows:\n> ");
                rows = scanner.nextInt();
                System.out.print("Enter the number of seats in each row:\n> ");
                seatsInEachRow = scanner.nextInt();
                
                if (rows > 9 || seatsInEachRow > 9 || rows < 1 || seatsInEachRow < 1) {
                    throw new RuntimeException("rows/seats amount must be less than 10 and greater than 0");
                }
            } catch (RuntimeException e) {
                errorMessage = String.format("\n%s\n\n", e.getMessage());
            }
        } while (!errorMessage.isEmpty());
        
        cinema = new Cinema(rows, seatsInEachRow);
        cli = new CinemaCli(cinema);
        cli.interact();
    }
    
}
