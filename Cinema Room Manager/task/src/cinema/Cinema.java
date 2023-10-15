package cinema;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Cinema {
    private final static Scanner SCANNER = new Scanner(System.in);
    private int currentIncome=0;
    private int numOfPurchasedTickets =0;
    private int totalIncome =0;
    private int rows;
    private int seatsInRow;
    private String[][] cinemaSeats;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public void setSeatsInRow(int seatsInRow) {
        this.seatsInRow = seatsInRow;
    }

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.initCinema();
        cinema.menu();
    }

    private void printSeatingArrangement() {
        System.out.println("\nCinema:");
        IntStream.range(1, seatsInRow + 1).forEach(num -> {
            if (num == 1) System.out.print("  " + num);
            else System.out.print(" " + num);
        });
        System.out.print("\n");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seatsInRow; j++) {
                System.out.format(" %s", cinemaSeats[i][j]);
            }
            System.out.print("\n");
        }
    }


    public void initCinema() {
        System.out.println("Enter the number of rows:");
        this.rows = SCANNER.nextInt();
        System.out.println("Enter the number of seats in each row:");
        this.seatsInRow = SCANNER.nextInt();

        this.cinemaSeats = new String[rows][seatsInRow];
        this.totalIncome = totalIncome();
        initSeats(cinemaSeats);
    }

    private int totalIncome() {
        return this.rows * this.seatsInRow <= 60 ? this.rows * this.seatsInRow * 10 : (this.rows / 2 * this.seatsInRow * 10) + ((this.rows - this.rows / 2) * this.seatsInRow * 8);
    }

    public void buyTicket() {
        boolean correctInput = false;
        do {
            int rowNum = 0, seatNum = 0;
            System.out.println("Enter a row number:");
            rowNum = SCANNER.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNum = SCANNER.nextInt();
            if(rowNum>this.rows || seatNum>this.seatsInRow){
                System.out.println("Wrong input!");
                continue;
            }
            if (!this.cinemaSeats[rowNum - 1][seatNum - 1].equals("B") ) {
                correctInput = true;
                int ticketPrice = this.rows * this.seatsInRow <= 60 ? 10 : rowNum <= rows / 2 ? 10 : 8;
                System.out.println("Ticket price: $" + ticketPrice);
                this.cinemaSeats[rowNum - 1][seatNum - 1] = "B";
                this.printSeatingArrangement();
                this.currentIncome+=ticketPrice;
                this.numOfPurchasedTickets++;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        } while (!correctInput);
    }

    private static void initSeats(String[][] cinemaSeats) {
        IntStream.range(0, cinemaSeats.length).forEach(x ->
                IntStream.range(0, cinemaSeats[x].length).forEach(y ->
                        cinemaSeats[x][y] = String.format("S")));
    }

    private void printStatistic() {
        double percentageOfPurchasedTickets = (double) this.numOfPurchasedTickets/(this.rows*this.seatsInRow)*100;
        System.out.format("Number of purchased tickets: %s%n",this.numOfPurchasedTickets);
        System.out.format("Percentage: %.2f%c%n",percentageOfPurchasedTickets,'%');
        System.out.format("Current income: $%s%n",this.currentIncome);
        System.out.format("Total income: $%s%n",this.totalIncome);

    }

    private void menu() {
        outerLoop:
        while (true) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit
                    """);
            int task = SCANNER.nextInt();
            switch (task) {
                case 1 -> this.printSeatingArrangement();
                case 2 -> this.buyTicket();
                case 3 -> this.printStatistic();
                case 0 -> {
                    break outerLoop;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}