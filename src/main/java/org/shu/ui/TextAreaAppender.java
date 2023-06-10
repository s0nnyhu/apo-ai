package org.shu.ui;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.*;

public class TextAreaAppender extends AppenderSkeleton {
    private static JTextArea textArea;
    private Layout layout;

    public TextAreaAppender() {
        super();
    }

    public static void setTextArea(JTextArea textArea) {
        TextAreaAppender.textArea = textArea;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return layout;
    }

    @Override
    public void close() {
        // Nothing to do here
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    @Override
    protected void append(LoggingEvent event) {
        if (textArea != null) {
            String message = this.layout.format(event);
            textArea.append(message);
        }
    }
}
