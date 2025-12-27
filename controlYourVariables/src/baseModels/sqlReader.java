package baseModels;

import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlReader {
	public void test() {
		String url = "jdbc:sqlite:C:/sqlite/my.db";

		try (var conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				var meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
