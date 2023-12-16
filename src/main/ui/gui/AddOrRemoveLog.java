package ui.gui;

import model.Log;
import model.LogCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Pop-up window that allows user interactions to add new logs
public class AddOrRemoveLog extends JFrame implements ActionListener, ScreenTemplate {
    private LogCollection logCollection;
    private JFrame home;
    private JPanel panel;
    private JTextField categoryBox;
    private JSpinner amount;
    private JSpinner month;
    private JSpinner year;
    private JRadioButton typeIncomeButton;
    private JRadioButton typeSpendingButton;
    private ButtonGroup typeButtons;
    private JButton doneButton;
    private JButton deleteButton;
    private SpinnerNumberModel spinnerModelAmount;
    private SpinnerNumberModel spinnerModelMonth;
    private SpinnerNumberModel spinnerModelYear;

    // REQUIRES: previous home page, valid log index
    // EFFECTS: initialize a new area to add new logs
    public AddOrRemoveLog(LogCollection logCollection, JFrame home, String input) {
        this.logCollection = logCollection;
        this.home = home;
        this.panel = new JPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(16, 1));
        container.setBackground(Color.LIGHT_GRAY);

        if (input.equals("remove")) {
            printComponents(container, "remove");
        } else if (input.equals("add")) {
            printComponents(container, "add");
        } else {
            printComponents(container, input);
        }

        popUp(panel, this, 400, 600);
    }

    // MODIFIES: this
    // EFFECTS: displays window for user input
    private void printComponents(Container container, String input) {
        enterDetails(container);
        enterDate(container);

        if (input.equals("remove")) {
            deleteButton = new JButton("Delete log");
            deleteButton.addActionListener(this);
            container.add(deleteButton);
        } else {
            doneButton = new JButton("Done");
            doneButton.addActionListener(this);
            container.add(doneButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to input the log details
    private void enterDetails(Container container) {
        JLabel enterType = new JLabel("Log Type (income or spending): ");
        enterType.setHorizontalTextPosition(JLabel.CENTER);
        enterType.setFont(new Font("Serif", Font.PLAIN, 14));
        text(enterType);
        container.add(enterType);
        logTypeButtons(container);

        JLabel enterCategory = new JLabel("Category: ");
        enterCategory.setHorizontalTextPosition(JLabel.CENTER);
        enterCategory.setFont(new Font("Serif", Font.PLAIN, 14));
        text(enterCategory);
        container.add(enterCategory);
        categoryBox = new JTextField("Description of the log");
        container.add(categoryBox);

        JLabel enterAmount = new JLabel("Amount (in dollars): ");
        enterAmount.setHorizontalTextPosition(JLabel.CENTER);
        enterAmount.setFont(new Font("Serif", Font.PLAIN, 14));
        text(enterAmount);
        container.add(enterAmount);
        spinnerModelAmount = new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01);
        amount = new JSpinner(spinnerModelAmount);
        container.add(amount);
        container.add(new JLabel());
    }

    // MODIFIES: this
    // EFFECTS: displays radio buttons for the log types
    private void logTypeButtons(Container container) {
        typeIncomeButton = new JRadioButton("Income");
        typeIncomeButton.setBounds(120, 30,50,50);
        typeIncomeButton.addActionListener(this);
        container.add(typeIncomeButton);

        typeSpendingButton = new JRadioButton("Spending");
        typeSpendingButton.setBounds(120, 30,50,50);
        typeSpendingButton.addActionListener(this);
        container.add(typeSpendingButton);

        typeButtons = new ButtonGroup();
        typeButtons.add(typeIncomeButton);
        typeButtons.add(typeSpendingButton);
    }

    // MODIFIES: this
    // EFFECTS: allows user to input the log dates
    private void enterDate(Container container) {
        JLabel enterMonth = new JLabel("Month: ");
        enterMonth.setFont(new Font("Serif", Font.PLAIN, 14));
        enterMonth.setHorizontalTextPosition(JLabel.CENTER);
        text(enterMonth);
        container.add(enterMonth);

        spinnerModelMonth = new SpinnerNumberModel(1, 1, 12, 1);
        month = new JSpinner(spinnerModelMonth);
        month.setAlignmentX(JSpinner.CENTER_ALIGNMENT);
        container.add(month);
        container.add(new JLabel());

        JLabel enterYear = new JLabel("Year: ");
        enterYear.setFont(new Font("Serif", Font.PLAIN, 14));
        enterYear.setHorizontalTextPosition(JLabel.CENTER);
        text(enterYear);
        container.add(enterYear);

        spinnerModelYear = new SpinnerNumberModel(2000, 1, Integer.MAX_VALUE, 1);
        year = new JSpinner(spinnerModelYear);
        year.setAlignmentX(JSpinner.CENTER_ALIGNMENT);
        container.add(year);
        container.add(new JLabel());
    }

    // EFFECTS: runs new JFrame windows based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doneButton) {
            try {
                addNew();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                new HomePage(logCollection, "else");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == deleteButton) {
            actionDeleteButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: runs the JFrame windows for the delete button
    private void actionDeleteButton() {
        try {
            removeExisting();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            new HomePage(logCollection, "else");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new log and adds it to the log collection
    private void addNew() throws IOException {
        int logYear = (int) year.getValue();
        int logMonth = (int) month.getValue();
        String logCategory = categoryBox.getText();
        double logAmount = (double) amount.getValue();

        Log log;
        if (typeIncomeButton.isSelected()) {
            log = new Log(logYear, logMonth, "in", logCategory, logAmount);
        } else {
            log = new Log(logYear, logMonth, "out", logCategory, logAmount);
        }
        logCollection.addLog(log);

        this.dispose();
        home.dispose();
    }

    // MODIFIES: this
    // EFFECTS: displays window when user wants to remove logs
    private void removeExisting() throws IOException {
        int logYear = (int) year.getValue();
        int logMonth = (int) month.getValue();
        String logCategory = categoryBox.getText();
        double logAmount = (double) amount.getValue();

        Log log;
        if (typeIncomeButton.isSelected()) {
            log = new Log(logYear, logMonth, "in", logCategory, logAmount);
        } else {
            log = new Log(logYear, logMonth, "out", logCategory, logAmount);
        }
        logCollection.removeLog(log);

        this.dispose();
        home.dispose();
    }
}
