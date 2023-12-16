package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a record of expenditure or revenue having a month, type (income or spending),
// category, and balance (in dollars).
public class Log implements Writable {
    private int month;
    private int year;
    private String type;
    private String category;
    private double amount;
    private static String INCOME = "in";
    private static String SPENDING = "out";

    // REQUIRES: 1 <= month <= 12 (January to december)
    // MODIFIES: this
    // EFFECTS: sets the year to yearNum, month to monthNum (the integer that corresponds to each month in a year),
    // and enters the log into the corresponding month; sets the type to typeString (either INCOME or SPENDING);
    // sets category to the chosen type of income/spending; if amount of income/spending is >= 0, then amount
    // spent is set to the amountDollars otherwise the amount is set to zero.
    public Log(int yearNum, int monthNum, String typeString, String categoryString, double amountDollars) {
        this.year = yearNum;
        this.month = monthNum;
        this.type = typeString;
        this.category = categoryString;
        if (amountDollars >= 0) {
            this.amount = amountDollars;
        } else {
            this.amount = 0;
        }
    }

    // EFFECTS: returns the year of the log
    public int getYear() {
        return this.year;
    }

    // EFFECTS: returns the month of the log
    public int getMonth() {
        return this.month;
    }

    // EFFECTS: returns the type of the log
    public String getType() {
        return this.type;
    }

    // EFFECTS: returns the category of the log
    public String getCategory() {
        return this.category;
    }

    // EFFECTS: returns the amount of money in the log
    public double getAmount() {
        return this.amount;
    }

    // EFFECTS: returns a string representation of the log.
    public String toString() {
        String amountStr = String.format("%.2f", this.amount);  // get balance to 2 decimal places as a string
        if (this.type.equals(INCOME)) {
            return "[ year = " + this.year + ", month = " + this.month + ", type = income, category = "
                    + this.category + ", amount = $" + amountStr + " ]";
        } else if (this.type.equals(SPENDING)) {
            return "[ year = " + this.year + ", month = " + this.month + ", type = spending, category = "
                    + this.category + ", amount = $" + amountStr + " ]";
        }
        return "[ year = " + this.year + ", month = " + this.month + ", type = " + this.type + ", category = "
                + this.category + ", amount = $" + amountStr + " ]";
    }

    // EFFECTS: returns this log as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("year", this.year);
        json.put("month", this.month);
        json.put("type", this.type);
        json.put("category", this.category);
        json.put("amount", this.amount);
        return json;
    }
}
