package chs.burr.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MySqlDao {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	// TODO:
	// table initialization for excessDeaths and masksByCounty tables
	// populateExcessDeaths(), populateMasksByCounty(), incl. helper functions

	public MySqlDao() throws SQLException, ClassNotFoundException {
		// Get the Java Database Connector driver, we don't care about the details
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Setup the connection with the DB
		// IF YOU CHANGED YOUR USER PASSWORD, UPDATE THE NEXT LINE TO MATCH:
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/covid?user=sqluser&password=sqluserpw&useTimezone=true&serverTimezone=UTC");

		// Statements allow us to issue SQL queries to the database
		statement = connection.createStatement();
	}

	// Destructor:
	protected void finalize() {
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
			// NOOP
		}
	}

	public void initializeDatabases() throws Exception {
		try {
			statement.execute("USE covid;");

			final String US_INIT = "create table if not exists totalUS (date DATE, cases INT, deaths int);";
			statement.execute(US_INIT);

			final String STATES_INIT = "create table if not exists totalByState (date DATE, state VARCHAR(128), fips INT, cases INT, deaths int);";
			statement.execute(STATES_INIT);

			final String COUNTIES_INIT = "create table if not exists totalByCounty (date DATE, county VARCHAR(128), state VARCHAR(128), fips INT, cases INT, deaths int);";
			statement.execute(COUNTIES_INIT);

			// TODO:
			// final String DEATHS_INIT = "create table if not exists excessDeaths ( ???
			// );";
			// statement.execute(DEATHS_INIT);

			// final String MASKS_INIT = "create table if not exists masksByCounty ( ???
			// );";
			// statement.execute(MASKS_INIT);

			System.out.println("All tables created successfully");
		} catch (Exception e) {
			throw e;
		}
	}

	public void populateDatabases() throws Exception {
		try {
			clearTables();

			populateTotalUS();
			populateTotalByState();
			populateTotalByCounty();
			// populateExcessDeaths();
			// populateMasksByCounty();
		} catch (Exception e) {
			throw e;
		}
	}

	private void clearTables() throws Exception {
		try {
			statement.execute("USE covid;");
			statement.execute("truncate totalUS;");
			statement.execute("truncate totalByState;");
			statement.execute("truncate totalByCounty;");
			// statement.execute("truncate excessDeaths;");
			// statement.execute("truncate masksByCounty;");
		} catch (Exception e) {
			throw e;
		}
	}

	public void populateTotalUS() throws Exception {
		try {
			final String filename = "C:\\Users\\tim\\Desktop\\covid-19-data\\us.csv";
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);

			myReader.nextLine();

			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(",");

				java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(data[0]);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				Integer cases = Integer.valueOf(data[1]);
				Integer deaths = Integer.valueOf(data[2]);

				insertRowIntoTotalUs(sqlDate, cases, deaths);
			}

			myReader.close();
		} catch (Exception e) {
			throw e;
		}
	}

	private void insertRowIntoTotalUs(java.sql.Date date, Integer cases, Integer deaths) throws SQLException {

		preparedStatement = connection.prepareStatement("insert into covid.totalUS values (?, ?, ?);");

		preparedStatement.setDate(1, date);
		preparedStatement.setInt(2, cases);
		preparedStatement.setInt(3, deaths);

		preparedStatement.executeUpdate();
	}

	public void populateTotalByState() throws Exception {
		try {
			final String filename = "C:\\Users\\tim\\Desktop\\covid-19-data\\us-states.csv";
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);

			myReader.nextLine();

			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(",");

				java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(data[0]);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				String state = data[1];
				Integer fips = Integer.valueOf(data[2]);
				Integer cases = Integer.valueOf(data[3]);
				Integer deaths = Integer.valueOf(data[4]);

				insertRowIntoTotalByState(sqlDate, state, fips, cases, deaths);
			}

			myReader.close();
		} catch (Exception e) {
			throw e;
		}
	}

	private void insertRowIntoTotalByState(java.sql.Date date, String state, Integer fips, Integer cases,
			Integer deaths) throws SQLException {
		preparedStatement = connection.prepareStatement("insert into covid.totalByState values (?, ?, ?, ?, ?);");

		preparedStatement.setDate(1, date);
		preparedStatement.setString(2, state);
		preparedStatement.setInt(3, fips);
		preparedStatement.setInt(4, cases);
		preparedStatement.setInt(5, deaths);

		preparedStatement.executeUpdate();
	}

	public void populateTotalByCounty() throws Exception {
		try {
			final String filename = "C:\\Users\\tim\\Desktop\\covid-19-data\\us-counties.csv";
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);

			myReader.nextLine();

			while (myReader.hasNextLine()) {
				String[] data = myReader.nextLine().split(",");

				java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(data[0]);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				String county = data[1];
				String state = data[2];
				Integer fips = "".equals(data[3]) ? null : Integer.valueOf(data[3]);
				Integer cases = "".equals(data[4]) ? null : Integer.valueOf(data[4]);
				Integer deaths = "".equals(data[5]) ? null : Integer.valueOf(data[5]);

				// OPTIONAL, ADA COUNTY ONLY:
				if ("16001".equals(county))
					insertRowIntoTotalByCounty(sqlDate, county, state, fips, cases, deaths);
			}

			myReader.close();
		} catch (Exception e) {
			throw e;
		}
	}

	private void insertRowIntoTotalByCounty(java.sql.Date date, String county, String state, Integer fips,
			Integer cases, Integer deaths) throws SQLException {
		preparedStatement = connection.prepareStatement("insert into covid.totalByCounty values (?, ?, ?, ?, ?, ?);");

		preparedStatement.setDate(1, date);
		preparedStatement.setString(2, county);
		preparedStatement.setString(3, state);
		if (fips != null)
			preparedStatement.setInt(4, fips);
		else
			preparedStatement.setNull(4, java.sql.Types.INTEGER);

		if (cases != null)
			preparedStatement.setInt(5, cases);
		else
			preparedStatement.setNull(5, java.sql.Types.INTEGER);

		if (deaths != null)
			preparedStatement.setInt(6, deaths);
		else
			preparedStatement.setNull(6, java.sql.Types.INTEGER);

		preparedStatement.executeUpdate();
	}
}
