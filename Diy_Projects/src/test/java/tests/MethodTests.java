package tests;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import project.dao.ProjectDao;
import project.service.ProjectService;
import projects.entity.Project;

public class MethodTests {
private static ProjectService ps = new ProjectService();
private static ProjectDao dao = new ProjectDao();

	public static void main(String[] args) {
		
		//ps.addProject();
		Project tp = new Project();
		tp.setProjectName("hat");
		for (Field field : tp.getClass().getDeclaredFields()) {
			System.out.println("Field value : " + field);
		}
		
	}

}
