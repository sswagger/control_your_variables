package myFarm.Models;

import baseModels.sqlReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBContext extends sqlReader {
	//=== CONSTRUCTORS ===\\\
	public DBContext() {
		super("C:\\Users\\Not Apple\\IdeaProjects\\control_your_variables\\controlYourVariables\\src\\myFarm\\Data\\myFarm.db");  // fixme
	}

	//=== METHODS ===\\
	public ArrayList<Products> readProducts(String sql) throws SQLException {
		ArrayList<Products> data = new ArrayList<>();
		try (var connection = DriverManager.getConnection(super.url)) {
			String readQuery = "SELECT * FROM Products";
			if (!sql.isEmpty()) {
				readQuery += "WHERE " + sql;
			}
			readQuery += ";";

			PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				data.add(new Products(result.getString("Name"), result.getInt("NumProduce"), result.getInt("NumProducts")));
			}

			preparedStatement.close();
			return data;
		}
		catch (SQLException e) {
			throw new SQLException(e);
		}
	}
	public void writeProducts(ArrayList<Products> products) throws SQLException {
		try (var connection = DriverManager.getConnection(super.url)) {
			for (Products i : products) {
				String createQuery = "INSERT INTO Products(Name, numProducts, numProduce) VALUES ('" + i.getName() + "', " + i.getNumProducts() +", " + i.getNumProduce() + ");";
				PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
				preparedStatement.executeUpdate();

				preparedStatement.close();
			}
		}
		catch (SQLException e) {
			throw new SQLException(e);
		}
	}
}
