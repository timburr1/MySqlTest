# MySqlTest

Simple Java project with Driver method and DAO. Need to have MySQL installed, and create a database and user like:

> create database feedback;

> use feedback;

> CREATE USER sqluser IDENTIFIED BY 'sqluserpw';

> grant usage on *.* to sqluser@localhost;

> grant all privileges on feedback.* to sqluser@localhost;
