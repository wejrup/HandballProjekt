package com.wejrup.handballprojekt.controller.data;

import com.wejrup.handballprojekt.domain.Event;
import com.wejrup.handballprojekt.domain.Match;
import com.wejrup.handballprojekt.domain.Team;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("CallToPrintStackTrace")
public class Database {
    private static Connection connection;

    public static boolean openConnection(String databaseName) {
        String connectionString =
                "jdbc:sqlserver://localhost:1433;" +
                        "instanceName=SQLEXPRESS;" +
                        "databaseName=" + databaseName + ";" +
                        "integratedSecurity=true;" +
                        "trustServerCertificate=true;";

        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Connected to database");
            return true;
        } catch (SQLException e) {
            System.out.println("Could not connect to database!");
            e.printStackTrace();
            return false;
        }
    }


    public static ArrayList<Team> selectAllTeams() {

        if (!openConnection("HåndboldDB")) return null;

        String sql = "SELECT teamid, teamname, points FROM team";

        ArrayList<Team> teams = new ArrayList<Team>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("teamid");
                String name = rs.getString("teamname");
                int points = rs.getInt("points");


                Team team = new Team(id, name, points);

                teams.add(team);

                System.out.println(id + " - " + team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public static int createMatch(int homeTeamId, int awayTeamId) {

        String sql = """
        INSERT INTO Match (hometeamID, awayteamID, hometeamgoals, awayteamgoals)
        OUTPUT INSERTED.MatchID
        VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, homeTeamId);
            ps.setInt(2, awayTeamId);
            ps.setInt(3,0);
            ps.setInt(4,0);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // det nye MatchID
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // hvis noget går galt
    }

    public static void addEvent(Event event) {
        String sql = """
        INSERT INTO dbo.[event] (matchid, eventtype, totalseconds, teamid, currentscore, teamside)
        VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, event.getMatchID());
            ps.setString(2, event.getType().name());
            ps.setInt(3, event.getEventTimestampSeconds());
            ps.setInt(4, event.getTeamID());
            ps.setString(5,event.getCurrentScore());
            ps.setString(6,event.getTeamSide().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTeam(String name) {
        String sql = "INSERT INTO team (teamname, points) VALUES (?, 0)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);

            ps.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Fejl ved oprettelse af team", e);

        }
    }

    public static void updateTeam(int teamId, String teamName, int points) {
        String sql = """
        UPDATE team
        SET teamname = ?, points = ?
        WHERE teamid = ?;
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, teamName);
            ps.setInt(2, points);
            ps.setInt(3, teamId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved opdatering af team", e);
        }
    }

    public static void deleteTeam(int teamId) {
        String sql = "DELETE FROM team WHERE teamid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teamId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved sletning af team", e);
        }
    }

    public static ArrayList<Match> selectAllMatches() {

        String sql = """
        SELECT matchid, hometeamID, awayteamID, hometeamgoals, awayteamgoals
        FROM dbo.[match];
        """;

        ArrayList<Match> matches = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int matchId = rs.getInt("matchid");
                int homeTeamId = rs.getInt("hometeamID");
                int awayTeamId = rs.getInt("awayteamID");
                int homeTeamGoals = rs.getInt("hometeamgoals");
                int awayTeamGoals = rs.getInt("awayteamgoals");

                Match match = new Match(matchId, selectTeamById(homeTeamId), selectTeamById(awayTeamId));
                match.setHomeTeamGoals(homeTeamGoals);
                match.setAwayTeamGoals(awayTeamGoals);
                matches.add(match);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    public static Team selectTeamById(int teamId) {

        String sql = """
        SELECT teamid, teamname, points
        FROM team
        WHERE teamid = ?;
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, teamId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    int id = rs.getInt("teamid");
                    String name = rs.getString("teamname");
                    int points = rs.getInt("points");

                    return new Team(id, name, points);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // hvis intet team findes med det id
    }

    public static void updateMatchGoals(int matchId, int homeTeamGoals, int awayTeamGoals) {
        String sql = """
        UPDATE dbo.[match]
        SET hometeamgoals = ?, awayteamgoals = ?
        WHERE matchid = ?;
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, homeTeamGoals);
            ps.setInt(2, awayTeamGoals);
            ps.setInt(3, matchId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved opdatering af mål for match", e);
        }
    }

    public static ArrayList<Event> selectEventsByMatchId(int matchId) {

        String sql = """
        SELECT matchid, eventtype, totalseconds, teamid, currentscore, teamside
        FROM dbo.[event]
        WHERE matchid = ?;
        """;

        ArrayList<Event> events = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, matchId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    int mId = rs.getInt("matchid");
                    String typeString = rs.getString("eventtype");
                    int totalSeconds = rs.getInt("totalseconds");
                    int teamId = rs.getInt("teamid");
                    String currentScore = rs.getString("currentscore");
                    String sideString = rs.getString("teamside");

                    Event.EventType type = Event.EventType.valueOf(typeString);
                    Event.TeamSide side = Event.TeamSide.valueOf(sideString);

                    Event event = new Event(mId, type, totalSeconds, teamId, side, currentScore);
                    events.add(event);

                    System.out.println(event);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }




}
