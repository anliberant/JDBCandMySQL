//ACCESSING DATABASE METADATA

package Lesson10;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Lesson10 {

	public static void main(String[] args) throws SQLException {
		Connection myConn = null;

		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			DatabaseMetaData databaseMetaData = myConn.getMetaData();

			System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
			System.out.println("Product version: " + databaseMetaData.getDatabaseProductVersion());
			System.out.println();

			System.out.println("JDBC Driver name: " + databaseMetaData.getDriverName());
			System.out.println("JDBC Driver version: " + databaseMetaData.getDriverVersion());
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myConn != null) {
				myConn.close();
			}
		}
	}

}
