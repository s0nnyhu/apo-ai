package org.shu.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Whisper {

    private final static String timePattern = "\\[\\d{2}:\\d{2}.\\d{3} --> \\d{2}:\\d{2}.\\d{3}\\] ";

    public static String transcribe(String command) {
        String result = null;
        try {
            // Create ProcessBuilder instance with the command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "whisper " + command);

            // Redirect the error stream to the standard output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get the input stream of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Read the output of the command
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();

            result = output.toString().replaceAll(timePattern, "");
            System.out.println("here");

        } catch (IOException | InterruptedException e) {
            System.out.println("error");
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return result;
        
    }
}