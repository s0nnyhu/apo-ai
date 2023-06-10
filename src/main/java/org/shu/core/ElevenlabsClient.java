package org.shu.core;

import org.shu.utils.HTTPClient;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

public class ElevenlabsClient {


    private final String API_ENDPOINT = "";
    private final String API_TOKEN = "";
    ;

    public ElevenlabsClient() {

    }

    private final Map<String, String> headers = Map.of(
            "Accept", "application/json",
            "xi-api-key", API_TOKEN
    );

    private final Map<String, String> headersAudio = Map.of(
            "Accept", "audio/mpeg",
            "Content-Type", "application/json",
            "xi-api-key", API_TOKEN
    );

    public String getModels() {
        String response = null;
        try {
            response = HTTPClient.get(String.format("%s%s", API_ENDPOINT, "v1/models"), headers);
        } catch (Exception e) {
            System.out.println("[ElevenlabsClient] Error occured: " + e);
        }
        return response;
    }

    public String getVoices() {
        String response = null;
        try {
            response = HTTPClient.get(String.format("%s%s", API_ENDPOINT, "v1/voices"), headers);
        } catch (Exception e) {
            System.out.println("[ElevenlabsClient] Error occured: " + e);
        }
        return response;
    }

    public void setVoice(String voice) {

    }

    public void setModel(String model) {

    }

    public void tts(String text) {
        try {
            String body = "{\n" +
                    "    \"text\": \"%CONTENT%\",\n" +
                    "    \"model_id\": \"eleven_multilingual_v1\",\n" +
                    "    \"voice_settings\": {\n" +
                    "        \"stability\": 0.5,\n" +
                    "        \"similarity_boost\": 0.5\n" +
                    "    }\n" +
                    "}";
            body = body.replace("%CONTENT%", text);
            InputStream responseAudioStream = HTTPClient.postAudio(String.format("%s%s", API_ENDPOINT, "v1/text-to-speech/MF3mGyEYCl7XYWbV9V6O"), headersAudio, body);
            FileOutputStream fileOutputStream = new FileOutputStream("response.wav");
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = responseAudioStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            responseAudioStream.close();
            fileOutputStream.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
