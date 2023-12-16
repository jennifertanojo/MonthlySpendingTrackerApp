package ui.gui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// The standard JFrame background and size for the application
public interface ScreenTemplate {

    // EFFECTS: makes a standard JFrame for a window
    default void screenTemplate(JPanel panel, JFrame frame) {
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setTitle("Monthly Spending Tracker App");
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    String eventLog = event.toString() + "\n";
                    System.out.println(eventLog);
                }
                EventLog.getInstance().clear();
                System.exit(0);
            }
        });
        ImageIcon logo = new ImageIcon("data/logo.png");
        frame.setIconImage(logo.getImage());
        frame.add(panel);
        frame.setVisible(true);
    }

    // EFFECTS: makes the existing panel scrollable
    default void scrollPanel(JScrollPane panel, JFrame frame) {
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setTitle("Monthly Spending Tracker App");
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("data/logo.png");
        frame.setIconImage(logo.getImage());
        frame.add(panel);
        frame.setVisible(true);
    }

    // EFFECTS: makes a standard JFrame for a pop-up window
    default void popUp(JPanel panel, JFrame frame, int width, int height) {
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setTitle("Monthly Spending Tracker App");
        frame.setBackground(Color.LIGHT_GRAY);
        ImageIcon logo = new ImageIcon("data/logo.png");
        frame.setIconImage(logo.getImage());
        frame.add(panel);
        frame.setVisible(true);
    }

    // EFFECTS: standardizes input text with given font and color
    default void text(JLabel text) {
        text.setForeground(Color.DARK_GRAY);
        text.setHorizontalTextPosition(JLabel.CENTER);
        text.setVerticalTextPosition(JLabel.CENTER);
    }

}
