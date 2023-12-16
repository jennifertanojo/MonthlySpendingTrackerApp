package ui.console;

import java.io.FileNotFoundException;

public class Main {

    //EFFECTS: runs the console application of MonthlySpendingTrackerApp
    public static void main(String[] args) {
        try {
            new MonthlySpendingTrackerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
