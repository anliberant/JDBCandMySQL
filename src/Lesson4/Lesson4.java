// DELETING DATA FROM THE DATABASE

package Lesson4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson4 {

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
			System.out.println("Before the DELETE");
			displayEmployee(connection, "John", "Doe");
			System.out.println("\nDeleting the Employee\n");

			int rowsAffected = statement
					.executeUpdate("delete from employees " + "where last_name='Doe' and first_name='John'");
			displayEmployee(connection, "John", "Doe");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
	}

	private static void displayEmployee(Connection connection, String firstName, String lastName) throws SQLException {
		PreparedStatement statement = null;
		ResultSet rS = null;
		try {
			statement = connection.prepareStatement(
					"SELECT last_name, first_name, email FROM employees WHERE last_name=? AND first_name=?");
			statement.setString(1, lastName);
			statement.setString(2, firstName);

			rS = statement.executeQuery();
			boolean found = false;

			while (rS.next()) {
				System.out.printf("%s %s\n", firstName, lastName);
				found = true;
			}
			if (!found) {
				System.out.println("Employee NOT FOUND: " + firstName + " " + lastName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(statement, rS);
		}

	}

	private static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}

	}

	private static void close(PreparedStatement statement, ResultSet rS) throws SQLException {
		close(null, statement, rS);
	}

}
