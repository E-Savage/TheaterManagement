package theater;

import java.util.*;

public class TheaterManagement {
	
	// Initialization and declaration of objects
		public static Scanner input = new Scanner(System.in);
		//public static String continueString = null;
	
		// Initialization and declaration of variables and arrays
		public static double totalSales = 0;
		public static double[] rowSales = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		public static char[][] seatChart = new char[15][30];
		public final static double[] ROW_PRICES = new double[15];
		public static int seatsAvailable = 450;
		public static int ticketsSold = 0;
		public static int[] seatsAvailablePerRow = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
		
	
	
	public static void main(String[] args){
		
		theaterLogic();		
	}


	public static void theaterLogic() {
		// Makes the seating chart and assigns values to each position
		makeSeatChart();
		
		// prints the welcome statement for the user to know what is happening during the program
		// Gives the user the "software header"
		welcomeStatement();
		
		getRowPrices();
		
		clearScreen();
		
		printSeatChart();
		
		readyToContinue();
		
		mainLoop();
		
		clearScreen();
		
		finalSales();
	}


	private static void finalSales() {
		System.out.println("\t\tFinal sales of the night");
		printSales();
		
	}


	public static void mainLoop() {
		boolean closeTheater = false;
		
		do {
			clearScreen();
			
			displayMenu();
			
			int userChoice = (int) getNumberInput();
			switch (userChoice) {
				case 1:
					clearScreen();
					saleTickets();
					readyToContinue();
					break;
				case 2:
					clearScreen();
					printAvailableSeats();
					readyToContinue();	
					break;
				case 3:
					clearScreen();
					printSeatPrices();
					readyToContinue();
					break;
				case 4:
					clearScreen();
					printSales();
					readyToContinue();
					break;
				case 5:
					closeTheater = true;
					break;
				default:
					System.out.println("Invalid input.");
					readyToContinue();
					break;
			}
			
		} while(!closeTheater);
	}
	
	
	// This allows the user to purchase tickets
	public static void saleTickets() {
		double thisSale = 0;
		int seatsThisSale = 0;
		int rowNum = 1;
		int seatNum = 1;
		boolean availableSeat = true;
		boolean seatPurchased = true;
		boolean rowAvailable = true;
		
		clearScreen();
		printSeatChart();
		
		if(seatsAvailable == 0) {
			System.out.println("There are no more available seats left in the auditorium."
					+ "\nWe are very sorry!");
			return;
		}
		
		do {
			System.out.print("\nHow many tickets would you like to purchase? \n>>> ");
			seatsThisSale = (int) getNumberInput();
			
			if(seatsThisSale > seatsAvailable) {
				System.out.println("There are only " + seatsAvailable + " seats available, choose a lower number.");
			}
		} while(seatsThisSale >= seatsAvailable);
		
		for (int i = 0; i < seatsThisSale; i++) {
				
			do {
				do {
					System.out.print("\nPlease select a row number. (1-15) \n>>> ");
					//input.next();
					rowNum = (int) getNumberInput();
				
					if(rowNum > seatChart.length) {
						System.out.println("There are only 15 rows, choose a number 1-15");
						rowAvailable = false;
					} else if(seatsAvailablePerRow[rowNum - 1] == 0) {
						System.out.println("This row is full, please choose another row");
						rowAvailable = false;
					} else {
						rowAvailable = true;
					}
				
				} while(!rowAvailable);
				
				do {
					System.out.print("\nPlease select a seat number. (1 - 30) \n>>> ");
					seatNum = (int) getNumberInput();
				
					if (seatNum > seatChart[rowNum - 1].length) {
						System.out.println("There are only 30 seats in the row please choose a lower number");
						availableSeat = false;
					} else if (seatChart[rowNum - 1][seatNum -1] == '*') {
						System.out.println("This seat is not available, please choose another seat.");
						availableSeat = false;
					} else {
						availableSeat = true;
					}
				
				} while(!availableSeat);
				
				System.out.print("\nAre you sure you would like to purchase this seat?"
						+ "\nfor yes enter in y"
						+ "\nfor no enter in n \n>>> ");
				String question = input.next();
				question.trim();
				
				if (question.charAt(0) == 'y' || question.charAt(0) == 'Y') {
					System.out.println("This seat and ticket has been added to your purchase!");
					thisSale += ROW_PRICES[rowNum - 1];
					totalSales += ROW_PRICES[rowNum - 1];
					rowSales[rowNum - 1] += ROW_PRICES[rowNum - 1];
					seatsAvailable -= 1;
					seatsAvailablePerRow[rowNum - 1] -= 1;
					seatChart[rowNum - 1][seatNum - 1] = '*';
					ticketsSold += 1;
					seatPurchased = true;
					
				}else if (question.charAt(0) == 'n' || question.trim().charAt(0) == 'N') {
					System.out.println("This seat and ticket were not added to your your purchase!"
							+ "\n please select another");
					System.out.print("\nWould you like to end the transaction here?,"
							+ "\nplease enter yes or no\n>>> ");
					String answer = input.nextLine();
					answer.toLowerCase();
					answer.trim();
					if(answer.charAt(0) == 'y') {
						System.out.println("Your transaction has been stopped, but other tickets cannot be refunded, if other tickets were purchased .");
						break;
					}
				} else {
					System.out.println("I did not quite understand what you meant, please try again from the start of purchasing a ticket.");
				}
			} while(!seatPurchased);
		}
		
		System.out.println("\nThe total for your sale is: $" + thisSale);
	}
	
