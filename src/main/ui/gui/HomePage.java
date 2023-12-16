package ui.gui;

import model.Log;
import model.LogCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// the homepage where users can see their monthly logs
public class HomePage extends JFrame implements ActionListener, ScreenTemplate {
    private static final String JSON_STORE = "./data/monthlySpendingTracker.json";
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JFrame home;
    private JPanel panel;
    private JButton addLogButton;
    private JButton removeLogButton;
    private JButton saveButton;
    private JLabel logsStatus;
    private JLabel greetings;
    private JPanel saveData;
    private Container container;
    private LogCollection logCollection;
    private Map<Integer, List<Integer>> dates = new LinkedHashMap<>();

    // EFFECTS: runs the homepage window
    public HomePage(LogCollection logCollection, String inputButton) throws IOException {
        if (inputButton.equals("y")) {
            this.logCollection = jsonReader.read();
        } else if (inputButton.equals("n")) {
            this.logCollection = new LogCollection();
        } else {
            this.logCollection = logCollection;
        }

        home = new JFrame();
        panel = new JPanel(new GridBagLayout());
        panel.setLayout(new GridLayout(0, 1));
        container = this.getContentPane();
        container.setLayout(new GridLayout(0, 1));
        container.setBackground(Color.LIGHT_GRAY);
        printComponents(panel, container);
    }

    // MODIFIES: this
    // EFFECTS: displays the entire home page
    private void printComponents(JPanel panel, Container container) {
        JPanel welcome = new JPanel(new GridLayout(4, 1));
        welcome.setBackground(Color.LIGHT_GRAY);

        greetings = new JLabel("Hello!");
        greetings.setFont(new Font("Serif", Font.PLAIN, 32));
        text(greetings);
        welcome.add(greetings);

        logsStatus = new JLabel();
        logsStatus.setText("<html> You have: " + logCollection.getLogCollection().size() + "  logs in total"
                + "<html><br>");
        logsStatus.setFont(new Font("Serif", Font.PLAIN, 18));
        text(logsStatus);
        welcome.add(logsStatus);

        setAddLogButton(welcome);
        setRemoveLogButton(welcome);
        container.add(welcome);

        showAllMonthsAndYears();
        save();

        container.add(saveData);
        panel.add(container);
        screenTemplate(panel, home);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPanel(scrollPane, home);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button to add logs into the log collection
    private void setAddLogButton(JPanel welcome) {
        addLogButton = new JButton("Add a new log");
        addLogButton.addActionListener(this);
        welcome.add(addLogButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button to add logs into the log collection
    private void setRemoveLogButton(JPanel welcome) {
        removeLogButton = new JButton("Remove existing log");
        removeLogButton.addActionListener(this);
        welcome.add(removeLogButton);
    }

    // MODIFIES: this
    // EFFECTS: writes data into JSON file or exit based on user input
    private void save() {
        saveData = new JPanel(new GridLayout(2, 1));
        saveData.setBackground(Color.LIGHT_GRAY);

        JLabel save = new JLabel("Save data before quitting?");
        save.setFont(new Font("Serif", Font.PLAIN, 16));
        text(save);
        saveData.add(save);

        saveButton = new JButton("Save data");
        saveButton.addActionListener(this);
        saveData.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: displays the categories and items panel
    private void showAllMonthsAndYears() {
        getDifferentDates();
        showDifferentDates();
    }

    // MODIFIES: this
    // EFFECTS: obtains the logs with different dates
    private void getDifferentDates() {
        for (Log log : logCollection.getLogCollection()) {
            if (!dates.containsKey(log.getYear())) {
                List<Integer> monthList = new ArrayList<>();
                monthList.add(log.getMonth());
                dates.put(log.getYear(), monthList);
            } else if (!dates.get(log.getYear()).contains(log.getMonth())) {
                List<Integer> monthList = dates.get(log.getYear());
                monthList.add(log.getMonth());
                dates.put(log.getYear(), monthList);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the logs with different dates
    private void showDifferentDates() {
        for (int year : dates.keySet()) {
            for (int month : dates.get(year)) {
                Container allMonthsAndYears = new JPanel(new GridLayout(2, 1));
                JPanel displayLogs = new JPanel(new GridLayout(dates.keySet().size(), 1));
                header(year, month, allMonthsAndYears);
                String[] columnNames = new String[]{"Type", "Category", "Amount (in dollars)"};
                DefaultTableModel model = new DefaultTableModel();
                JTable logTable = new JTable(model);
                model.addColumn(columnNames[0]);
                model.addColumn(columnNames[1]);
                model.addColumn(columnNames[2]);
                model.addRow(columnNames);

                for (Log log : logCollection.getMonthlyLogs(year, month)) {
                    displayMonthlyLogs(log, model);
                }
                displayLogs.add(logTable);
                allMonthsAndYears.add(displayLogs);
                container.add(allMonthsAndYears);
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: displays the year and month and the add log button
    private void header(int year, int month, Container allMonthsAndYears) {
        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        JLabel dateLabel = new JLabel();
        dateLabel.setText(year + " / " + month);
        headerPanel.add(dateLabel);
        allMonthsAndYears.add(headerPanel);
    }

    // MODIFIES: this
    // EFFECTS: displays the logs in the specific year/month
    private void displayMonthlyLogs(Log log, DefaultTableModel model) {
        String logType;
        if (log.getType().equals("in")) {
            logType = "income";
        } else {
            logType = "spending";
        }
        String logCategory = log.getCategory();
        String logAmount = String.format("%.2f", log.getAmount());

        String[] rowData = {logType, logCategory, logAmount};
        model.addRow(rowData);
    }

    // EFFECTS: goes to new JFrame page based on user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addLogButton) {
            this.dispose();
            setVisible(false);
            new AddOrRemoveLog(logCollection, home, "add");
        }
        if (e.getSource() == removeLogButton) {
            this.dispose();
            setVisible(false);
            new AddOrRemoveLog(logCollection, home, "remove");
        }
        if (e.getSource() == saveButton) {
            saveActionPerformed();
        }
    }

    // EFFECTS: specifies the action performed for the save button
    public void saveActionPerformed() {
        try {
            jsonWriter.open();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        jsonWriter.write(logCollection);
        jsonWriter.close();
    }

}
