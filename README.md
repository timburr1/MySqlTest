# MySQL DAO
Simple Java project with Driver method and DAO.

## Prerequisites
* [**MySQL**](https://www.mysql.com/downloads/)
* [**Java**](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

## MySQL Setup
```bash
$ sudo apt install mysql-server
$ mysql -p
```

## Creating a User
Once you have the MySQL CLI open enter the following line to create a new user:
```sql
CREATE USER 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';
```
If you receive an error, try dropping the user in case it already exists:
```sql
DROP USER 'sqluser'@'localhost';
```

## Creating a Database
```sql
CREATE DATABASE feedback;
USE feedback;
```

## Granting Permissions
If you attempt to run this repository's [**Main.java**](https://github.com/LeSirH/MySqlTest/blob/master/src/chs/burr/Main.java) file, you will likely receive an error stating that the 'sqluser' database user does not have permissions. Enter the following lines in the MySQL CLI:
```sql
GRANT USAGE ON *.* TO sqluser@localhost;
GRANT ALL PRIVILEGES ON feedback.* TO sqluser@localhost;
```

## Issues Configuring Java Workspace
* Make sure you have the [**Java JDK**](http://techoral.com/blog/java/install-openjdk-11-debian.html) installed.
* If you are having issues running this repository in Visual Studio Code, follow the steps provided in [**this article**](https://code.visualstudio.com/docs/java/java-tutorial).

## TODO
Modify initializeDatabase() to create a new table that isn't Comments. This could be Warleader, Student, or whatever you come up with; might as well just put it in the feedback database.  
Checkout the NYT's Covid dataset with:
```bash
git clone https://github.com/nytimes/covid-19-data.git
```
Pick one of the csv files in that repository and print it out with printFileToConsole(). The base-level files us.csv and us-states.csv will be the easiest to work with; us-counties.csv, mask-use and excess-deaths will be more complicated, but maybe let you ask more interesting questions at the end of this project.  
Modify initializeDatabase() again, or create a new method that creates a database with columns matching your chosen csv file.  
Instead of just printing, write a method to parse a comma-separated file into its constituent values. **Hint: [String split](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#split(java.lang.String)) is a thing, and someString.split(",") is a valid usage.**  
Use prepared statements to write your csv file to the database that you created.  
Query your database: How many new cases of Covid were reported on March 14, 2020? How many excess deaths in Ada county, in the two weeks after the bars reopened? How has the number of mask-wearers (i.e. people who reported that they "Frequantly" or "Always" wear a mask in public when they expect to be within 6' of another person?) changed over time?
