package org.shu.utils;

import java.awt.*;
import java.net.MalformedURLException;

public class Notification {
    public static void displayTray(String text) throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image);
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("Apo AI");
        tray.add(trayIcon);

        trayIcon.displayMessage("Apo AI", text, TrayIcon.MessageType.INFO);
        // Wait for a few seconds before removing the icon
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tray.remove(trayIcon);
    }
}
