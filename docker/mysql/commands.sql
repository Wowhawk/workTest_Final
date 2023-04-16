select *
from Persons;

ALTER TABLE Persons
    ADD AGE INT(3);
ALTER TABLE Persons
DROP COLUMN AGE;

desc Persons;

INSERT INTO Persons (PersonID, LastName, FirstName, Title, Gender)
values (0, 'Doe', 'Jane', 'Mrs', 'female');
INSERT INTO Persons (PersonID, LastName, FirstName, Title, Gender)
values (1, 'Doe', 'John', 'Mr', 'male');
INSERT INTO Persons (PersonID, LastName, FirstName, Title, Gender)
values (2, 'oe', 'Richard', 'Mr', 'male');

select FirstName, LastName
from Persons
WHERE Persons.LastName LIKE '%oe';
select FirstName, LastName
from Persons
WHERE Persons.PersonID IN (0, 1, 3, 5);

update Persons
set LastName = 'Abc';
update Persons
set LastName = 'Doe'
where PersonID IN (0, 1);
update Persons
set LastName = 'Oe'
where PersonID NOT IN (0, 1);
update Persons
set LastName = NULL
where PersonID NOT IN (0, 1);

Delete
from Persons
where PersonID > 10;
Delete
from Persons
where PersonID = 2;

select count(LastName)
from Persons;

select LastName
from Persons;
select LastName as TestName
from Persons;

create table Contacts
(
    PersonID int,
    EMAIL    varchar(200),
    PHONE    int(10)
);

insert into Contacts (PersonID, EMAIL, PHONE)
VALUES (0, 'jane.doe@gmail.com', 123456789);
insert into Contacts (PersonID, EMAIL, PHONE)
VALUES (1, 'john.doe@gmail.com', 987654321);
insert into Contacts (PersonID, EMAIL, PHONE)
VALUES (2, 'richard.oe@gmail.com', 789321456);

select *
from Contacts;

select personsTable.FirstName, personsTable.LastName, contactsTable.PHONE, contactsTable.EMAIL
from Persons personsTable
         join Contacts contactsTable on personsTable.PersonID = contactsTable.PersonID;


drop table Persons;

CREATE TABLE Persons (
                         PersonID int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
                         LastName varchar(255),
                         FirstName varchar(255),
                         Title varchar(255),
                         Gender varchar(255)
);

INSERT INTO Persons (LastName, FirstName, Title, Gender)
values ('Doe', 'Jane', 'Mrs', 'female');
INSERT INTO Persons (LastName, FirstName, Title, Gender)
values ('Doe', 'John', 'Mrs', 'male');

select * from Persons;

CREATE TABLE Persons (
                         PersonID int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
                         LastName varchar(255),
                         FirstName varchar(255),
                         Title varchar(255),
                         Gender varchar(255)
);

INSERT INTO Persons (LastName, FirstName, Title, Gender)
values ('Doe', 'Jane', 'Mrs', 'female');
INSERT INTO Persons (LastName, FirstName, Title, Gender)
values ('Doe', 'John', 'Mrs', 'male');

select * from Persons;