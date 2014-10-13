
##### Drop statements ######
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Developer;
DROP TABLE IF EXISTS Application;	
DROP TABLE IF EXISTS RoleType;
DROP TABLE IF EXISTS Dev_App;
DROP TABLE IF EXISTS AppView;
DROP TABLE IF EXISTS Controller;
DROP TABLE IF EXISTS Model;
DROP TABLE IF EXISTS Script;
DROP TABLE IF EXISTS EventHandler;
DROP TABLE IF EXISTS Control_Script;
DROP TABLE IF EXISTS ValueType;
DROP TABLE IF EXISTS NameValuePair;
DROP TABLE IF EXISTS Display;
SET FOREIGN_KEY_CHECKS = 1;

#### Create statements ####
CREATE TABLE Developer(
	devID INT NOT NULL AUTO_INCREMENT,
	dFirstName VARCHAR(50) NOT NULL,
	dLastName VARCHAR(50) NOT NULL,
	PRIMARY KEY (devID)
	);

	
CREATE TABLE Application(
	appID INT NOT NULL AUTO_INCREMENT,
	appName VARCHAR(100) NOT NULL,
	target VARCHAR(100),
	createdDate DATE,
	url VARCHAR(100),
	pricePerAccess INT,
	installedTime TIMESTAMP,
	pricePerDownload INT,
	appType VARCHAR(30),
	PRIMARY KEY (appID)
	);
	

CREATE TABLE RoleType(
	rType VARCHAR(30) NOT NULL PRIMARY KEY
	);
	
INSERT INTO RoleType (rType) VALUES ("userExperience");
INSERT INTO RoleType (rType) VALUES ("projectManager");
INSERT INTO RoleType (rType) VALUES ('businessAnalyst');
INSERT INTO RoleType (rType) VALUES ('architect');
INSERT INTO RoleType (rType) VALUES ('databaseAdmin');
INSERT INTO RoleType (rType) VALUES ('backendDeveloper');
INSERT INTO RoleType (rType) VALUES ('frontendDeveloper');
	

