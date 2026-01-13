package com.wejrup.handballprojekt;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Connection connection;

    private static boolean openConnection(String databaseName) {
        String connectionString =
                "jdbc:sqlserver://localhost:1433;" +
                        "instanceName=SQLEXPRESS;" +
                        "databaseName=" + databaseName + ";" +
                        "integratedSecurity=true;" +
                        "trustServerCertificate=true;";

        connection = null;

        try {
            System.out.println("Connecting to database...");

            connection = DriverManager.getConnection(connectionString);

            System.out.println("Connected to database");

            return true;
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database!");
            System.out.println(e.getMessage());

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
        INSERT INTO Match (HomeTeamID, AwayTeamID)
        OUTPUT INSERTED.MatchID
        VALUES (?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, homeTeamId);
            ps.setInt(2, awayTeamId);

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
        INSERT INTO dbo.[event] (matchid, eventtype, totalseconds, teamid)
        VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, event.getMatchID());
            ps.setString(2, event.getType().name());
            ps.setInt(3, event.getEventTimestampSeconds());
            ps.setInt(4, event.getTeamID());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
