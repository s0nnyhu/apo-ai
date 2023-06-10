package org.shu.core;

import java.sql.*;
import java.util.HashMap;

public class ConnectionManager {
    private static ConnectionManager instance;
    private static final String DB_URL = "jdbc:sqlite:config.db";

    private ConnectionManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public synchronized void saveConfiguration(String apiElevenLabsEndpoint, String apiElevenLabsToken, String apiChatGptEndpoint, String apiChatGptToken, String promptChatGpt, String modelChatGpt) {
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS config (id INTEGER PRIMARY KEY, apiElevenLabsEndpoint TEXT, apiElevenLabsToken TEXT, apiChatGptEndpoint TEXT, apiChatGptToken TEXT, promptChatGpt TEXT, modelChatGpt TEXT)");

            PreparedStatement pstmt = conn.prepareStatement("INSERT OR REPLACE INTO config (id, apiElevenLabsEndpoint, apiElevenLabsToken, apiChatGptEndpoint, apiChatGptToken, promptChatGpt, modelChatGpt) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 1);
            pstmt.setString(2, apiElevenLabsEndpoint);
            pstmt.setString(3, apiElevenLabsToken);
            pstmt.setString(4, apiChatGptEndpoint);
            pstmt.setString(5, apiChatGptToken);
            pstmt.setString(6, promptChatGpt);
            pstmt.setString(7, modelChatGpt);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized HashMap<String, String> getConfiguration() {
        HashMap<String, String> config = new HashMap<>();
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS config (id INTEGER PRIMARY KEY, apiElevenLabsEndpoint TEXT, apiElevenLabsToken TEXT, apiChatGptEndpoint TEXT, apiChatGptToken TEXT, promptChatGpt TEXT, modelChatGpt TEXT)");

            ResultSet rs = stmt.executeQuery("SELECT * FROM config WHERE id = 1");
            if (rs.next()) {
                config.put("apiElevenLabsEndpoint", rs.getString("apiElevenLabsEndpoint"));
                config.put("apiElevenLabsToken", rs.getString("apiElevenLabsToken"));
                config.put("apiChatGptEndpoint", rs.getString("apiChatGptEndpoint"));
                config.put("apiChatGptToken", rs.getString("apiChatGptToken"));
                config.put("promptChatGpt", rs.getString("promptChatGpt"));
                config.put("modelChatGpt", rs.getString("modelChatGpt"));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return config;
    }
}
