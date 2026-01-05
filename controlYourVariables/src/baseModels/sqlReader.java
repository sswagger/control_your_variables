/**
 * This is the class that reads data from .db files
 */

//=== PACKAGE ===\\
package baseModels;

//=== IMPORTED MODULES ===\\
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//=== CLASS ===\\
public class sqlReader {
	//=== ATTRIBUTES ===\\
	private String url;

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
	public ArrayList<String> readDb(String sql, ArrayList<String> columns) throws Exception {
		ArrayList<String> data = new ArrayList<>();
		try (var connection = DriverManager.getConnection(this.url)) {
			String readQuery = sql;
			PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
			ResultSet s = preparedStatement.executeQuery();

			for (String i : columns) {
				data.add(s.getString(i));
			}

			preparedStatement.close();
			return data;
		}
		catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
	public void updateDb(String sql) throws Exception {
		try (var connection = DriverManager.getConnection(this.url)) {
			String updateQuery = sql;
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		}
		catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}
}
