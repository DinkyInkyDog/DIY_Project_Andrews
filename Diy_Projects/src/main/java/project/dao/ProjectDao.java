package project.dao;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import project.exception.DbException;
import projects.entity.Material;
import projects.entity.Project;
import provided.util.DaoBase;

public class ProjectDao extends DaoBase {
	private static final String PROJECT_TABLE = "project";
	private static final String MATERIAL_TABLE = "material";
	private static final String CATEGORY_TABLE = "category";
	private static final String STEP_TABLE = "step";
	/**
	 * 
	 * @param sqlStatements
	 */
	public static void executeBatch(List<String> sqlBatch) {
		try (Connection conn = Dbconnection.getConnections()) {
			startTransaction(conn);
			try (Statement stmt = conn.createStatement()) {
				for (String sql : sqlBatch) {
					stmt.addBatch(sql);
				}
				stmt.executeBatch();
				commitTransaction(conn);
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		} catch (SQLException e) {
			throw new DbException(e);
			
		}
		
	}
	public Project insertProject(Project project) {
		String sql = " " + "INSERT INTO " + PROJECT_TABLE + " " + 
	"(project_name, estimated_hours, actual_hours, difficulty, notes)"
				+ "VALUES " + "(?, ?, ?, ?, ?)";
		try (Connection conn = Dbconnection.getConnections()) {
			startTransaction(conn);
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, project.getProjectName(), String.class);
				setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, project.getDifficulty(), Integer.class);
				setParameter(stmt, 5, project.getNotes(), String.class);
				
				stmt.executeUpdate();
				
				Integer projectId = getLastInsertId(conn, PROJECT_TABLE);
				commitTransaction(conn);
				project.setProjectId(projectId);
				return project;
			} catch (Exception e) {
				rollbackTransaction(conn); 
				throw new DbException(e);
			}
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	/**
	 * 
	 * @param search  
	 * @return
	 * 
	 * I need to change it so it shows all the other stuff too.
	 */
	public List<Project> listProjects(boolean specificProject, int project) {
		List<Project> list = new LinkedList<>();
		String sql =  ""+"SELECT * FROM " + PROJECT_TABLE;
		if (specificProject == true) {
				sql += " WHERE project_id = ?;";
		} else {
			sql += ";";
		}
		try (Connection conn = Dbconnection.getConnections()){
			startTransaction(conn);
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				if (specificProject == true) {
					setParameter(ps, 1, project, Integer.class);
				}
				try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						list.add(extract(rs, Project.class));
					}
				} 
				return list;
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		}catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	public Material insertMaterial(Material material) {
		String sql = " " + "INSERT INTO " + MATERIAL_TABLE 
				+ " (project_id, material_name, num_required, cost)"
				+ "VALUE (?, ?, ?, ?);"; 
		try (Connection conn = Dbconnection.getConnections()){
			startTransaction(conn);
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, material.getProjectId(), Integer.class);
				setParameter(stmt, 2, material.getMaterialName(), String.class);
				setParameter(stmt, 3, material.getNumRequired(), Integer.class);
				setParameter(stmt, 4, material.getCost(), BigDecimal.class);
				
				stmt.executeUpdate();
				commitTransaction(conn);
				
				Integer materialId = getLastInsertId(conn, MATERIAL_TABLE);
				material.setMaterialId(materialId);
				return material;
			}catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		}catch (SQLException e) {
			throw new DbException(e);
		}
	
	}
	
	
	public int updateProject(Project cp) {
		String sql = "UPDATE " + PROJECT_TABLE + " SET "
				+ "project_name = ?,"
				+ "estimated_hours = ?,"
				+ "actual_hours = ?,"
				+ "difficulty = ?,"
				+ "notes = ? "
				+ "WHERE project_id = ?;";
		
		try(Connection conn = Dbconnection.getConnections()) {
			startTransaction(conn);
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				setParameter(stmt, 1, cp.getProjectName(), String.class);
				setParameter(stmt, 2, cp.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, cp.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, cp.getDifficulty(), Integer.class);
				setParameter(stmt, 5, cp.getNotes(), String.class);
				setParameter(stmt, 6, cp.getProjectId(), Integer.class);
				int changes = stmt.executeUpdate();
				commitTransaction(conn);
				return changes;
			}catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
		} catch(SQLException e) {
			throw new DbException(e);
		}
		
		
	}
	public void deleteProject(Integer projectId) {
		String sql = "DELETE FROM " + PROJECT_TABLE + " WHERE project_id = ?;";
		try (Connection conn = Dbconnection.getConnections()) {
			startTransaction(conn);
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				setParameter(stmt, 1, projectId, Integer.class);
				int changes = stmt.executeUpdate();
				commitTransaction(conn);
				if(changes == 0) {
					throw new DbException("Nothing was deleted");
				}
				
			} catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
			
			
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	 
}
