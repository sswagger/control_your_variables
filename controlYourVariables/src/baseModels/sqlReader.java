/**
 * This is the class that reads data from .db files
 */

//=== PACKAGE ===\\
package baseModels;

//=== IMPORTED MODULES ===\\
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

//=== CLASS ===\\
public abstract class sqlReader {
	//=== ATTRIBUTES ===\\
	protected String url;

	//=== CONSTRUCTORS ===\\
	public sqlReader(String dataPath) {
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e) {
			return;
		}

		this.url = "jdbc:sqlite:" + dataPath;
	}

	//=== METHODS ===\\
	public void createTable(String table, ArrayList<ArrayList<String>> columns) throws SQLException {
		try (var connection = DriverManager.getConnection(this.url)) {
			StringBuilder createSql = new StringBuilder("CREATE " + table + "(");

			for (ArrayList<String> i : columns) {
				createSql.append(" ").append(i.getFirst()).append(" ").append(i.getLast());
				if (i != columns.getLast()) {
					createSql.append(",");
				}
			}

			createSql.append(")");
			PreparedStatement preparedStatement = connection.prepareStatement(createSql.toString());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e) {
			throw new SQLException(e);
		}
	}
	public void deleteTable(String table) throws SQLException {
		try (var connection = DriverManager.getConnection(this.url)) {
			String deleteSql = "DROP " + table;

			PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e) {
			throw new SQLException(e);
		}
	}
}
