package org.shu.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TTS {


    public static void toAudio(String text) {
        String cleanedText = text.replace("\"", "\\\"").replace("\n", "");
        System.out.println(cleanedText);
        String command = "cmd /c tts --text \"%TEXT%\" --model_name \"tts_models/en/ljspeech/tacotron2-DDC\" --out_path speech.wav --use_cuda True";
        command = command.replace("%TEXT%", cleanedText);

        System.out.println(command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();
            String output = getOutput(process.getInputStream());
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Command failed with exit code " + exitCode);
            } else {
                System.out.println("Command executed successfully");
            }
            System.out.println("Output: " + output);
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to execute command: " + e.getMessage());
        }
    }


    private static String getOutput(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

}
