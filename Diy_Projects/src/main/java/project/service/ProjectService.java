package project.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.math.BigDecimal;

import project.dao.ProjectDao;
import project.exception.DbException;
import projects.entity.Project;

@SuppressWarnings("unused")
public class ProjectService{
	

private static final String SCHEMA_FILE = "project_schema.sql";
private static final String DATA_FILE = "project_data.sql";
private ProjectDao dao = new ProjectDao();
private Scanner scan = new Scanner(System.in);
/**
 * loads the two pre-made files that contain the sql code to create tables and then
 * fill them with data.
 */
	public void createAndPopulateTables() {
		loadFile(SCHEMA_FILE);
		//loadFile(DATA_FILE);
		
	}
	


/**
 * 	
 * @param schemaFile sql file to load and execute
 */
	private void loadFile(String schemaFile) {
		String content = readFileContent(schemaFile);
		List<String> sqlStatements = convertContentToSQLStatments(content);
		
		
		ProjectDao.executeBatch(sqlStatements);	
	}
/**
 * 
 * @param content the string pulled from the readFileContent method
 * @return a clean list of each string of sql that we want.
 * 
 * This method is the pruning the flowers part. Getting rid of all the extra dead stuff
 * leaving only each individual flower.
 */
	private List<String> convertContentToSQLStatments(String content) {
		content = removeComments(content);
		content = removeWhiteSpace(content);
		
		return extractLinesFromContent(content);
	
}
/**
 * 
 * @param content fully clean string that just needs to be split up
 * @return a list of individual strings that can be executed
 */
	private List<String> extractLinesFromContent(String content) {
		List<String> lines = new LinkedList<String>();
		while(!content.isEmpty()) {
			int semicolon = content.indexOf(";");
			if (semicolon == -1) {
				if (!content.isBlank()) {
					lines.add(content);
				}
				content = "";
			} else {
				lines.add(content.substring(0, semicolon).trim());
				content = content.substring(semicolon+1);
			}
		}
	return lines;
}

	/**
	 * @param content the string that now doesn't have any comments to 
	 * worry about
	 * @return a clean string that only has the code we wont in it.
	 */
	private String removeWhiteSpace(String content) {
		return content.replaceAll("//s+", " ");
	}

	/**
	 * 
	 * @param content the string fresh from the readFile
	 * @return a string without Comments
	 */
	private String removeComments(String content) {
		StringBuilder build = new StringBuilder(content);
		int commentPos = 0;
		while((commentPos = build.indexOf("-- ", commentPos)) != -1) {
			int eolPos = build.indexOf("/n", commentPos+1);
			if (eolPos == -1) {
				build.replace(commentPos, build.length(), "");
			} else {
				build.replace(commentPos, eolPos + 1 , "");
			}
		}
	return build.toString(); 
}

	/**
	 * 
	 * @param schemaFile the file name that we need
	 * @return the String of all the content from the file Loaded as one big clump
	 */
	private String readFileContent(String schemaFile) {
	try {
		Path path = Paths.get(getClass().getClassLoader().getResource(schemaFile).toURI());
		return Files.readString(path);
	} catch (Exception e) {
		throw new DbException(e);
	}
	
}



	public Project addProject(Project project) {
		return dao.insertProject(project);
		
	}



	
	public Project getProjectFromId(Project project) {
		int projectId = project.getProjectId();
		List<Project> output = dao.listProjects(true, projectId);
		for (Project pr : output) {
			project = pr;
		}
		return project;
	}



	public List<Project> selectAllProjects() {
		return dao.listProjects(false, 0);
		
	}


	




	

	
	
}
