//Reading CLOBs from DataBase

package Lesson13;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lesson13b {

	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Reader input = null;
		FileWriter output = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow",
					"student", "student");
			statement = connection.createStatement();
			String sql = "SELECT resume FROM employees WHERE email = 'john.doe@foo.com'";
			resultSet = statement.executeQuery(sql);

			File theFile = new File("resume_from_bd.txt");
			output = new FileWriter(theFile);

			if (resultSet.next()) {
				input = resultSet.getCharacterStream("resume");
				System.out.println("Reading resume from database...");
				System.out.println(sql);

				int theChar;
				while ((theChar = input.read()) > 0) {
					output.write(theChar);
				}
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
			if (resultSet != null) {
				resultSet.close();
			}
			if (output != null) {
				output.close();
			}
		}

	}

}
