package org.shu.ui;


import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Apo {
    private static final Logger logger = Logger.getLogger(Apo.class);
    private JFrame frame;
    private JTextArea textArea;
    private JButton editButton;
    private Configuration config;
    private SystemTray systemTray;
    private TrayIcon trayIcon;

    public void create() {

        // Create the main frame
        frame = new JFrame("Apo AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

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

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the file menu
        JMenu fileMenu = new JMenu("File");

        // Create the exit menu item
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        // Add the file menu to the menu bar
        menuBar.add(fileMenu);

        // Create the help menu
        JMenu helpMenu = new JMenu("Help");

        // Create the about menu item
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Apo AI v1.0", "A vocal ChatGPT (only english for now). Its uses Whisper + ChatGPT + TTS tocatron2 python script", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);

        // Add the help menu to the menu bar
        menuBar.add(helpMenu);

        // Set the menu bar for the frame
        frame.setJMenuBar(menuBar);

        // Create the text area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to the scroll pane
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        TextAreaAppender.setTextArea(textArea);
        TextAreaAppender appender = new TextAreaAppender();
        appender.setLayout(new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n"));
        logger.addAppender(appender);

        // Create the edit button
        editButton = new JButton("Edit Parameters");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEditUI();
            }
        });
        frame.getContentPane().add(editButton, BorderLayout.SOUTH);
        // Create the configuration
        config = new Configuration();

        // Show the frame
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                minimizeToTray();
            }
        });
    }


    private void openEditUI() {
        config.openUI();
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
