package org.shu.ui;

import org.shu.core.ChatGPT;
import org.shu.core.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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

        ConnectionManager connectionManager = ConnectionManager.getInstance();
        HashMap<String, String> config = connectionManager.getConfiguration();
        apiElevenLabsEndpointField.setText(config.get("apiElevenLabsEndpoint"));
        apiElevenLabsTokenField.setText(config.get("apiElevenLabsToken"));
        apiChatGptEndpointField.setText(config.get("apiChatGptEndpoint"));
        apiChatGptTokenField.setText(config.get("apiChatGptToken"));
        promptChatGptField.setText(config.get("promptChatGpt"));
        modelChatGptField.setText(config.get("modelChatGpt"));
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
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        connectionManager.saveConfiguration(apiElevenLabsEndpointField.getText(),
                apiElevenLabsTokenField.getText(),
                apiChatGptEndpointField.getText(),
                apiChatGptTokenField.getText(),
                promptChatGptField.getText(),
                modelChatGptField.getText());
        //Reload ChatGPT
        ChatGPT.setModel(promptChatGptField.getText());
        ChatGPT.setApiToken(apiChatGptTokenField.getText());
        ChatGPT.setApiEndpoint(apiChatGptEndpointField.getText());
        ChatGPT.setPrompt(promptChatGptField.getText());
    }
}