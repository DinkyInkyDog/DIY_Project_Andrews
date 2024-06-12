package project;


import java.math.BigDecimal;
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
			"5) Add material to selected Project",
			"6) Add step to selected Project",
			"7) Add category to selected Project"
			
			
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
				createStep();
				break;
			case 7:
				addCategoryToProject();
				break;
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
			System.out.println("/nError: " +e.toString() + " Try again.");
		}
		
	}


private void listProjects() {
		try {
			selectAllProjects();
		} catch (Exception e) {
			System.out.println("/nError: " + e.toString() + " Try again.");
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
			System.out.println("/nError: " + e.toString() + " Try again.");
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


/**
 *  
 * @return
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
