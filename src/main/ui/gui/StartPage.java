package ui.gui;

import model.LogCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Monthly Spending Tracker application start page
public class StartPage extends JFrame implements ActionListener, ScreenTemplate {
    private JFrame startPage;
    private ImageIcon logo;
    private Image splashLogo;
    private JLabel welcome;
    private JButton yesButton;
    private JButton noButton;
    private LogCollection logCollection = new LogCollection();

    // MODIFIES: this
    // EFFECTS: runs the first window
    public StartPage() {
        printWelcome();
        displayButton();

        JPanel screen = new JPanel();
        screen.add(welcome);
        screen.add(yesButton);
        screen.add(noButton);
        screen.setBackground(Color.LIGHT_GRAY);
        screen.setAlignmentX(Component.CENTER_ALIGNMENT);
        screen.setAlignmentY(Component.CENTER_ALIGNMENT);

        startPage = new JFrame();
        screenTemplate(screen, startPage);
    }

    // MODIFIES: this
    // EFFECTS: Displays the welcome page
    private void printWelcome() {
        logo = new ImageIcon("data/logo.png");
        splashLogo = logo.getImage().getScaledInstance(380, 380, Image.SCALE_DEFAULT);
        ImageIcon homeLogo = new ImageIcon(splashLogo);
        welcome = new JLabel("<html> Welcome! Already have previous logs?",
                homeLogo, JLabel.CENTER);
        welcome.setFont(new Font("Serif", Font.PLAIN, 20));
        text(welcome);
        welcome.setVerticalTextPosition(JLabel.BOTTOM);
        welcome.setHorizontalTextPosition(JLabel.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Display options as buttons to load saved data
    private void displayButton() {
        yesButton = new JButton("<html> Yes, load data");
        yesButton.setBounds(280, 400, 100, 80);
        yesButton.addActionListener(this);

        noButton = new JButton("<html> No, create new logs");
        noButton.setBounds(280, 400, 100, 80);
        noButton.addActionListener(this);
    }

    // EFFECTS: Goes to next JFrame page when button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            startPage.dispose();
            try {
                new HomePage(logCollection,"y");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == noButton) {
            startPage.dispose();
            try {
                new HomePage(logCollection,"n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
