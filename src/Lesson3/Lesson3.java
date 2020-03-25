// UPDATING DATA IN THE DATABASE

package Lesson3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson3 {

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
			System.out.println("Before the UPDATE");
			displayEmployee(connection, "John", "Doe");
			System.out.println("\nExecuting the UPDATE for John Doe\n");
			int rowAffected = statement.executeUpdate("UPDATE employees " + "set email = 'john.doe@luv2code.com' "
					+ "WHERE last_name = 'Doe' AND first_name = 'John' ");
			System.out.println("After the UPDATE");
			displayEmployee(connection, "John", "Doe");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}

	}

	private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Prepare statement
			myStmt = myConn.prepareStatement(
					"select last_name, first_name, email from employees where last_name=? and first_name=?");

			myStmt.setString(1, lastName);
			myStmt.setString(2, firstName);

			// Execute SQL query
			myRs = myStmt.executeQuery();

			// Process result set
			while (myRs.next()) {
				String theLastName = myRs.getString("last_name");
				String theFirstName = myRs.getString("first_name");
				String email = myRs.getString("email");

				System.out.printf("%s %s, %s\n", theFirstName, theLastName, email);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}

	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

	private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}
}
