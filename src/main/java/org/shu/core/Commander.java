package org.shu.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Commander implements NativeKeyListener {

    private boolean isAltKeyPressed;
    private boolean isQKeyPressed;
    private boolean isRecording;
    private Recorder recorder;

    private ElevenlabsClient elevenlabsClient;
    private ChatGPT chatGPT;

    Player player;

    public Commander() {
        this.isRecording = false;
        isAltKeyPressed = false;
        isQKeyPressed = false;

        recorder = new Recorder();
        elevenlabsClient = new ElevenlabsClient();
        chatGPT = new ChatGPT();
        player = new Player();

        try {
            // Register the native key listener
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (Exception e) {
            System.out.println("Failed loading global native hook: " + e);
        }

        System.out.println(isRecording);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        // Check if Alt key is pressed
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_L) {
            // Set a flag to check if Q key is pressed next
            isAltKeyPressed = true;
        }

        // Check if Q key is pressed while Alt key is already pressed
        if (isAltKeyPressed && nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_Q) {
            // Perform your desired action here
            if (!isRecording) {
                isRecording = true;
                System.out.println("start recording");
                recorder.startRecording();
            }

        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        // Check if Alt key is released
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_L) {
            // Reset the flag since Alt key is released
            isAltKeyPressed = false;
            isRecording = false;
            System.out.println("stop recording");
            recorder.stopRecording();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String request = Whisper.transcribe("recording.wav --model medium --language en --device cuda");

            TTS.toAudio("Your demand has been transcribed");
            player.play("speech.wav");
//            request = request.split("\n")[2];


            String response = chatGPT.ask(request);
            System.out.println(response);

            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray choices = jsonObject.getAsJsonArray("choices");
            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            JsonObject message = firstChoice.getAsJsonObject("message");
            String content = message.get("content").getAsString();

            TTS.toAudio("Your demand has been processed by chat GPT");
            player.play("speech.wav");

            //Format GPT response to be readable
//            content = content.replaceAll("\"", " ");
//            content = content.replaceAll("\\r?\\n", "");

//            elevenlabsClient.tts(content);
//            player.play("response.wav");

            TTS.toAudio(content);
            player.play("speech.wav");
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    public static void main(String[] args) throws InterruptedException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Commander c = new Commander();

    }
}
