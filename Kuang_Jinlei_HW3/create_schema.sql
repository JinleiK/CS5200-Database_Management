drop database if exists hw3;
create database hw3;
use hw3;

CREATE TABLE Developer (
	#change identity(1,1) to AUTO_INCREMENT in order to run on mysql
	Id INT AUTO_INCREMENT NOT NULL,
	firstName VARCHAR (50) NULL,
	lastName  VARCHAR (50) NULL,
	PRIMARY KEY (Id ASC)
);

CREATE TABLE ApplicationCategory (
	category VARCHAR (25) NOT NULL,
	PRIMARY KEY (category ASC)
);

INSERT INTO ApplicationCategory (category) VALUES ('GAMES');
INSERT INTO ApplicationCategory (category) VALUES ('PRODUCTIVITY');

CREATE TABLE Application (
	#change identity(1,1) to AUTO_INCREMENT in order to run on mysql
	Id INT AUTO_INCREMENT NOT NULL,
	#change "name" to "appName" to avoid use of mysql reserved words
	appName VARCHAR (50) DEFAULT 'Application' NULL,
	#change date type to timestamp in order to set current time as default
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
	category VARCHAR (25) NULL,
	#add a new column "price" to the application table in order to calculate the revenue
	price FLOAT(53) DEFAULT 0.00 NULL,
	PRIMARY KEY (Id ASC),
	FOREIGN KEY (category) REFERENCES ApplicationCategory (category)
);

CREATE TABLE Sales (
	applicationId  INT,
	developerId    INT,
	sold           DATE NULL,
	#give "quantity" a default value 0 to make calculation of the revenue easier
	quantity       INT DEFAULT 0,
	FOREIGN KEY (applicationId) REFERENCES Application (Id),
	FOREIGN KEY (developerId)   REFERENCES Developer   (Id)
);

CREATE TABLE DesktopApplication (#delete "downloads" and "pricePerDownload" to remove redundancy
	Id INT NOT NULL,
	#downloads INT NULL, 
	os VARCHAR (50) NULL,
	#pricePerDownload FLOAT (53) NULL,
	PRIMARY KEY (Id ASC),
	FOREIGN KEY (Id) REFERENCES Application (Id) ON DELETE CASCADE
);

CREATE TABLE WebApplication (#delete "price" and "subscribers" to remove redundancy
	Id      INT           NOT NULL,
	url     VARCHAR (100) DEFAULT 'http://cnn.com' NULL,
	#price   FLOAT (53)    DEFAULT ((0.99)) NULL,
	#subscribers INT NULL,
	browser VARCHAR (50)  NULL,
	PRIMARY KEY (Id ASC),
	FOREIGN KEY (Id) REFERENCES Application (Id) ON DELETE CASCADE
);

CREATE TABLE MobileApplication (#delete "installCount" and "price" to remove redundancy
	Id           INT          NOT NULL,
	os           VARCHAR (50) NULL,
	#installCount INT          NULL,
	#price        FLOAT (53)   NULL,
	PRIMARY KEY (Id ASC),
	FOREIGN KEY (Id) REFERENCES Application (Id) ON DELETE CASCADE
);

CREATE TABLE RoleType (
	#change "type" to "roleType" to avoid use of mysql reserved words
	roleType VARCHAR (50) NOT NULL,
	PRIMARY KEY (roleType ASC)
);

INSERT INTO RoleType (roleType) VALUES ('ARCHITECT');
INSERT INTO RoleType (roleType) VALUES ('BUSINESS ANALYST');
INSERT INTO RoleType (roleType) VALUES ('DEVELOPER');
INSERT INTO RoleType (roleType) VALUES ('PROJECT MANAGER');
INSERT INTO RoleType (roleType) VALUES ('PRODUCT MANAGER');
INSERT INTO RoleType (roleType) VALUES ('USER EXPERIENCE');
INSERT INTO RoleType (roleType) VALUES ('DIRECTOR');

CREATE TABLE Role (
	#change identity(1,1) to AUTO_INCREMENT in order to run on mysql
	Id INT AUTO_INCREMENT NOT NULL,
	application INT          NULL,
	developer   INT          NULL,
	role        VARCHAR (50) NULL,
	PRIMARY KEY (Id ASC),
	UNIQUE (developer ASC, application ASC, role ASC),
	#set the foreign key on delete cascade in order to be able to delete applications successfully
	FOREIGN KEY (application) REFERENCES Application (Id) ON DELETE CASCADE,
	FOREIGN KEY (developer) REFERENCES Developer (Id),
	FOREIGN KEY (role) REFERENCES RoleType (roleType)
);

CREATE TABLE PrivilegeEnum (
	privilege VARCHAR (20) NOT NULL,
	PRIMARY KEY (privilege ASC)
);

INSERT INTO PrivilegeEnum (privilege) VALUES ('ALL');
INSERT INTO PrivilegeEnum (privilege) VALUES ('CREATE');
INSERT INTO PrivilegeEnum (privilege) VALUES ('DELETE');
INSERT INTO PrivilegeEnum (privilege) VALUES ('READ');
INSERT INTO PrivilegeEnum (privilege) VALUES ('UPDATE');

CREATE TABLE AssetType (
	#change "type" to "assetType" to avoid use of mysql reserved words
	assetType VARCHAR (20) NOT NULL,
	PRIMARY KEY (assetType ASC)
);

INSERT INTO AssetType (assetType) VALUES ('APPLICATION');
INSERT INTO AssetType (assetType) VALUES ('DATA');
INSERT INTO AssetType (assetType) VALUES ('MODEL');
INSERT INTO AssetType (assetType) VALUES ('LOGIC');
INSERT INTO AssetType (assetType) VALUES ('PAGE');
INSERT INTO AssetType (assetType) VALUES ('PRESENTATION');
INSERT INTO AssetType (assetType) VALUES ('SCRIPT');
INSERT INTO AssetType (assetType) VALUES ('CONTROLLER');
INSERT INTO AssetType (assetType) VALUES ('VIEW');

CREATE TABLE Privilege (
	applicationId INT          NULL,
	developerId  INT          NULL,
	privilege     VARCHAR (20) NULL,
	assetType     VARCHAR (20) NULL,
	FOREIGN KEY (applicationId) REFERENCES Application (Id),
	FOREIGN KEY (developerId) REFERENCES Developer (Id),
	FOREIGN KEY (privilege) REFERENCES PrivilegeEnum (privilege),
	FOREIGN KEY (assetType) REFERENCES AssetType (assetType)
);

CREATE TABLE Asset ( 
	ID INT NOT NULL PRIMARY KEY, 
	assetName VARCHAR(50) NOT NULL, 
	assetType VARCHAR(20) REFERENCES AssetType(assetType), 
	applicationID INT REFERENCES Application (Id) 
); 