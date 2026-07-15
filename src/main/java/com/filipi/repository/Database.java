package com.filipi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URI = "jdbc:sqlite:park_app.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(Database.URI);
    }

    private static void createTables() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tickets (
                    id TEXT PRIMARY KEY,
                    vehiclePlate TEXT NOT NULL,
                    vehicleType TEXT NOT NULL,
                    vehicleName TEXT NOT NULL,
                    startAt TEXT DEFAULT CURRENT_TIMESTAMP,
                    endAt TEXT NULL
                );
                """;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error on try to create tables", e);
        }
    }
}
