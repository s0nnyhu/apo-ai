package org.shu.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hera {
    private JFrame frame;
    private JButton button;

    private SystemTray systemTray;
    private TrayIcon trayIcon;
    public void create() {
        frame = new JFrame("Simple GUI Application");

        // Create a system tray icon
        if (SystemTray.isSupported()) {
            systemTray = SystemTray.getSystemTray();
            Image trayImage = Toolkit.getDefaultToolkit().getImage("path/to/trayIcon.png"); // Replace with your tray icon image path
            ActionListener trayActionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("open")) {
                        frame.setVisible(true);
                        systemTray.remove(trayIcon);
                    } else if (e.getActionCommand().equals("exit")) {
                        System.exit(0);
                    }
                }
            };
            PopupMenu trayPopupMenu = new PopupMenu();
            MenuItem openMenuItem = new MenuItem("Open");
            openMenuItem.setActionCommand("open");
            openMenuItem.addActionListener(trayActionListener);
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.setActionCommand("exit");
            exitMenuItem.addActionListener(trayActionListener);
            trayPopupMenu.add(openMenuItem);
            trayPopupMenu.add(exitMenuItem);
            trayIcon = new TrayIcon(trayImage, "Simple GUI Application", trayPopupMenu);
            trayIcon.setImageAutoSize(true);
        }

        button = new JButton("Click me!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button clicked!");
            }
        });

        frame.getContentPane().add(button, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                minimizeToTray();
            }
        });
        frame.setVisible(true);
    }

    private void minimizeToTray() {
        if (SystemTray.isSupported()) {
            try {
                systemTray.add(trayIcon);
                frame.setVisible(false);
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }
}
