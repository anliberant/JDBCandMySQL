//Read BLOB from DB

package Lesson12;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson12b {

	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		InputStream input = null;
		FileOutputStream output = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			statement = connection.createStatement();
			String sql = "SELECT resume FROM employees WHERE email ='john.doe@foo.com'";
			resultSet = statement.executeQuery(sql);

			File theFile = new File("resume_from_bd.pdf");
			output = new FileOutputStream(theFile);

			if (resultSet.next()) {
				input = resultSet.getBinaryStream("resume");
				System.out.println("Reading from DATABASE");
				System.out.println(sql);

				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					output.write(buffer);
				}

				System.out.println("\nSaved to file: " + theFile.getAbsolutePath());
				System.out.println("\nCompleted successfull");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				input.close();
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
