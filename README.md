# MySQL DAO
![](https://img.shields.io/github/repo-size/timburr1/MySqlTest)
![](https://img.shields.io/github/contributors/timburr1/MySqlTest)
![](https://img.shields.io/github/last-commit/timburr1/mysqltest)
<br>Simple Java project with Driver method and DAO.

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
CREATE DATABASE covid;
USE covid;
```

## Granting Permissions
If you attempt to run this repository's [**Main.java**](https://github.com/timburr1/MySqlTest/blob/master/src/chs/burr/Main.java) file, you will likely receive an error stating that the 'sqluser' database user does not have permissions. Enter the following lines in the MySQL CLI:
```sql
GRANT USAGE ON *.* TO sqluser@localhost;
GRANT ALL PRIVILEGES ON covid.* TO sqluser@localhost;
```

## Issues Configuring Java Workspace
* Make sure you have the [**Java JDK**](http://techoral.com/blog/java/install-openjdk-11-debian.html) installed.
* If you are having issues running this repository in Visual Studio Code, follow the steps provided in [**this article**](https://code.visualstudio.com/docs/java/java-tutorial).

## TODO
Checkout the NYT's Covid dataset with:
```bash
git clone https://github.com/nytimes/covid-19-data.git
```
Modify the populateX() methods in MySqlDao to point to the appropriate csv files.  
Query your new database tables with MySQL CLI or Workbench.  
Ctrl+F "TODO" in MySqlDao.  
