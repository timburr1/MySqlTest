# MySqlTest

Simple Java project with Driver method and MySQL DAO. Need to have MySQL installed, and create a database like:

> create database feedback;
> use feedback;

> CREATE USER sqluser IDENTIFIED BY 'sqluserpw';

> grant usage on *.* to sqluser@localhost identified by 'sqluserpw';
> grant all privileges on feedback.* to sqluser@localhost;

> CREATE TABLE comments (
        id INT NOT NULL AUTO_INCREMENT,
        MYUSER VARCHAR(30) NOT NULL,
        EMAIL VARCHAR(30),
        WEBPAGE VARCHAR(100) NOT NULL,
        DATUM DATE NOT NULL,
        SUMMARY VARCHAR(40) NOT NULL,
        COMMENTS VARCHAR(400) NOT NULL,
        PRIMARY KEY (ID)
    );

> INSERT INTO comments VALUES (default, 'Tim', 'myemail@gmail.com','https://www.westada.org/', '2020-09-30 10:33:11', 'Go CHS!', 'Go go go, you fighting Centennial High School Patriots!' );
