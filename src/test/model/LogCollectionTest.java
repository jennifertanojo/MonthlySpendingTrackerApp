package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogCollectionTest {
    private LogCollection testLogCollection;
    private Log log1;
    private Log log2;
    private Log log3;
    private Log log4;
    private Log log5;
    private Log log6;
    private Log log7;
    private Log log8;
    private Log log9;

    @BeforeEach
    void runBefore() {
        testLogCollection = new LogCollection();
        log1 = new Log(2010, 1, "in", "salary", 8000.00);
        log2 = new Log(2010, 1, "out", "grocery", 200.00);
        log3 = new Log(2010, 1, "out", "rent", 1500.50);
        log4 = new Log(2010, 1, "in", "bonus", 100.00);
        log5 = new Log(2010, 1, "out", "grocery", 4000.00);
        log6 = new Log(2012, 12, "in", "salary", 8500.50);
        log7 = new Log(2012, 12, "out", "rent", 1600.50);
        log8 = new Log(2012, 12, "out", "holiday splurge", 2649.75);
        log9 = new Log(2012, 12, "out", "holiday splurge", 4250.25);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLogCollection.getLogCollection().size());
    }

    @Test
    void testAddLogOne() {
        testLogCollection.addLog(log1);

        List<Log> testList1 = testLogCollection.getLogCollection();
        assertEquals(1, testList1.size());
        assertEquals(log1, testList1.get(0));

        List<Log> testList2 = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(1, testList2.size());
        assertEquals(log1, testList2.get(0));
    }

    @Test
    void testAddLogMultiple() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log6);

        List<Log> testList1 = testLogCollection.getLogCollection();
        assertEquals(3, testList1.size());
        assertEquals(log1, testList1.get(0));
        assertEquals(log2, testList1.get(1));
        assertEquals(log6, testList1.get(2));

        List<Log> testList2 = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(2, testList2.size());
        assertEquals(log1, testList2.get(0));
        assertEquals(log2, testList2.get(1));

        List<Log> testList3 = testLogCollection.getMonthlyLogs(log6.getYear(), log6.getMonth());
        assertEquals(1, testList3.size());
        assertEquals(log6, testList3.get(0));
    }

    @Test
    void testRemoveLogOne() {
        testLogCollection.addLog(log1);
        assertEquals(1, testLogCollection.getLogCollection().size());
        testLogCollection.removeLog(log1);
        assertEquals(0, testLogCollection.getLogCollection().size());

        testLogCollection.addLog(log2);
        testLogCollection.addLog(log6);
        testLogCollection.removeLog(log6);
        assertEquals(1, testLogCollection.getLogCollection().size());
    }

    @Test
    void testRemoveLogNone() {
        testLogCollection.addLog(log1);
        assertEquals(1, testLogCollection.getLogCollection().size());
        testLogCollection.removeLog(log2);
        assertEquals(1, testLogCollection.getLogCollection().size());
        assertEquals(log1, testLogCollection.getLogCollection().get(0));
        testLogCollection.removeLog(log6);
        assertEquals(1, testLogCollection.getLogCollection().size());
        assertEquals(log1, testLogCollection.getLogCollection().get(0));
        log6 = new Log(2010, 12, "in", "salary", 8000.50);
        testLogCollection.removeLog(log6);
        assertEquals(1, testLogCollection.getLogCollection().size());
        assertEquals(log1, testLogCollection.getLogCollection().get(0));
        log6 = new Log(2010, 1, "in", "bonus", 8000.00);
        testLogCollection.removeLog(log6);
        assertEquals(1, testLogCollection.getLogCollection().size());
        assertEquals(log1, testLogCollection.getLogCollection().get(0));
        log6 = new Log(2010, 1, "in", "salary", 7000.00);
        testLogCollection.removeLog(log6);
        assertEquals(1, testLogCollection.getLogCollection().size());
        assertEquals(log1, testLogCollection.getLogCollection().get(0));
        log6 = new Log(2010, 1, "in", "Salary", 8000.00);
        testLogCollection.removeLog(log6);
        assertEquals(0, testLogCollection.getLogCollection().size());
    }

    @Test
    void testGetMonthlyLogsSameYearDiffMonths() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        log6 = new Log(2010, 12, "in", "salary", 8500.50);
        log7 = new Log(2010, 12, "out", "rent", 1600.50);
        testLogCollection.addLog(log6);
        testLogCollection.addLog(log7);

        List<Log> testList1 = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(2, testList1.size());
        assertEquals(log1, testList1.get(0));
        assertEquals(log2, testList1.get(1));

        List<Log> testList2 = testLogCollection.getMonthlyLogs(log6.getYear(), log6.getMonth());
        assertEquals(2, testList2.size());
        assertEquals(log6, testList2.get(0));
        assertEquals(log7, testList2.get(1));
    }

    @Test
    void testGetMonthlyBalanceEmpty() {
        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(0, testList.size());

        assertEquals(0.00, testLogCollection.getMonthlyBalance(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlyBalanceOnlyIncome() {
        testLogCollection.addLog(log1);

        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(1, testList.size());
        assertEquals(log1, testList.get(0));

        assertEquals(8000.00, testLogCollection.getMonthlyBalance(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlyBalanceOnlySpend() {
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);

        List<Log> testList = testLogCollection.getMonthlyLogs(log2.getYear(), log2.getMonth());
        assertEquals(2, testList.size());
        assertEquals(log2, testList.get(0));
        assertEquals(log3, testList.get(1));

        assertEquals(-1700.50, testLogCollection.getMonthlyBalance(log2.getYear(), log2.getMonth()));
    }

    @Test
    void testGetMonthlyBalanceMultiple() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);

        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(3, testList.size());
        assertEquals(log1, testList.get(0));
        assertEquals(log2, testList.get(1));
        assertEquals(log3, testList.get(2));

        assertEquals(6299.50, testLogCollection.getMonthlyBalance(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlyIncomeEmpty() {
        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(0, testList.size());

        assertEquals(0.00, testLogCollection.getMonthlyIncome(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlyIncomeOne() {
        testLogCollection.addLog(log1);

        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(1, testList.size());
        assertEquals(log1, testList.get(0));

        assertEquals(8000.00, testLogCollection.getMonthlyIncome(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlyIncomeMultiple() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log4);

        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(2, testList.size());
        assertEquals(log1, testList.get(0));
        assertEquals(log4, testList.get(1));

        assertEquals(8100.00, testLogCollection.getMonthlyIncome(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlySpendingEmpty() {
        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(0, testList.size());

        assertEquals(0.00, testLogCollection.getMonthlySpending(log1.getYear(), log1.getMonth()));
    }

    @Test
    void testGetMonthlySpendingOne() {
        testLogCollection.addLog(log2);

        List<Log> testList = testLogCollection.getMonthlyLogs(log2.getYear(), log2.getMonth());
        assertEquals(1, testList.size());
        assertEquals(log2, testList.get(0));

        assertEquals(200.00, testLogCollection.getMonthlySpending(log2.getYear(), log2.getMonth()));
    }

    @Test
    void testGetMonthlySpendingMultiple() {
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);

        List<Log> testList = testLogCollection.getMonthlyLogs(log2.getYear(), log2.getMonth());
        assertEquals(2, testList.size());
        assertEquals(log2, testList.get(0));
        assertEquals(log3, testList.get(1));

        assertEquals(1700.50, testLogCollection.getMonthlySpending(log2.getYear(), log2.getMonth()));
    }

    @Test
    void testToMonthlyStringEmpty() {
        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(0, testList.size());

        assertTrue(testLogCollection.toMonthlyString(2010,1).contains("This month currently has no logs."));
    }

    @Test
    void testToMonthlyString() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);

        assertTrue(testLogCollection.toMonthlyString(2010,1).contains("[ year = 2010, month = 1, " +
                "type = income, category = salary, amount = $8000.00 ]"));
        assertTrue(testLogCollection.toMonthlyString(2010,1).contains("[ year = 2010, month = 1, " +
                "type = spending, category = grocery, amount = $200.00 ]"));
        assertTrue(testLogCollection.toMonthlyString(2010,1).contains("[ year = 2010, month = 1, " +
                "type = spending, category = rent, amount = $1500.50 ]"));
    }

    @Test
    void testMonthlySummaryEmpty() {
        List<Log> testList = testLogCollection.getMonthlyLogs(log1.getYear(), log1.getMonth());
        assertEquals(0, testList.size());

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $0.00, spending = $0.00, balance = $0.00 ]"));
    }

    @Test
    void testMonthlySummaryNoSpending() {
        testLogCollection.addLog(log1);

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $8000.00, spending = $0.00, balance = $8000.00 ]"));
    }

    @Test
    void testMonthlySummaryNoIncome() {
        testLogCollection.addLog(log2);

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $0.00, spending = $200.00, balance = $-200.00 ]"));
    }

    @Test
    void testMonthlySummaryMoreThanHalf() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);
        testLogCollection.addLog(log5);

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $8000.00, spending = $5700.50, balance = $2299.50, percentage of spending = 71.26% ]\n"
                + "You have spent more than half of your income this month!"));
    }

    @Test
    void testMonthlySummaryLessThanHalf() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $8000.00, spending = $1700.50, balance = $6299.50, percentage of spending = 21.26% ]\n"
                + "You have spent less than half of your income this month!"));
    }

    @Test
    void testMonthlySummaryHalf() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log5);

        assertTrue(testLogCollection.monthlySummary(2010, 1).contains("[ year = 2010, month = 1, "
                + "income = $8000.00, spending = $4000.00, balance = $4000.00, percentage of spending = 50.00% ]\n"
                + "You have spent exactly half of your income this month!"));
    }

    @Test
    void testCompareMonthsMoreThan() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);
        testLogCollection.addLog(log6);
        testLogCollection.addLog(log7);
        testLogCollection.addLog(log8);
        testLogCollection.addLog(log5);

        assertTrue(testLogCollection.compareMonths(2010,1, 2012, 12)
                .contains("You spent more money in month 1, 2010 compared to"
                        + " month 12, 2012 relative to the income in each month."));
    }

    @Test
    void testCompareMonthsLessThan() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log2);
        testLogCollection.addLog(log3);
        testLogCollection.addLog(log6);
        testLogCollection.addLog(log7);
        testLogCollection.addLog(log8);

        assertTrue(testLogCollection.compareMonths(2010, 1, 2012, 12)
                .contains("You spent less money in month 1, 2010 compared to"
                + " month 12, 2012 relative to the income in each month."));
    }

    @Test
    void testCompareMonthsEqual() {
        testLogCollection.addLog(log1);
        testLogCollection.addLog(log6);
        testLogCollection.addLog(log5);
        testLogCollection.addLog(log9);

        assertTrue(testLogCollection.compareMonths(2010, 1,2012, 12)
                .contains("You spent the same amount money in month 1, 2010 as in"
                + " month 12, 2012 relative to the income in each month."));
    }

    @Test
    void testCompareMonthsEmptyAll() {
        assertTrue(testLogCollection.compareMonths(2010,1, 2012, 12)
                .contains("Cannot compare logs: unavailable logs"));
    }

    @Test
    void testCompareMonthsEmptyOne() {
        testLogCollection.addLog(log1);

        assertTrue(testLogCollection.compareMonths(2010,1, 2012, 12)
                .contains("Cannot compare logs: unavailable logs"));
    }

}