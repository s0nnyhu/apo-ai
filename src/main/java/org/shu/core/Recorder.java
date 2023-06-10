package org.shu.core;

import org.apache.log4j.LogManager;

import javax.sound.sampled.*;


public class Recorder {

    private static final org.apache.log4j.Logger logger = LogManager.getLogger(Recorder.class);
    private static final int SAMPLE_RATE = 44100; // Sample rate in Hz
    private static final int SAMPLE_SIZE_IN_BITS = 16; // Bits per sample
    private static final int CHANNELS = 1; // Mono channel
    private static final boolean SIGNED = true; // Signed samples
    private static final boolean BIG_ENDIAN = false; // Little-endian byte order
    private TargetDataLine targetDataLine;

    public void startRecording() {
        try {
            AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);

            targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
            targetDataLine.open(audioFormat);

            targetDataLine.start();

            // Create a thread to capture audio for 60 seconds
            Thread recordingThread = new Thread(() -> {
                try {
                    AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new java.io.File(System.getenv("TEMP") + "\\recording.wav"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            recordingThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        try {
            targetDataLine.stop();
            targetDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}