CREATE TABLE Dev_App(
	devID INT NOT NULL,
	appID INT NOT NULL,
	role VARCHAR(30) NOT NULL,
	PRIMARY KEY (devID, appID, role),
	FOREIGN KEY (devID) REFERENCES Developer(devID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (appID) REFERENCES Application(appID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (role) REFERENCES RoleType(rType)
	);
	
	
CREATE TABLE AppView(
	vID INT NOT NULL,
	vName VARCHAR(100),
	parentID INT,
	appID INT,
	PRIMARY KEY (vID),
	FOREIGN KEY (parentID) REFERENCES AppView(vID)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	FOREIGN KEY (appID) REFERENCES Application(appID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);
		

CREATE TABLE Controller(
	ctrID INT NOT NULL,
	ctrName VARCHAR(100),
	appID INT,
	PRIMARY KEY (ctrID),
	FOREIGN KEY (appID) REFERENCES Application(appID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);
	

CREATE TABLE Model(
	mdID INT NOT NULL,
	mName VARCHAR(100),
	appID INT,
	PRIMARY KEY (mdID),
	FOREIGN KEY (appID) REFERENCES Application(appID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);

CREATE TABLE Script(
	sID INT NOT NULL,
	sName VARCHAR(100),
	targetView INT,
	PRIMARY KEY (sID),
	FOREIGN KEY (targetView) REFERENCES AppView(vID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);
	

CREATE TABLE EventHandler(
	ehID INT NOT NULL,
	ehName VARCHAR(100),
	vID INT,
	sID INT,
	PRIMARY KEY (ehID),
	FOREIGN KEY (vID) REFERENCES AppView(vID)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	FOREIGN KEY (sID) REFERENCES Script(sID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);
	

CREATE TABLE Control_Script(
	ctrID INT NOT NULL,
	sID INT NOT NULL,
	PRIMARY KEY (ctrID, sID),
	FOREIGN KEY (ctrID) REFERENCES Controller(ctrID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (sID) REFERENCES Script(sID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
	);
	

CREATE TABLE ValueType(
	vType VARCHAR(30) NOT NULL PRIMARY KEY
	);
	
INSERT INTO ValueType VALUES ('string');
INSERT INTO ValueType VALUES ('number');
INSERT INTO ValueType VALUES ('date');
INSERT INTO ValueType VALUES ('listof<NameValuePair>');
	

CREATE TABLE NameValuePair(
	nvpID INT NOT NULL,
	nvpName VARCHAR(50) NOT NULL,
	nvpType VARCHAR(30) NOT NULL,
	nvpValue VARCHAR(100),
	parentNVP INT,
	mdID INT,
	sID INT,
	PRIMARY KEY (nvpID),
	FOREIGN KEY (nvpType) REFERENCES ValueType(vType),
	FOREIGN KEY (parentNVP) REFERENCES NameValuePair(nvpID)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	FOREIGN KEY (mdID) REFERENCES Model(mdID)
		ON DELETE SET NULL
		ON UPDATE CASCADE,
	FOREIGN KEY (sID) REFERENCES Script(sID)
		ON DELETE SET NULL
		ON UPDATE CASCADE
	);
	

CREATE TABLE Display(
	vID INT NOT NULL,
	dataID INT NOT NULL,
	PRIMARY KEY (vID, dataID),
	FOREIGN KEY (vID) REFERENCES AppView(vID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (dataID) REFERENCES NameValuePair(nvpID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
	);
	
### Insert statements ###
#Developer Table
INSERT INTO Developer VALUES ('1','Annie','White');
INSERT INTO Developer VALUES ('2','Bob','Smith');
INSERT INTO Developer VALUES ('3','Cate','Keeley');
INSERT INTO Developer VALUES ('4','David','Hupper');
INSERT INTO Developer VALUES ('5','Eddie','Grant');
INSERT INTO Developer VALUES ('6','Frank','Seward');

#Application
INSERT INTO Application VALUES ('1', 'Content Management System', 'Internet Explorer', '2005-06-02','www.CMS.com', '12', NULL, NULL,'web');
INSERT INTO Application VALUES ('2', 'Yelp', 'IOS', '2003-04-21', NULL, NULL,'2003-10-23 08:23:00', NULL, 'mobile');
INSERT INTO Application VALUES ('3', 'Eclipse', 'Windows 7', '1998-10-26', NULL, NULL, NULL,'30', 'desktop');
INSERT INTO Application VALUES ('4', 'Dropbox', 'Android', '2001-11-16', NULL, NULL, '2002-03-11 09:15:00', NULL, 'mobile');
INSERT INTO Application VALUES ('5', 'Student Management System', 'FireFox', '1999-05-19','www.SMS.com', '8',NULL, NULL, 'web');
INSERT INTO Application VALUES ('6', 'Sequel Pro', 'MacOS', '2005-07-10', NULL, NULL, NULL,'5', 'destopp');

#Dev_App
INSERT INTO Dev_App VALUES ('1', '4', 'userExperience');
INSERT INTO Dev_App VALUES ('2', '5', 'architect');
INSERT INTO Dev_App VALUES ('5', '1', 'databaseAdmin');
INSERT INTO Dev_App VALUES ('3', '2', 'projectManager');
INSERT INTO Dev_App VALUES ('6', '3', 'backendDeveloper');
INSERT INTO Dev_App VALUES ('4', '6', 'frontendDeveloper');

#AppView
INSERT INTO AppView VALUES ('1', 'homepage', NULL, '1');
INSERT INTO AppView VALUES ('2', 'showResturant', NULL, '2');
INSERT INTO AppView VALUES ('3', 'download', NULL, '4');
INSERT INTO AppView VALUES ('4', 'menu', '1', '1');
INSERT INTO AppView VALUES ('5', 'studentProfile', NULL, '5');
INSERT INTO AppView VALUES ('6', 'tableTree', NULL, '6');

#Controller
INSERT INTO Controller VALUES ('1', 'addNewStudent', '5');
INSERT INTO Controller VALUES ('2', 'searchResturant', '2');
INSERT INTO Controller VALUES ('3', 'addTable', '6');
INSERT INTO Controller VALUES ('4', 'downloadFile', '4');
INSERT INTO Controller VALUES ('5', 'newJaveProject', '3');
INSERT INTO Controller VALUES ('6', 'editWebpage', '1');

#Model
INSERT INTO Model VALUES ('1', 'user', '4');
INSERT INTO Model VALUES ('2', 'student', '5');
INSERT INTO Model VALUES ('3', 'database', '6');
INSERT INTO Model VALUES ('4', 'resturants', '2');
INSERT INTO Model VALUES ('5', 'project', '3');
INSERT INTO Model VALUES ('6', 'website', '1');

#Script
INSERT INTO Script VALUES ('1', 'downloadFile', '3'); 
INSERT INTO Script VALUES ('2', 'addFavorite', '1');
INSERT INTO Script VALUES ('3', 'searchByID', '6');
INSERT INTO Script VALUES ('4', 'searchByID', '2');
INSERT INTO Script VALUES ('5', 'searchByLoc', '4');
INSERT INTO Script VALUES ('6', 'searchByName', '5');

#EventHandler
INSERT INTO EventHandler VALUES ('1', 'addtoFavorite', '2','2');
INSERT INTO EventHandler VALUES ('2', 'refresh', '4','4');
INSERT INTO EventHandler VALUES ('3', 'downloadFile', '3','1');
INSERT INTO EventHandler VALUES ('4', 'deleteTable', '6','3');
INSERT INTO EventHandler VALUES ('5', 'createNewWebsite', '1','6');
INSERT INTO EventHandler VALUES ('6', 'editProfile', '5','5');

#Control_Script
INSERT INTO Control_Script VALUES ('2', '6');
INSERT INTO Control_Script VALUES ('6', '4');
INSERT INTO Control_Script VALUES ('5', '3');
INSERT INTO Control_Script VALUES ('1', '5');
INSERT INTO Control_Script VALUES ('3', '4');
INSERT INTO Control_Script VALUES ('4', '1');

#NameValuePair
INSERT INTO NameValuePair VALUES('1','studentGPA','number','3',NULL,'2','3');
INSERT INTO NameValuePair VALUES('2','username','string','admin',NULL,'1','5');
INSERT INTO NameValuePair VALUES('3','keywords','listof<NameValuePair>',NULL,NULL,'4','6');
INSERT INTO NameValuePair VALUES('4','keyword','string','sushi','3','4','6');
INSERT INTO NameValuePair VALUES('5','createDate','date','2008-08-11',NULL,'3','4');
INSERT INTO NameValuePair VALUES('6','keyword','string','seafood','3','4','6');

#Display
INSERT INTO Display VALUES ('5','1');
INSERT INTO Display VALUES ('1','2');
INSERT INTO Display VALUES ('2','3');
INSERT INTO Display VALUES ('6','5');


### Select statements ###
SELECT * FROM Developer;
SELECT * FROM Application;
SELECT * FROM RoleType;
SELECT * FROM Dev_App;
SELECT * FROM AppView;
SELECT * FROM Controller;
SELECT * FROM Model;
SELECT * FROM EventHandler;
SELECT * FROM Script;
SELECT * FROM Control_Script;
SELECT * FROM NameValuePair;
SELECT * FROM ValueType;
SELECT * FROM Display;

# list all the developers and applications with thier roles
SELECT d.dFirstName, a.appName, da.role 
	FROM Dev_App da 
	JOIN Developer d ON d.devID = da.devID 
	JOIN Application a ON a.appID = da.appID;
	
# show the number of the event handler of all the views that belong to applications whose target is "IOS" and the name the application
SELECT av.vName, COUNT(eh.ehID), a.appName FROM AppView av, EventHandler eh, Application a
	WHERE a.target = 'IOS' AND a.appID = av.appID AND av.vID = eh.vID
	GROUP BY eh.vID;

# list the names of the controller and the names of the related applications
SELECT c.ctrName, a.appName FROM Application a, Controller c
	WHERE a.appID = c.appID;
	
# list the all the name value pairs of a particular application: "Yelp"
SELECT nvp.nvpName, nvp.nvpType, nvp.nvpValue
	FROM Application a, Model m, NameValuePair nvp
	WHERE a.appName = 'Yelp' AND a.appID = m.appID AND m.mdID = nvp.mdID;
	
# list names of the views that have parent views and the names of their parent views
SELECT av1.vName, av2.vName FROM AppView av1 
	JOIN AppView av2 ON av1.parentID = av2.vID;
	
# list names and values of the name value pairs that have parents and the names of their parent name value pairs
SELECT nvp1.nvpName, nvp1.nvpValue, nvp2.nvpName FROM NameValuePair nvp1, NameValuePair nvp2
	WHERE nvp1.parentNVP = nvp2.nvpID;
	
# list the names, types and values of the name value pairs that controlled by controller "addTable"
SELECT nvp.nvpName,nvp.nvpType, nvp.nvpValue FROM Controller c, Control_Script cs, Script s, NameValuePair nvp
	WHERE c.ctrName = 'addTable' AND c.ctrID = cs.ctrID AND cs.sID = s.sID AND s.sID = nvp.sID;
	
# list the names of the views that are defined by the running results of the event handler "addtoFavorite"
SELECT av.vName FROM EventHandler eh, Script s, AppView av
	WHERE eh.ehName = 'addtoFavorite' AND eh.sID = s.sID AND s.targetView = av.vID;
	
# list the values of name value pairs that display in a particular view "tableTree"
SELECT nvp.nvpValue FROM AppView av, Display d, NameValuePair nvp
	WHERE av.vName = 'tableTree' AND av.vID = d.vID AND d.dataID = nvp.nvpID;
		