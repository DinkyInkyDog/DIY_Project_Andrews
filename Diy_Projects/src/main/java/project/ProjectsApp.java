package project;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import project.exception.DbException;
import project.service.ProjectService;
import projects.entity.Material;
import projects.entity.Project;







public class ProjectsApp {
	private Scanner scan = new Scanner(System.in);
	private ProjectService ps = new ProjectService();
	private int curProject; 
	// @formatter:off
	private List<String> operations = List.of(
			"1) Create and Populate the Tables",
			"2) Add Project",
			"3) Select Project from Id",
			"4) List All Projects",
			"5) Add Material to selected Project",
			"6) Modify selected Project",
			"7) Delete selected Project"
			
			
			
	);
	// @formatter:on

	public static void main(String[] args) {
		new ProjectsApp().displayMenu();
		
	}
	
	
	private void displayMenu() {
		System.out.println();
		boolean done = false;
		
		while(!done) {
			try {
			int op = getOperation();
			switch (op) {
			case 1:
				uploadTables();
				break;
			case 2:
				createProject();
				break;
			case 3:
				viewProjectFromId();
				break;
			case 4:
				listProjects();
				break;
			case 5:
				createMaterial();
				break;
			case 6:
				modifyProject();
			case -1:
				done = quitMenu();
				break;
			default:
				System.out.println("\n" + op + " is invalid. Try again.");
				break;
			}
			}catch (Exception e) {
				System.out.println("\nError: "+ e.toString() + " try again.");
			}
		}
	}



/**
 * This method allows the user to pick a field they wish to update in the selected project and put in the information
 * the change it to. You can do multiple fields in one update. if no changes are selected it goes back to 
 * the main selection menu
 */
private void modifyProject() {
		try {
			System.out.println("What would you like to change about the Project?");
			List<String> columns = List.of(
					"1) Project name",
					"2) Estimated hours",
					"3) Actual hours",
					"4) Difficulty",
					"5) Notes"
			);
			for (String option : columns) {
				System.out.println(option);
			}
			boolean changeMore = true;
			Project upProject = new Project();
			
			while (changeMore == true) {
				Integer userChoice = getIntInput("\nSelect the number of the operation to change (enter to Finish)");	
				
				switch (userChoice) {
				case 1:
					String newName = getStringInput("New Project Name: ");
					upProject.setProjectName(newName);
					changeMore = quitChanges();
					break;
				case 2:
					BigDecimal newEstimatedHours = getBDInput("New Estimated Hours: ");
					upProject.setEstimatedHours(newEstimatedHours);
					changeMore = quitChanges();
					break;
				case 3:
					BigDecimal newActualHours = getBDInput("New Actual Hours: ");
					upProject.setActualHours(newActualHours);
					changeMore = quitChanges();
					break;
				case 4:
					Integer newDifficulty = getIntInput("New Difficulty: ");
					upProject.setDifficulty(newDifficulty);
					changeMore = quitChanges();
					break;
				case 5:
					String newNote = getStringInput("New Note: ");
					upProject.setNotes(newNote);
					changeMore = quitChanges();
					break;
				case -1:
					boolean changesMade = false;
					List<Field> fields = new LinkedList<>();
					for(Field field : upProject.getClass().getDeclaredFields()) {
						field.setAccessible(true);
						if( field != null) {
							changesMade = true;
							
						}
						fields.add(field);
					}
					if (changesMade != false) {
						ps.modifyProjectFields(fields);
					} else {
						System.out.println("No changes were made. Returning to main menu");
						displayMenu();
					}
						
				default:
					System.out.println("\n" + userChoice + " is invalid. Try again.");
					break;
				}
				
			}
			            
		} catch (Exception e) {
			System.out.println("\nError: " + e.toString() + " Try again.");
		}
		
	}


private void createMaterial() {
		try {
			if(!Objects.nonNull(curProject)) {
				System.out.println("No project currently selected."); 
				return;
			} 
			Material material = new Material();
			material.setProjectId(curProject);
			String materialName = getStringInput("Material name: ");
			Integer numRequired = getIntInput("number reqired: ");
			BigDecimal cost = getBDInput("Material overall cost: ");
			
			material.setMaterialName(materialName);
			material.setNumRequired(numRequired);
			material.setCost(cost);
			ps.addMaterial(material);
		}catch (Exception e) {
			System.out.println("\nError: " +e.toString() + " Try again.");
		}
		
	}


private void listProjects() {
		try {
			selectAllProjects();
		} catch (Exception e) {
			System.out.println("\nError: " + e.toString() + " Try again.");
		}
		
	}


private void selectAllProjects() {
	List<Project> output = ps.selectAllProjects();
	
	for (Project project : output) {
		System.out.println(project.toString());
	}
}


private void viewProjectFromId() {
		try {
			selectProject();
		} catch (Exception e) {
			System.out.println("\nError: " + e.toString() + " Try again.");
		}
		
	}


public void selectProject() {
	Integer projectId = getIntInput("Enter the Project's Id: ");
	Project project = new Project();
	project.setProjectId(projectId);
	Project output = ps.getProjectFromId(project);
	curProject = output.getProjectId();
	System.out.println();
	System.out.println(output.toString());
}


private void createProject() {
	try {
	addProject();
	System.out.println("Project created successfully!");
	} catch (Exception e) {
		System.out.println("\nError: " + e.toString() + " try again");
	}
}


private void uploadTables() {
		try {
		ps.createAndPopulateTables();
		} catch (Exception e) {
			System.out.println("\nError: " + e.toString() + " try again.");
		}
		
	}


private boolean quitMenu() {
		System.out.println("See ya!");
		return true;
	}

private boolean quitChanges() {
	Integer input = getIntInput("Update more columns? 1 = update more, enter = finish.");
	
	if (input == 1) {
		System.out.println("All Changes Collected!");
		return false;
	} else {
		return true;
	}
	
}
/**
 *  
 * @return the user's number they chose as the operation number
 */
	private int getOperation() {
		printOperations();
		Integer op = getIntInput("enter the operation number(just enter to exit)");
		return Objects.isNull(op) ? -1 : op;
		
	}


private Integer getIntInput(String prompt) {
	String input = getStringInput(prompt);
	if (Objects.isNull(input)) {
		return null;
	}
	try {
		return Integer.parseInt(input);
	}catch (NumberFormatException e) {
		throw new DbException(input + " is not a valid number.");
	}
	
}


private String getStringInput(String prompt) {
	System.out.print(prompt);
	String line = scan.nextLine();
	
	return line.isBlank() ? null : line.trim();
}


private void printOperations() {
	System.out.println();
	System.out.println("Here's what you can do:");
	operations.forEach(op -> System.out.println(op));
	
	
}
public void addProject() {
	Project project = new Project();
	System.out.println("Enter the Project data");
	String projectName = null;
	while (projectName == null) {
		projectName = getStringInput("Project name= ");
		if (projectName == null) {
			System.out.println("Must name your Project.");
		}
	}
	BigDecimal estimatedHours = getBDInput("Estimated hours to complete the project= ");
	BigDecimal actualHours = getBDInput("Actual hours to complete the project= ");
	
	Integer difficulty = getIntInput("On a scale of 1 to 10, how difficult is this project? (1 being easiest, 10 being hardest) ");
	String notes = getStringInput("Any project notes? Type enter when you're finished. \n");
	
	project.setActualHours(actualHours);
	project.setDifficulty(difficulty);
	project.setProjectName(projectName);
	project.setNotes(notes);
	project.setEstimatedHours(estimatedHours);
	
	Project updatedProject = ps.addProject(project);
	System.out.println("You have successfully created the project " + updatedProject);
}






private BigDecimal getBDInput(String prompt) {

String decimal = getStringInput(prompt);

	if (Objects.isNull(decimal)) {
			System.out.println("is Null.");
			return null;
	}
		try {
			
			return new BigDecimal(decimal).setScale(2);
		}catch (NumberFormatException e) {
			throw new DbException(decimal + " is not a valid number.");
		}


}






}
