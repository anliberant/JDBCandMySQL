//READING ResultSet MetaData

package Lesson11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson11 {

	public static void main(String[] args) throws SQLException {
		Connection myConn = null;
		Statement myStst = null;
		ResultSet myRs = null;

		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			myStst = myConn.createStatement();
			myRs = myStst.executeQuery("SELECT id, last_name, first_name, salary FROM employees");
			ResultSetMetaData rsMetaData = myRs.getMetaData();

			int columnCount = rsMetaData.getColumnCount();
			System.out.println("Column count: " + columnCount + "\n");
			for (int i = 1; i <= columnCount; i++) {
				System.out.println("Column name: " + rsMetaData.getColumnName(i));
				System.out.println("Column type name: " + rsMetaData.getColumnTypeName(i));
				System.out.println("Is Nullable: " + rsMetaData.isNullable(i));
				System.out.println("Is Auto Increment: " + rsMetaData.isAutoIncrement(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}
			if (myStst != null) {
				myStst.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}
	}

}
