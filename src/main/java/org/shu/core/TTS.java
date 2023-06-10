package org.shu.core;

import org.apache.log4j.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TTS {

    static {
        Log4j.init();
    }

    private static org.apache.log4j.Logger logger = LogManager.getLogger(Commander.class.getName());

    public static void toAudio(String text) {
        String cleanedText = text.replace("\"", "\\\"").replace("\n", "");
        String command = "cmd /c tts --text \"%TEXT%\" --model_name \"tts_models/en/ljspeech/tacotron2-DDC\" --out_path " + System.getenv("TEMP") + "\\speech.wav --use_cuda True";

        command = command.replace("%TEXT%", cleanedText);

        logger.info(command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();
            String output = getOutput(process.getInputStream());
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.error("Command failed with exit code " + exitCode);
            } else {
                logger.error("Command executed successfully");
            }
            logger.error("Output: " + output);
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to execute command: " + e.getMessage());
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
