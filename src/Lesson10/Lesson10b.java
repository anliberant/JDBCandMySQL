//ACCESSING DATABASE SCHEMA METADATA

package Lesson10;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lesson10b {

	public static void main(String[] args) throws SQLException {
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = null;
		String columnNamePattern = null;
		String[] types = null;

		Connection myConn = null;
		ResultSet myRs = null;

		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			DatabaseMetaData data = myConn.getMetaData();

			System.out.println("List of Tables");
			System.out.println("--------------");
			myRs = data.getTables(catalog, tableNamePattern, columnNamePattern, types);
			while (myRs.next()) {
				System.out.println(myRs.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}
			if (myConn != null) {
				myConn.close();
			}
		}

	}

}
