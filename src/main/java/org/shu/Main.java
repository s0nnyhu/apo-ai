package org.shu;

import org.jnativehook.GlobalScreen;
import org.shu.core.Commander;
import org.shu.ui.Apo;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.TargetDataLine;
import java.util.logging.Level;
import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        Apo ui = new Apo();
        ui.create();

        Commander c = new Commander();


//        JTextArea textArea = ui.getTextarea();
//
//        String a = "{\n" +
//                "    \"glossary\": {\n" +
//                "        \"title\": \"example glossary\",\n" +
//                "\t\t\"GlossDiv\": {\n" +
//                "            \"title\": \"S\",\n" +
//                "\t\t\t\"GlossList\": {\n" +
//                "                \"GlossEntry\": {\n" +
//                "                    \"ID\": \"SGML\",\n" +
//                "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
//                "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
//                "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
//                "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
//                "\t\t\t\t\t\"GlossDef\": {\n" +
//                "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
//                "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
//                "                    },\n" +
//                "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
//                "                }\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//        textArea.append("2021-09-01 14:30:00 INFO  MyApp:10 - Application started\n");
//        textArea.append("2021-09-01 14:30:00 INFO  MyApp:10 - Application started\n");
//        textArea.append("2021-09-01 14:30:00 INFO  MyApp:10 - Application started\n");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Object jsonObject = gson.fromJson(a, Object.class);
//        String formattedJson = gson.toJson(jsonObject);
//
//        textArea.append(formattedJson);
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

