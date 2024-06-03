package project;

import java.util.List;
import java.util.Scanner;

public class application {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
	}
	public void processUserInput(Scanner scan) {
		boolean done = false;
//		@formatter:off
			List<String> operations = List.of(
					"1) Add a Project",
					"-1) Exit"
					
			);
		//	@formatter:on
		
		while (!done == false) {
			System.out.println("Please select one of the following options by their number.");
			displayMenu(operations);
			String choice = scan.nextLine();
				switch (choice) {
				case "1": 
					
					continue;
				case "-1":
					done = true;
					continue;
				default:
					System.out.println("Selection invalid. Please select a number from the menu.");
				}
			}
 		}

	public void displayMenu(List<String> menu) {
		for (String option : menu) {
			System.out.println(option);
		}
	}
}
