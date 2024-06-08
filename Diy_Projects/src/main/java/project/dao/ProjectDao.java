package project.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import project.exception.DbException;
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
	 
}
