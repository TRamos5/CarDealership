DROP DATABASE IF EXISTS carDealershipTest;
CREATE DATABASE carDealershipTest;

USE carDealershipTest;


CREATE TABLE `User`(
userId INT PRIMARY KEY AUTO_INCREMENT,
firstName VARCHAR(45),
lastName VARCHAR(45),
email VARCHAR(45),
`password` VARCHAR(45),
`role` INT );

CREATE TABLE Make(
makeId INT PRIMARY KEY AUTO_INCREMENT,
makeName VARCHAR(45),
dateAdded DATE,
userId INT,
FOREIGN KEY (userId) REFERENCES `user`(userId));

CREATE TABLE Model(
modelId INT PRIMARY KEY AUTO_INCREMENT,
modelName VARCHAR(45),
dateAdded DATE,
userId INT,
makeId INT,
FOREIGN KEY (userId) REFERENCES `user`(userId),
FOREIGN KEY (makeId) REFERENCES make(makeId));

CREATE TABLE Vehicle(
vehicleId INT PRIMARY KEY AUTO_INCREMENT,
`year` INT,
mileage INT,
isNew TINYINT,
salePrice DECIMAL,
style VARCHAR(45),
interior VARCHAR(45),
trans VARCHAR(45),
msrp DECIMAL,
color VARCHAR(45),
vin CHAR(17),
`description` MEDIUMTEXT,
featured TINYINT,
sold TINYINT,
fileImg VARCHAR(45),
makeId INT,
modelId INT,
userId INT,
FOREIGN KEY (makeId) REFERENCES make(makeId),
FOREIGN KEY (modelId) REFERENCES model(modelId),
FOREIGN KEY (userId) REFERENCES `user`(userId));

CREATE TABLE Purchases(
purchaseId INT PRIMARY KEY AUTO_INCREMENT,
purchaseCost DECIMAL,
purchaseType VARCHAR(45),
firstName VARCHAR(45),
lastName VARCHAR(45),
street VARCHAR(45),
street2 VARCHAR(45),
city VARCHAR(45),
state VARCHAR(45),
zip CHAR(5),
phone CHAR(10),
email VARCHAR(45),
purchaseDate DATE,
vehicleId INT,
userId INT,
FOREIGN KEY (vehicleId) REFERENCES vehicle(vehicleId),
FOREIGN KEY (userId) REFERENCES `user`(userId));

CREATE TABLE Specials(
specialId INT PRIMARY KEY AUTO_INCREMENT,
updateMultiplier DECIMAL,
title VARCHAR(45),
`description` MEDIUMTEXT);

CREATE TABLE Contacts(
contactId INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45),
phone CHAR(10),
message MEDIUMTEXT,
email VARCHAR(45));