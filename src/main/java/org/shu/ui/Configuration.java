package org.shu.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Configuration {
    private JTextField apiElevenLabsEndpointField;
    private JTextField apiElevenLabsTokenField;
    private JTextField apiChatGptEndpointField;
    private JTextField apiChatGptTokenField;
    private JTextField promptChatGptField;
    private JTextField modelChatGptField;

    public Configuration() {
        // Initialize the fields with default values
        apiElevenLabsEndpointField = new JTextField("");
        apiElevenLabsTokenField = new JTextField("");
        apiChatGptEndpointField = new JTextField("https://api.pawan.krd");
        apiChatGptTokenField = new JTextField("");
        promptChatGptField = new JTextField("You are an helpful assistant.");
        modelChatGptField = new JTextField("gpt-3.5-turbo");

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:config.db");
            Statement stmt = conn.createStatement();
            // Create the table if it does not exist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS config (id INTEGER PRIMARY KEY, apiElevenLabsEndpoint TEXT, apiElevenLabsToken TEXT, apiChatGptEndpoint TEXT, apiChatGptToken TEXT, promptChatGpt TEXT, modelChatGpt TEXT)");
            // Query the table for the values and set the fields
            ResultSet rs = stmt.executeQuery("SELECT * FROM config WHERE id = 1");
            if (rs.next()) {
                apiElevenLabsEndpointField.setText(rs.getString("apiElevenLabsEndpoint"));
                apiElevenLabsTokenField.setText(rs.getString("apiElevenLabsToken"));
                apiChatGptEndpointField.setText(rs.getString("apiChatGptEndpoint"));
                apiChatGptTokenField.setText(rs.getString("apiChatGptToken"));
                promptChatGptField.setText(rs.getString("promptChatGpt"));
                modelChatGptField.setText(rs.getString("modelChatGpt"));
            }
            // Close the database connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // If there was an error connecting to the database or querying the table,
            // set the fields to empty values
            apiElevenLabsEndpointField.setText("");
            apiElevenLabsTokenField.setText("");
            apiChatGptEndpointField.setText("");
            apiChatGptTokenField.setText("");
            promptChatGptField.setText("");
            modelChatGptField.setText("");
        }
    }

    public void openUI() {
        // Create the edit UI
        JFrame editFrame = new JFrame("Edit Configuration");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setSize(400, 300);

        // Create the form
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("API_ENDPOINT_ELEVENLABS:"));
        panel.add(apiElevenLabsEndpointField);
        panel.add(new JLabel("API_TOKEN_ELEVENLABS:"));
        panel.add(apiElevenLabsTokenField);
        panel.add(new JLabel("API_ENDPOINT_CHATGPT:"));
        panel.add(apiChatGptEndpointField);
        panel.add(new JLabel("API_TOKEN_CHATGPT:"));
        panel.add(apiChatGptTokenField);
        panel.add(new JLabel("PROMPT_CHATGPT:"));
        panel.add(promptChatGptField);
        panel.add(new JLabel("MODEL_CHATGPT:"));
        panel.add(modelChatGptField);

        // Add padding to the form panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        editFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // Create the save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfiguration();
                editFrame.dispose();
            }
        });
        editFrame.getContentPane().add(saveButton, BorderLayout.SOUTH);

        // Show the edit UI
        editFrame.setVisible(true);
    }

    private void saveConfiguration() {
        // Save the values from the fields to an SQLite database
        String apiElevenLabsEndpoint = apiElevenLabsEndpointField.getText();
        String apiElevenLabsToken = apiElevenLabsTokenField.getText();
        String apiChatGptEndpoint = apiChatGptEndpointField.getText();
        String apiChatGptToken = apiChatGptTokenField.getText();
        String promptChatGpt = promptChatGptField.getText();
        String modelChatGpt = modelChatGptField.getText();
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:config.db");
            Statement stmt = conn.createStatement();
            // Create the table if it does not exist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS config (id INTEGER PRIMARY KEY, apiElevenLabsEndpoint TEXT, apiElevenLabsToken TEXT, apiChatGptEndpoint TEXT, apiChatGptToken TEXT, promptChatGpt TEXT, modelChatGpt TEXT)");
            // Insert or update the data in the table
            PreparedStatement pstmt = conn.prepareStatement("INSERT OR REPLACE INTO config (id, apiElevenLabsEndpoint, apiElevenLabsToken, apiChatGptEndpoint, apiChatGptToken, promptChatGpt, modelChatGpt) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 1);
            pstmt.setString(2, apiElevenLabsEndpoint);
            pstmt.setString(3, apiElevenLabsToken);
            pstmt.setString(4, apiChatGptEndpoint);
            pstmt.setString(5, apiChatGptToken);
            pstmt.setString(6, promptChatGpt);
            pstmt.setString(7, modelChatGpt);
            pstmt.executeUpdate();
            // Close the database connection
            pstmt.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}