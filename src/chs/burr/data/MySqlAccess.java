package chs.burr.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/** based on https://www.vogella.com/tutorials/MySQLJava/article.html **/

public class MySqlAccess {
	private Connection connect;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public void initializeDatabase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Setup the connection with the DB
			// IF YOU CHANGED YOUR USER PASSWORD, UPDATE THE NEXT LINE TO MATCH:
			connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
					+ "user=sqluser&password=sqluserpw&useTimezone=true&serverTimezone=UTC");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			statement.execute("USE feedback;");

			final String TABLE_INIT = "CREATE TABLE IF NOT EXISTS comments (" + "	id INT NOT NULL AUTO_INCREMENT,"
					+ "	MYUSER VARCHAR(30) NOT NULL," + "	EMAIL VARCHAR(30)," + "	WEBPAGE VARCHAR(100) NOT NULL,"
					+ "	DATUM DATE NOT NULL," + "	SUMMARY VARCHAR(40) NOT NULL," + "	COMMENTS VARCHAR(400) NOT NULL,"
					+ "	PRIMARY KEY (ID));";

			statement.execute(TABLE_INIT);

			final String INSERT = "INSERT INTO comments VALUES (default, 'Tim', 'myemail@gmail.com','https://www.westada.org/', "
					+ "'2020-09-30', 'Go CHS!', 'Go go go, you fighting Centennial High School Patriots!' );";

			statement.execute(INSERT);

			System.out.println("Database initialized successfully.");
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	public void readDatabase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Setup the connection with the DB
			// IF YOU CHANGED YOUR USER PASSWORD, UPDATE THE NEXT LINE TO MATCH:
			connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
					+ "user=sqluser&password=sqluserpw&useTimezone=true&serverTimezone=UTC");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from feedback.comments");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");

			// "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from feedback.comments");
			writeMetaData(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));

		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summary = resultSet.getString("summary");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("summary: " + summary);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
		}
	}
}
