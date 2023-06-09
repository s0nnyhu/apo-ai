package org.shu.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.LogManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.shu.utils.Notification;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashMap;

public class Commander implements NativeKeyListener {

    static {
        Log4j.init();
    }

    private static org.apache.log4j.Logger logger = LogManager.getLogger(Commander.class.getName());
    private boolean isAltKeyPressed;
    private boolean isQKeyPressed;
    private boolean isRecording;
    private Recorder recorder;
    private ElevenlabsClient elevenlabsClient;
    private ChatGPT chatGPT;
    private Player player;


    public Commander() {
        logger.info("Commander initialized");
        this.isRecording = false;
        isAltKeyPressed = false;
        isQKeyPressed = false;

        ConnectionManager connectionManager = ConnectionManager.getInstance();
        HashMap<String, String> config = connectionManager.getConfiguration();
        recorder = new Recorder();
        elevenlabsClient = new ElevenlabsClient();
        chatGPT = new ChatGPT(config.get("apiChatGptEndpoint"), config.get("apiChatGptToken"), config.get("promptChatGpt"), config.get("modelChatGpt"));
        player = new Player();

        try {
            // Register the native key listener
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (Exception e) {
            logger.error("Failed loading global native hook: " + e);
        }
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
                logger.info("Start recording");
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
        }
        if (isAltKeyPressed && nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_Q) {
            // Perform your desired action here
            isRecording = false;
            logger.info("Stop recording");
            recorder.stopRecording();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String request = Whisper.transcribe(System.getenv("TEMP") + "\\recording.wav --model medium --language en --device cuda");

//            request = request.split("\n")[2];

            String response = chatGPT.ask(request);
            System.out.println(response);

            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray choices = jsonObject.getAsJsonArray("choices");
            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            JsonObject message = firstChoice.getAsJsonObject("message");
            String content = message.get("content").getAsString();

            //Format GPT response to be readable
//            content = content.replaceAll("\"", " ");
//            content = content.replaceAll("\\r?\\n", "");

//            elevenlabsClient.tts(content);
//            player.play("response.wav");

            try {
                Notification.displayTray(content);
            } catch (AWTException | MalformedURLException e) {
                throw new RuntimeException(e);
            }

            TTS.toAudio(content);
            player.play(System.getenv("TEMP") + "\\speech.wav");

        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
