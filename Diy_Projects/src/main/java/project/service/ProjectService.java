package project.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import project.dao.ProjectDao;
import project.exception.DbException;

@SuppressWarnings("unused")
public class ProjectService {

private static final String SCHEMA_FILE = "project_schema.sql";
private static final String DATA_FILE = "project_data.sql";
	

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
	private  void loadFile(String schemaFile) {
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
		content = removeWhiteSpace();
		
		return extractLinesFromContent(content);
	return null;
}

	
	/**
	 * 
	 * @param content the string fresh from the readFile
	 * @return a string without Comments
	 */
	private String removeComments(String content) {
		
	return null; 
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
	
	
}
