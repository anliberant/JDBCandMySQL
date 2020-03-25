//READING AND WRITING CLOBs

package Lesson13;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lesson13 {

	public static void main(String[] args) throws IOException, SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		FileReader input = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			String sql = "UPDATE employees SET resume = ?" + " WHERE email = 'john.doe@foo.com'";
			statement = connection.prepareStatement(sql);

			File theFile = new File("sample_resume.txt");
			input = new FileReader(theFile);
			statement.setCharacterStream(1, input);

			System.out.println("Reading the file: " + theFile.getAbsolutePath());

			System.out.println("\nStoring the file in database: " + theFile);
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
