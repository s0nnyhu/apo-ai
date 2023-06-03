package org.shu.core;

import org.apache.log4j.LogManager;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class Player {

    static {
        Log4j.init();
    }

    private static org.apache.log4j.Logger logger = LogManager.getLogger(Commander.class.getName());
    private final MediaPlayerFactory mediaPlayerFactory;
    private final MediaPlayer mediaPlayer;

    public Player() {
        // Create an instance of MediaPlayerFactory
        this.mediaPlayerFactory = new MediaPlayerFactory();
        // Create a MediaPlayer
        this.mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();

    }

    public void play(String audioFilePath) {
        try {
            this.mediaPlayer.media().play(audioFilePath);
        } catch (Exception e) {
            logger.error("Could not play " + audioFilePath + ": " + e);
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            // Release resources
            this.mediaPlayer.release();
            this.mediaPlayerFactory.release();
        } catch (Exception e) {
            logger.error("Could not stop audio: " + e);
            e.printStackTrace();
        }
    }

}
