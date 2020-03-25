// INSERTING DATA INTO THE DATABASE

package Lesson2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson2 {

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		String dbURL = "jdbc:mysql://localhost: 3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
		String dbUser = "student";
		String dbPass = "student";

		try {
			connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
			statement = connection.createStatement();
			System.out.println("Inserting a new employee to database \n");

			int rowsAffected = statement
					.executeUpdate("INSERT INTO employees " + "(last_name, first_name, email, department, salary) "
							+ "values" + "('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.00)");
			resultSet = statement.executeQuery("SELECT * FROM employees ORDER BY last_name");
			while (resultSet.next()) {
				System.out.println(resultSet.getString("last_name") + " " + resultSet.getString("first_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
