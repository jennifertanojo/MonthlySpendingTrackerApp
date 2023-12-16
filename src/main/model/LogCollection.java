package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of logs.
public class LogCollection implements Writable {
    private List<Log> logCollection;
    private static String INCOME = "in";
    private static String SPENDING = "out";

    public LogCollection() {
        this.logCollection = new ArrayList<>();
    }

    // REQUIRES: log is not Null.
    // MODIFIES: this
    // EFFECTS: adds/records the log into the list
    public void addLog(Log log) {
        logCollection.add(log);
        EventLog.getInstance().logEvent(new Event("Log: " + log.toString() + " was added."));
    }

    // REQUIRES: log is not Null.
    // MODIFIES: this
    // EFFECTS: removes the log from the list
    public void removeLog(Log log) {
        logCollection.removeIf(l -> l.getYear() == log.getYear() && l.getMonth() == log.getMonth()
                && l.getType().equalsIgnoreCase(log.getType())
                && l.getCategory().equalsIgnoreCase(log.getCategory())
                && l.getAmount() == log.getAmount());
        EventLog.getInstance().logEvent(new Event("Log: " + log.toString() + " was removed."));
    }

    // EFFECTS: returns a list of all the logs in the list
    public List<Log> getLogCollection() {
        return this.logCollection;
    }

    // EFFECTS: returns a list of the logs with the specified year
    public List<Log> getYearlyLogs(int year) {
        List<Log> yearlyLog = new ArrayList<>();
        for (Log log : this.logCollection) {
            if (log.getYear() == year) {
                yearlyLog.add(log);
            }
        }
        return yearlyLog;
    }

    // EFFECTS: returns a list of the logs with the specified year and month
    public List<Log> getMonthlyLogs(int year, int month) {
        List<Log> yearlyLog = getYearlyLogs(year);
        List<Log> monthlyLog = new ArrayList<>();
        for (Log log : yearlyLog) {
            if (log.getMonth() == month) {
                monthlyLog.add(log);
            }
        }
        EventLog.getInstance().logEvent(new Event("Displayed logs in (YYYY/MM): " + year + " / " + month));
        return monthlyLog;
    }

    // MODIFIES: balance
    // EFFECTS: returns the balance (in dollars) for the intended year and month
    public double getMonthlyBalance(int year, int month) {
        double balance = 0.00;
        if (!getMonthlyLogs(year, month).isEmpty()) {
            for (Log log : getMonthlyLogs(year, month)) {
                if (log.getType().equals(INCOME)) {
                    balance += log.getAmount();
                }
                if (log.getType().equals(SPENDING)) {
                    balance -= log.getAmount();
                }
            }
        }
        return balance;
    }

    // MODIFIES: income
    // EFFECTS: returns the income (in dollars) for the intended year and month
    public double getMonthlyIncome(int year, int month) {
        double income = 0.00;
        if (!getMonthlyLogs(year, month).isEmpty()) {
            for (Log log : getMonthlyLogs(year, month)) {
                if (log.getType().equals(INCOME)) {
                    income += log.getAmount();
                }
            }
        }
        return income;
    }

    // MODIFIES: spending
    // EFFECTS: returns the spending (in dollars) for the intended year and month
    public double getMonthlySpending(int year, int month) {
        double spending = 0.00;
        if (!getMonthlyLogs(year, month).isEmpty()) {
            for (Log log : getMonthlyLogs(year, month)) {
                if (log.getType().equals(SPENDING)) {
                    spending += log.getAmount();
                }
            }
        }
        return spending;
    }

    // MODIFIES: monthReport
    // EFFECTS: returns a string representation of the logs in the
    //          specified year and month
    public String toMonthlyString(int year, int month) {
        String monthReport = "";
        if (!getMonthlyLogs(year, month).isEmpty()) {
            for (Log log : getMonthlyLogs(year, month)) {
                monthReport += log.toString() + "\n";
            }
        } else {
            monthReport += "This month currently has no logs.";
        }
        return monthReport;
    }

    // EFFECTS: returns a string representation of the spending summary in a month;
    //          returns the total income, total spending, ending balance, and
    //          the percentage of income spent in the month.
    public String monthlySummary(int year, int month) {
        String incomeStr = String.format("%.2f", getMonthlyIncome(year, month));
        String spendingStr = String.format("%.2f", getMonthlySpending(year, month));
        String balanceStr = String.format("%.2f", getMonthlyBalance(year, month));
        String standardReport = "[ year = " + year + ", month = " + month + ", income = $" + incomeStr
                + ", spending = $" + spendingStr + ", balance = $" + balanceStr;

        if (getMonthlySpending(year, month) != 0 && getMonthlyIncome(year, month) != 0) {
            double percentageSpending = (getMonthlySpending(year, month) / getMonthlyIncome(year, month)) * 100;
            String percentage = String.format("%.2f", percentageSpending);
            standardReport += ", percentage of spending = " + percentage + "% ]\n";

            if (percentageSpending > 50) {
                return standardReport + "You have spent more than half of your income this month!";
            } else if (percentageSpending < 50) {
                return standardReport + "You have spent less than half of your income this month!";
            } else {
                return standardReport + "You have spent exactly half of your income this month!";
            }
        } else {
            return standardReport + " ]";
        }
    }

    // EFFECTS: returns a string comparing the total spending between two months relative to the income.
    public String compareMonths(int year1, int month1, int year2, int month2) {
        if (getMonthlyLogs(year1, month1).size() != 0 && getMonthlyLogs(year2, month2).size() != 0) {
            double percentageSpending1 = (getMonthlySpending(year1, month1) / getMonthlyIncome(year1, month1)) * 100;
            double percentageSpending2 = (getMonthlySpending(year2, month2) / getMonthlyIncome(year2, month2)) * 100;
            if (percentageSpending1 < percentageSpending2) {
                return "You spent less money in month " + month1 + ", " + year1 + " compared to month " + month2
                        + ", " + year2 + " relative to the income in each month.";
            } else if (percentageSpending1 > percentageSpending2) {
                return "You spent more money in month " + month1 + ", " + year1 + " compared to month " + month2
                        + ", " + year2 + " relative to the income in each month.";
            } else {
                return "You spent the same amount money in month " + month1 + ", " + year1 + " as in month " + month2
                        + ", " + year2 + " relative to the income in each month.";
            }
        } else {
            return "Cannot compare logs: unavailable logs";
        }

    }

    // EFFECTS: returns this log collection as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("logs", logsToJson());
        return json;
    }


    // EFFECTS: returns things in this log collection as a JSON array
    private JSONArray logsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Log log : this.logCollection) {
            jsonArray.put(log.toJson());
        }

        return jsonArray;
    }

}