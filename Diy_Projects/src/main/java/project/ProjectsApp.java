package project;


import java.util.List;
import java.util.Scanner;






public class ProjectsApp {
	private static Scanner scan = new Scanner(System.in);
	// @formatter:off
	private List<String> operations = List.of(
			"1) Create and Populate the Tables",
			"2) Add Project",
			"-1) Exit"
			
	);
	// @formatter:on

	public static void main(String[] args) {
		new ProjectsApp().displayMenu();
		
	}
	
	
	private void displayMenu() {
		System.out.println();
		boolean done = false;
		
		while(!done) {
			int op = getOperation();
			switch (op) {
			case 1:
				
			}
		}
	}

/**
 *  
 * @return
 */
	private int getOperation() {
		printOperations();
		Integer op = getIntInput("enter the operation number(just enter to exit)");
		
		
	}


private void printOperations() {
	System.out.println();
	System.out.println("Here's what you can do:");
	operations.forEach(op -> System.out.println(op));
	
	
}


}
