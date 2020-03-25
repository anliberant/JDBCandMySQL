//READING AND WRITING BLOBS

package Lesson12;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lesson12 {
	public static void main(String[] args) throws IOException, SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream input = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");

			String sql = "UPDATE employees SET resume = ?" + " WHERE email = 'john.doe@foo.com'";
			statement = connection.prepareStatement(sql);

			File theFile = new File("sample_resume.pdf");
			input = new FileInputStream(theFile);
			statement.setBinaryStream(1, input);

			System.out.println("Reading input File: " + theFile.getAbsolutePath());

			System.out.println("Storing resume in database: " + theFile);
			System.out.println(sql);

			statement.executeUpdate();
			System.out.println("Completed successfull");

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
