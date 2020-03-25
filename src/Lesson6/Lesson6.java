// INOUT PARAMETERS

package Lesson6;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class Lesson6 {

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		CallableStatement statement = null;
		String dbURL = "jdbc:mysql://localhost: 3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
		String dbUser = "student";
		String dbPass = "student";
		try {
			connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String theDepartment = "Engineering";
			statement = connection.prepareCall("{call greet_the_department(?)}");
			statement.registerOutParameter(1, Types.VARCHAR);
			statement.setString(1, theDepartment);

			System.out.println("Calling stored procedure. greet_the_department('" + theDepartment + "')");
			statement.execute();
			System.out.println("Finishing stored procedure.");

			String theResult = statement.getString(1);
			System.out.println("\nThe result = " + theResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

}
