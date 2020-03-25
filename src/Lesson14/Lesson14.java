package Lesson14;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Lesson14 {

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("demo.properties"));

			String theUser = prop.getProperty("user");
			String thePassword = prop.getProperty("password");
			String theDbUrl = prop.getProperty("dburl");

			System.out.println("Connecting the DataBase...");
			System.out.println("Database URL: " + theDbUrl);
			System.out.println("User: " + theUser);

			connection = DriverManager.getConnection(theDbUrl, theUser, thePassword);

			System.out.println("Successfull connection!\n");
			statement = connection.createStatement();

			// 5. Execute SQL query
			resultSet = statement.executeQuery("select * from employees");

			// 6. Process the result set
			while (resultSet.next()) {
				System.out.println(resultSet.getString("last_name") + ", " + resultSet.getString("first_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

}
