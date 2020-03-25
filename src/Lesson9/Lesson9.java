//DEFINING JDBC TRANSACTIONS

package Lesson9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Lesson9 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
		String dbUser = "student";
		String dbPass = "student";

		Connection myConnection = null;
		Statement myStatement = null;

		try {
			myConnection = DriverManager.getConnection(url, dbUser, dbPass);
			myConnection.setAutoCommit(false);
			System.out.println("Salaries BEFORE: ");
			showSalaries(myConnection, "HR");
			showSalaries(myConnection, "Engineering");

			myStatement = myConnection.createStatement();
			myStatement.executeUpdate("DELETE FROM employees WHERE department = 'HR'");
			myStatement.executeUpdate("UPDATE employees SET salary = 300000 WHERE department = 'Engineering'");
			System.out.println("\n>>Transactions steps are READY.\n");

			boolean ok = askUserIfOkToSave();

			if (ok) {
				myConnection.commit();
				System.out.println("\n>>Transactions COMMITED\n");
			} else {
				myConnection.rollback();
				System.out.println("\n>>Transactions ROLLED BACK");
			}

			System.out.println("Salaries AFTER\n");
			showSalaries(myConnection, "HR");
			showSalaries(myConnection, "Engineering");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(myConnection, myStatement, null);
		}

	}

	private static boolean askUserIfOkToSave() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Is it okay to save?  yes/no: ");
		String input = scanner.nextLine();

		scanner.close();

		return input.equalsIgnoreCase("yes");
	}

	private static void showSalaries(Connection myConnection, String theDepartment) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		System.out.println("Show Salaries for Department: " + theDepartment);

		try {
			// Prepare statement
			myStmt = myConnection.prepareStatement("select * from employees where department=?");

			myStmt.setString(1, theDepartment);

			// Execute SQL query
			myRs = myStmt.executeQuery();

			// Process result set
			while (myRs.next()) {
				String lastName = myRs.getString("last_name");
				String firstName = myRs.getString("first_name");
				double salary = myRs.getDouble("salary");
				String department = myRs.getString("department");

				System.out.printf("%s, %s, %s, %.2f\n", lastName, firstName, department, salary);
			}

			System.out.println();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(null, myStmt, myRs);
		}
	}

	private static void close(Connection myConnection, Statement myStatement, ResultSet myRs) throws SQLException {
		if (myStatement != null) {
			myStatement.close();
		}
		if (myConnection != null) {
			myConnection.close();
		}
		if (myRs != null) {
			myRs.close();
		}
	}

}
