package org.shu;

import org.shu.ui.Hera;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        Hera ui = new Hera();
        ui.create();

        //Sound recorder

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();

        for (Info info : mixerInfos) {
            Mixer mixer = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();

            if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                System.out.println("Microphone: " + info.getName());
            }
        }

        String classpath = System.getProperty("java.class.path");
        System.out.println("Classpath: " + classpath);


//        if (SystemTray.isSupported()) {
//            try {
//                displayTray("Hello there");
//            } catch (AWTException | MalformedURLException ex) {
//
//            }
//        } else {
//            System.err.println("System tray not supported!");
//        }
    }
}

