#1. Create Productivity applications: Keynote, Pages, Outlook, Word, PowerPoint, and Numbers#
INSERT INTO Application (appName, created, category) VALUES ('Keynote', '2009-2-14', 'PRODUCTIVITY');
INSERT INTO Application (appName, created, category) VALUES ('Pages', '2009-5-24', 'PRODUCTIVITY');
INSERT INTO Application (appName, created, category) VALUES ('Outlook', '1999-3-5', 'PRODUCTIVITY');
INSERT INTO Application (appName, category) VALUES ('Word', 'PRODUCTIVITY');
INSERT INTO Application (appName, category) VALUES ('PowerPoint', 'PRODUCTIVITY');
INSERT INTO Application (appName, category) VALUES ('Numbers', 'PRODUCTIVITY');

INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Keynote'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Pages'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Outlook'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Word'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'PowerPoint'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Numbers'));

#2. Create Game applications: Minecraft, Asteroids, Space Invaders, Galaga, and Centipide#
INSERT INTO Application (appName, created, category, price) VALUES ('Minecraft', '2012-6-28', 'GAMES', 6.99);
INSERT INTO Application (appName, created, category, price) VALUES ('Asteroids', '2010-2-18', 'GAMES', 4.99);
INSERT INTO Application (appName, created, category, price) VALUES ('Space Invaders', '2009-5-11', 'GAMES', 5.99);
INSERT INTO Application (appName, created, category) VALUES ('Galaga', '2004-6-18', 'GAMES');
INSERT INTO Application (appName, created, category) VALUES ('Centipide', '2005-7-15', 'GAMES');

INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Minecraft'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Asteroids'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Space Invaders'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Galaga'));
INSERT INTO WebApplication (Id) VALUES 
((SELECT Id FROM Application WHERE appName = 'Centipide'));

#3. Create the the Developers with the the roles in the specified Application categories#
INSERT INTO Developer (firstName, lastName) VALUES ('Alice', 'Wonderland');
INSERT INTO Developer (firstName, lastName) VALUES ('Bob', 'Marley');
INSERT INTO Developer (firstName, lastName) VALUES ('Charly', 'Garcia');
INSERT INTO Developer (firstName, lastName) VALUES ('Frank', 'Herbert');
INSERT INTO Developer (firstName, lastName) VALUES ('Gregory', 'Peck');
INSERT INTO Developer (firstName, lastName) VALUES ('Edward', 'Norton');

CREATE VIEW Role_Apps AS
	SELECT d.Id as developer, d.firstName, d.lastName, a.Id as application, a.category, rt.roleType
	FROM Developer d JOIN Application a JOIN RoleType rt;
#(1)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Alice' AND lastName = 'Wonderland' AND category = 'GAMES' AND roleType = 'ARCHITECT');
#(2)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Bob' AND lastName = 'Marley' AND category = 'GAMES' AND roleType = 'USER EXPERIENCE');
#(3)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Charly' AND lastName = 'Garcia' AND category = 'GAMES' AND roleType = 'DEVELOPER');
#(4)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Frank' AND lastName = 'Herbert' AND category = 'PRODUCTIVITY' AND roleType = 'PROJECT MANAGER');
#(5)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Gregory' AND lastName = 'Peck' AND category = 'GAMES' AND roleType = 'PRODUCT MANAGER');
#(6)#
INSERT INTO Role (application, developer, role) 
	(SELECT application, developer, roleType 
		FROM Role_Apps 
		WHERE firstName = 'Edward' AND lastName = 'Norton' AND category = 'PRODUCTIVITY' AND roleType = 'USER EXPERIENCE');

#4#
INSERT INTO Privilege VALUES (
	(SELECT Id FROM Application WHERE appName = 'Centipede'),
	(SELECT Id FROM Developer WHERE firstName = 'Charly' AND lastName ='Garcia'),
	'ALL', 'VIEW');
	