	// Allows the user to look at previous prompts before continuing 
	public static void readyToContinue() {
		System.out.println("Type in any key and press enter");
		input.next();
		
	}
	
	// Displays the main menu for the application
	public static void displayMenu() {
		System.out.print("\nUniversity Theater Software Menu"
				       + "\n1. Sale tickets"
				       + "\n2. View Available Seats"
				       + "\n3. View Seating Prices"
				       + "\n4. View Sales"
				       + "\n5. Exit the Program"
				       + "\nENTER YOUR CHOICE (1-5): \n>>> ");
	}

	// Makes the seating chart and assigns values to each position
	// '#' is an unassigned seat
	// '*' is a taken seat
	public static void makeSeatChart() {
		for(int row = 0; row < seatChart.length; row++) {
			for (int seat = 0; seat < seatChart[row].length; seat++) {
				seatChart[row][seat] = '#';
			}
		}
	}
		
	// Asks the user to input a price for each row and makes sure that the user gives 
	// a valid number value for the price
	// it also checks for valid input, the input can only be a number
	// that must be greater than zero		
	private static void getRowPrices() {
		for (int i=0; i < ROW_PRICES.length; i++) {
			System.out.print("\nPlease enter price for Row "+ (i + 1) + "\n>>> ");
			ROW_PRICES[i] = 123; //getNumberInput();
		}
		
		System.out.println();
		readyToContinue();
	}
	
	// Gets valid number input
	// valid number input is any integer or decimal greater than 0
	public static double getNumberInput() {
		boolean valid = false;
		double num = 0;
		do {
			try {
				
			num = input.nextDouble();
			//ROW_PRICES[i] = 1;
			
				
			if (num <= 0) {
					System.out.print("\nYou must enter a positive number greater than 0.\n>>> ");
				}else {
					valid = true;
				}
			} catch (InputMismatchException e) {
				System.out.print("\nInvalid input, Please enter a number\n>>> ");
					input.next(); 
		   		}
			}while (!valid);
		
		input.nextLine();
		return num;
	}
	
	// function to clear the screen of previous prompts, prints 100 line breaks 
	public static void clearScreen() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
	}
	
	// prints the available seats in the theater and lets the person know where seats are available
	public static void printAvailableSeats() {
		
		System.out.println("\t\tSeats Available"
				+ "\nSeats available: " + seatsAvailable
				+ "\nPLEASE NOTE THAT '#' IS AN AVAILABLE SEAT"
				+ "\n'*' IS A TAKEN SEAT");
		System.out.println("\t123456789012345678901234567890");
		
		for(int i = 0; i < seatChart.length; i++) {
			
			System.out.print("Row " + (i+1) + "\t");
			
			for(int j = 0; j < seatChart[i].length; j++) {
				
				System.out.print(seatChart[i][j]);
			}
			System.out.print("\t\t Seats available in this row: " + seatsAvailablePerRow[i]);
			
			System.out.println();
		}
	}
	
	// Prints the prices of the seats for the people to see
	public static void printSeatPrices() {
		System.out.println("\t\tROW PRICES");
		for (int i = 0; i < ROW_PRICES.length; i++) {
			System.out.print("\n Row" + (i + 1) + ": " + ROW_PRICES[i] + "\n");
		}
	}
	
	// prints the sales of the night so that the user can see the sales of everything 
	public static void printSales() {
		System.out.println("\t\tTotal Sales"
				+ "\nTotal Sales: " + totalSales
				+ "\nTickets Sold: " + ticketsSold
				+ "\t\tSales Per Row");
		for (int i = 0; i < rowSales.length; i++) {
			System.out.print("\n Row" + (i + 1) + ": " + rowSales[i] + "\n");
		}
		
	}
	
	// prints the seating chart
	// for the 15 by 30 matrix
	public static void printSeatChart() {
		
		System.out.println("\tSeat Chart"
				+ "\nPLEASE NOTE THAT '#' IS AN AVAILABLE SEAT"
				+ "\n'*' IS A TAKEN SEAT");
		System.out.println("\t123456789012345678901234567890");
		
		for(int i = 0; i < seatChart.length; i++) {
			
			System.out.print("Row " + (i+1) + "\t");
			
			for(int j = 0; j < seatChart[i].length; j++) {
				
				System.out.print(seatChart[i][j]);
			}
			
			System.out.println();
		}
	}
	
	// prints the welcome statement for the user to read
	public static void welcomeStatement() {
		System.out.println("=================================================================================\n"
				+ "\n\t\t\tWELCOME TO THE UNIVERSITY THEATER"
				+ "\n=================================================================================\n"
				+ "\nYou can use this software to keep track of the important information of the theater"
				+ "\nsuch as how many seats are available, how many seats are taken, total sales, and prices."
				+ "\nof the tickets."
				+ "\n\t\t\t**NOTE**"
				+ "\nIf given a selection of options, choose the number associated with that option."
				+ "\n=================================================================================\n"
				+ "Now you will be prompted to enter the values for the 15 rows of seats\n");
	}
}