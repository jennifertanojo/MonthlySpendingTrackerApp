package ui.console;

import model.Log;
import model.LogCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Monthly spending tracker application
// Code influenced by the TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Code influenced by the JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class MonthlySpendingTrackerApp {
    private static final String JSON_STORE = "./data/monthlySpendingTracker.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private LogCollection logCollection;
    private Log logIncome;
    private Log logSpending;
    private Scanner input;
    private static String INCOME = "in";
    private static String SPENDING = "out";

    // EFFECTS: runs the monthly spending tracker application
    public MonthlySpendingTrackerApp() throws FileNotFoundException {
        runMonthlySpendingTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMonthlySpendingTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("l")) {
            typeLog();
        } else if (command.equals("h")) {
            spendingHistory();
        } else if (command.equals("f")) {
            spendingSummary();
        } else if (command.equals("c")) {
            compare();
        } else if (command.equals("st")) {
            saveLogCollection();
        } else if (command.equals("lt")) {
            loadLogCollection();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes logs and the list of logs
    private void init() {
        logCollection = new LogCollection();
        logIncome = new Log(0, 0, INCOME, "", 0.00);
        logSpending = new Log(0, 0, SPENDING, "", 0.00);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tl -> log income or spending");
        System.out.println("\th -> view spending history of a month");
        System.out.println("\tf -> view financial summary of a month");
        System.out.println("\tc -> compare spending between 2 months");
        System.out.println("\tst -> save work room to file");
        System.out.println("\tlt -> load work room from file");
        System.out.println("\tq -> quit");
        System.out.println("Enter selection: ");
    }

    // MODIFIES: this
    // EFFECTS: prints out the spending history of a month
    private void spendingHistory() {
        System.out.print("Enter the year: ");
        int year = input.nextInt();
        System.out.print("Enter the month: ");
        int month = input.nextInt();

        if (year >= 0 || month >= 0) {
            System.out.println(logCollection.toMonthlyString(year, month));
        } else {
            System.out.println("Cannot process negative amount...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out the financial summary of a month
    private void spendingSummary() {
        System.out.print("Enter the year: ");
        int year = input.nextInt();
        System.out.print("Enter the month: ");
        int month = input.nextInt();

        if (year < 0 || month < 0) {
            System.out.println("Cannot process negative amount...\n");
        } else {
            System.out.println(logCollection.monthlySummary(year, month));
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out the spending comparison between two specified
    //          months relative to the income within each month
    private void compare() {
        System.out.print("Enter the 1st year: ");
        int year1 = input.nextInt();
        System.out.print("Enter the 1st month: ");
        int month1 = input.nextInt();
        System.out.print("Enter the 2nd year: ");
        int year2 = input.nextInt();
        System.out.print("Enter the 2nd month: ");
        int month2 = input.nextInt();

        if (year1 < 0 || month1 < 0 || year2 < 0 || month2 < 0) {
            System.out.println("Cannot transfer negative amount...\n");
        } else {
            System.out.println(logCollection.compareMonths(year1, month1, year2, month2));
        }
    }

    // EFFECTS: prompts user to select income or spending and logs it
    private void typeLog() {
        String selection = "";  // force entry into loop
        while (!(selection.equals("i") || selection.equals("s"))) {
            System.out.println("i to enter income");
            System.out.println("s to enter spending");
            selection = input.next().toLowerCase();
        }
        if (selection.equals("i")) {
            enterIncome();
        } else {
            enterSpending();
        }
    }

    //EFFECTS: prompts user to input a log of their income
    private void enterIncome() {
        String prompt = "n";
        while (prompt.equals("n")) {
            System.out.print("Enter the year: ");
            int year = input.nextInt();
            System.out.print("Enter the month: ");
            int month = input.nextInt();
            System.out.print("Enter category of income: ");
            String category = input.next();
            System.out.print("Enter amount of income: $ ");
            double amount = input.nextDouble();
            logIncome = new Log(year, month, INCOME, category, amount);
            System.out.println(logIncome.toString());
            System.out.print("Is this correct? (y/n): ");
            prompt = input.next();
        }
        logCollection.addLog(logIncome);
    }

    //EFFECTS: prompts user to input a log of their income
    private void enterSpending() {
        String prompt = "n";
        while (prompt.equals("n")) {
            System.out.print("Enter the year: ");
            int year = input.nextInt();
            System.out.print("Enter the month: ");
            int month = input.nextInt();
            System.out.print("Enter category of spending: ");
            String category = input.next();
            System.out.print("Enter amount of spending: $ ");
            double amount = input.nextDouble();
            logSpending = new Log(year, month, SPENDING, category, amount);
            System.out.println(logSpending.toString());
            System.out.print("Is this correct? (y/n): ");
            prompt = input.next();
        }
        logCollection.addLog(logSpending);
    }

    // EFFECTS: saves the log collection to file
    private void saveLogCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(logCollection);
            jsonWriter.close();
            System.out.println("Saved this session's logs to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads log collection from file
    private void loadLogCollection() {
        try {
            logCollection = jsonReader.read();
            System.out.println("Loaded logs from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
