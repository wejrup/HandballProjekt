-- hvis vi står i HåndboldDB kan vi ikke slette den, så derfor
-- skifter vi til master databasen, da den altid findes.
USE master;
GO
 
IF DB_ID('HåndboldDB') IS NOT NULL
  BEGIN
    ALTER DATABASE HåndboldDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HåndboldDB;
END
  GO
 
--HåndboldDB findes ikke
 
CREATE DATABASE HåndboldDB;
GO -- gør alt hertil, inden vi går videre
 
-- skifter til HåndboldDB, så vi kan udføre sql-sætninger på den.
USE HåndboldDB;
GO
 
IF OBJECT_ID('dbo.team', 'U') IS NOT NULL
    DROP TABLE dbo.team;
GO
 
CREATE TABLE team (
  -- IDENTITY autogenererer nøgler, her fra 100 og opefter
  -- i spring på 1, dvs. 100, 101, 102, osv.
  teamid int IDENTITY(0, 1) NOT NULL,
  teamname nvarchar(20) NOT NULL,
  points int,
 
  PRIMARY KEY (teamid)
);
GO
INSERT INTO team VALUES ('Flemming', 48);
INSERT INTO team VALUES ('Aalborg', 36);
INSERT INTO team VALUES ('Skanderborg', 38);
INSERT INTO team VALUES ('Mors Thy', 23);
INSERT INTO team VALUES ('GOG',  22);
INSERT INTO team VALUES ('Skjern', 21);
INSERT INTO team VALUES ('Bjerringbro', 18);
INSERT INTO team VALUES ('TTH', 42);
INSERT INTO team VALUES ('Sønderjyske', 17);
INSERT INTO team VALUES ('Høj', 15);
INSERT INTO team VALUES ('Fredericia', 15);
INSERT INTO team VALUES ('Ringsted', 14);
INSERT INTO team VALUES ('Ribe-Esbjerg', 13);
INSERT INTO team VALUES ('Nordsjælland', 13);
INSERT INTO team VALUES ('Grindsted', 3);
GO
 
 
-- Drop table, hvis den allerede findes
IF OBJECT_ID('dbo.standings', 'U') IS NOT NULL
    DROP TABLE dbo.standings;
GO
 
-- Opret standings som liga
CREATE TABLE dbo.standings (
    standingsID INT IDENTITY(1,1) NOT NULL,  -- unik ID for hver liga
    leagueName NVARCHAR(50) NOT NULL,        -- navn på ligaen
    PRIMARY KEY (standingsID)
);
GO
 
-- Indsæt en liga (alle hold i denne liga får samme standingsID)
INSERT INTO dbo.standings (leagueName)
VALUES ('Håndboldligaen');  -- alle nuværende hold hører til denne liga
GO
 
-- Drop table, hvis den allerede findes
IF OBJECT_ID('dbo.standings_entry', 'U') IS NOT NULL
    DROP TABLE dbo.standings_entry;
GO
 
-- opret table 
CREATE TABLE dbo.standings_entry (
    teamid INT NOT NULL,            -- primærnøgle: unikt hold
    standingsID INT NOT NULL,       -- refererer til hvilken liga holdet hører til
    teamname NVARCHAR(20) NOT NULL, -- holdnavnet
    points INT NOT NULL,            -- holdets point
    position INT NOT NULL,          -- position

    PRIMARY KEY (teamid),
    FOREIGN KEY (standingsID) REFERENCES dbo.standings(standingsID)
);
GO
 
-- Indsæt alle hold med samme standingsID (alle i samme liga)
INSERT INTO dbo.standings_entry (teamid, standingsID, teamname, points, position)
SELECT
    t.teamid,
    1 AS standingsID,  -- alle peger på liga med ID=1
    t.teamname,
    t.points,
    ROW_NUMBER() OVER (ORDER BY t.points DESC, t.teamname ASC) AS position
FROM team t;
GO
 
 
-- Drop view, hvis det allerede findes
IF OBJECT_ID('dbo.vw_standings', 'V') IS NOT NULL
    DROP VIEW dbo.vw_standings;
GO
 
-- Opret view for ligaen med standingsID = 1
CREATE VIEW dbo.vw_standings AS
SELECT 
    position,
    teamname,
    points
FROM dbo.standings_entry
WHERE standingsID = 1;
GO
 
-- Drop match-tabellen hvis den allerede findes
IF OBJECT_ID('dbo.match', 'U') IS NOT NULL
    DROP TABLE dbo.match;
GO
 
-- Opret match-tabel
CREATE TABLE dbo.match (
    matchid INT IDENTITY(1,1) NOT NULL,   -- unik kamp-ID
    hometeamID INT NOT NULL,              -- hjemmehold
    awayteamID INT NOT NULL,              -- udehold
    hometeamgoals INT,                    -- hjemme holdets antla mål
    awayteamgoals INT,                    -- Ude holdets antal mål
 
    PRIMARY KEY (matchid),
 
    -- Begge hold skal findes i team-tabellen
    FOREIGN KEY (hometeamID) REFERENCES dbo.team(teamid),
    FOREIGN KEY (awayteamID) REFERENCES dbo.team(teamid),
 
);
GO
 
-- Drop event-tabellen hvis den allerede findes
IF OBJECT_ID('dbo.event', 'U') IS NOT NULL
    DROP TABLE dbo.event;
GO
 
-- Opret event-tabel
CREATE TABLE dbo.event (
    eventid INT IDENTITY(1,1) NOT NULL,   -- unik hændelse i en kamp
    matchid INT NOT NULL,                 -- hvilken kamp hændelsen skete i
    eventtype NVARCHAR(50) NOT NULL,      -- fx 'Goal', 'Penalty', 'Timeout'
    totalseconds INT NOT NULL,            -- tidspunkt i kampen (sekunder siden start)
    teamid INT NOT NULL,                  -- holdet som hændelsen hører til
    currentscore NVARCHAR(50) NOT NULL,   -- den aktuelle score i kampen ved eventet 
    teamside NVARCHAR(50) NOT NULL,       -- home eller away
 
    PRIMARY KEY (eventid),
 
    -- refererer til kampen
    FOREIGN KEY (matchid) REFERENCES dbo.match(matchid),
    -- refererer til holdet
    FOREIGN KEY (teamid) REFERENCES dbo.team(teamid),
 
 
);
GO


-- TESTDATA TIL MATCH OG EVENT

-- Indsæt 2 matches
INSERT INTO dbo.match (hometeamID, awayteamID, hometeamgoals, awayteamgoals)
VALUES (6, 0, 3, 2);   -- TTH vs Aalborg

INSERT INTO dbo.match (hometeamID, awayteamID, hometeamgoals, awayteamgoals)
VALUES (3, 4, 1, 1);   -- GOG vs Skjern
GO

-- Match 1 events (TTH vs Aalborg, 3-2)
INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Goal', 45, 6, '1 - 0', 'Home');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Goal', 120, 0, '1 - 1', 'Away');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Goal', 240, 6, '2 - 1', 'Home');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Suspension', 310, 0, '2 - 1', 'Away');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Goal', 410, 6, '3 - 1', 'Home');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (1, 'Goal', 520, 0, '3 - 2', 'Away');
GO

-- Match 2 events (GOG vs Skjern, 1-1)
INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (2, 'Goal', 90, 3, '1 - 0', 'Home');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (2, 'Suspension', 180, 4, '1 - 0', 'Away');

INSERT INTO dbo.event (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
VALUES (2, 'Goal', 350, 4, '1 - 1', 'Away');
GO