INSERT INTO Privilege VALUES (
	(SELECT Id FROM Application WHERE appName = 'Outlook'),
	(SELECT Id FROM Developer WHERE firstName = 'Edward' AND lastName ='Norton'),
	'UPDATE', 'SCRIPT');

#5#
CREATE VIEW Developer_Role AS
	SELECT d.Id as developerId, d.firstName, d.lastName, r.role
	FROM Developer d JOIN Role r ON d.Id = r.developer;
	
UPDATE Developer_Role 
	SET role = 'PRODUCT MANAGER' 
	WHERE firstName = 'Frank' AND lastName = 'Herbert' AND role = 'PROJECT MANAGER';
	
#6#
CREATE VIEW Web_Role AS
	SELECT d.firstName, d.lastName, wa.Id as webApplication, r.role
	FROM Developer d, WebApplication wa, Role r
	WHERE d.Id = r.developer AND wa.Id = r.application;
	
UPDATE Web_Role SET role = 'DIRECTOR'
	WHERE firstName = 'Gregory' AND lastName = 'Peck';

#7#
DELETE FROM Privilege 
	WHERE applicationId = (SELECT Id FROM Application WHERE appName = 'Outlook')
	AND developerId = (SELECT Id FROM Developer WHERE firstName = 'Edward' AND lastName = 'Norton')
	AND privilege = 'UPDATE'
	AND assetType = 'SCRIPT';
	
#8#
DELETE FROM Privilege
	WHERE applicationId = (SELECT Id FROM Application WHERE appName = 'PowerPoint');
	
DELETE FROM Application WHERE appName = 'PowerPoint';

#9#
CREATE VIEW Previous_Sale AS
	SELECT applicationId, MONTH(sold) as sold_month, SUM(quantity) as sold_num
	FROM Sales
	WHERE DATE_FORMAT(sold, '%Y-%M') = DATE_FORMAT(now() - interval 1 month, '%Y-%M')
	GROUP BY applicationId;
	
CREATE VIEW Current_Sale AS
	SELECT applicationId, MONTH(sold) as sold_month, SUM(quantity) as sold_num
	FROM Sales
	WHERE DATE_FORMAT(sold, '%Y-%M') = DATE_FORMAT(now(), '%Y-%M')
	GROUP BY applicationId;
	
CREATE VIEW Decline_10 AS
	SELECT cs.*
	FROM Current_Sale cs JOIN Previous_Sale ps ON cs.applicationId = ps.applicationId
	WHERE (ps.sold_num - cs.sold_num)/ps.sold_num >= 0.1;
	
SELECT r.developer, r.role, r.application, ast.assetType
FROM WebApplication wa, Role r, Decline_10 d, Asset ast
WHERE wa.Id = r.application AND r.application = d.applicationId AND d.applicationId = ast.applicationId
	AND r.role = 'PRODUCT MANAGER' AND ast.assetType = 'VIEW'
GROUP BY r.developer, r.application;
	
#10#
SELECT app.appName, ast.assetType
	FROM Developer d, Application app, Role r, Asset ast
	WHERE d.Id = r.developer AND app.Id = r.application AND r.application = ast.applicationId
	AND d.firstName = 'Alice' AND d.lastName = 'Wonderland' AND r.role = 'DEVELOPER' AND ast.assetType = 'CONTROLLER';

#11#
SELECT app.category, SUM(app.price*s.quantity) as revenue
	FROM Application app, Sales s
	WHERE app.Id = s.applicationId
	GROUP BY app.category
	ORDER BY revenue desc LIMIT 1;

#12#
SELECT d.Id, d.firstName, d.lastName, SUM(s.quantity) AS soldNum
	FROM Developer d, Sales s, WebApplication wa
	WHERE d.Id = s.developerId AND s.applicationId = wa.Id AND MONTH(s.sold) = MONTH(CURRENT_DATE)
	GROUP BY s.developerId
	ORDER BY soldNum desc LIMIT 1;


