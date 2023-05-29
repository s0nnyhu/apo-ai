package org.shu.core;

import org.shu.ui.Hera;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PushToTalk implements KeyEventDispatcher {

    public PushToTalk() {
        System.out.println("Push-to-talk enabled. Press and hold 'Alt + Q' to speak.");
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        System.out.println(e.getKeyCode());
        System.out.println(e.isAltDown());
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Q && e.isAltDown()) {
            System.out.println("Hello");
        } else if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_Q && e.isAltDown()) {
            System.out.println("Bye");
        }
        return false;
    }

    public static void main(String[] args) {
        Hera ui = new Hera();
        ui.create();
        PushToTalk ptt = new PushToTalk();

    }
}
