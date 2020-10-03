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
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public void initializeDatabase() throws Exception {
		try {
			// Get the Java Database Connector driver, we don't care about the details
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Setup the connection with the DB
			// IF YOU CHANGED YOUR USER PASSWORD, UPDATE THE NEXT LINE TO MATCH:
			connection = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
					+ "user=sqluser&password=sqluserpw&useTimezone=true&serverTimezone=UTC");

			// Statements allow us to issue SQL queries to the database
			statement = connection.createStatement();

			statement.execute("USE feedback;");

			final String TABLE_INIT = "create table if not exists totalUS (date DATE, cases INT, deaths int);";

			statement.execute(TABLE_INIT);

			System.out.println("Table totalUS created successfully");
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	// Insert a row into feedback.totalUS, make sure we can select it, and then
	// delete it:
	public void readDatabase() throws Exception {
		try {
			// Get the Java Database Connector driver, we don't care about the details
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Setup the connection with the DB
			// IF YOU CHANGED YOUR USER PASSWORD, UPDATE THE NEXT LINE TO MATCH:
			connection = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
					+ "user=sqluser&password=sqluserpw&useTimezone=true&serverTimezone=UTC");

			// Statements allow us to issue SQL queries to the database
			statement = connection.createStatement();

			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select date, cases, deaths from feedback.totalUS");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection.prepareStatement("insert into feedback.totalUS values (?, ?, ?);");

			// date, cases, deaths from feedback.totalUS
			// Parameters start with 1
			preparedStatement.setDate(1, new java.sql.Date(2020, 0, 1));
			preparedStatement.setInt(2, 1);
			preparedStatement.setInt(3, 0);

			// Don't need any strings for this table, but this is how we would do that:
			// preparedStatement.setString(6, "TestComment");

			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("SELECT date, cases, deaths from feedback.totalUS");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connection.prepareStatement("delete from feedback.totalUS where date = '2020-01-21';");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from feedback.totalUS");
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
			// e.g. resultSet.getString(2);
			Date date = resultSet.getDate("date");
			int cases = resultSet.getInt("cases");
			int deaths = resultSet.getInt("deaths");
			System.out.println("Date: " + date);
			System.out.println("Cases: " + cases);
			System.out.println("Deaths: " + deaths);
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

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
		}
	}
}
