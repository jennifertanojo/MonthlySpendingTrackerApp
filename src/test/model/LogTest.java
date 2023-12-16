package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    private Log testLog;

    @BeforeEach
    void runBefore() {
        testLog = new Log(2010, 1, "in", "salary", 8000.00);
    }

    @Test
    void testConstructor() {
        assertEquals(2010, testLog.getYear());
        assertEquals(1, testLog.getMonth());
        assertEquals("in", testLog.getType());
        assertEquals("salary", testLog.getCategory());
        assertEquals(8000.00, testLog.getAmount());
    }

    @Test
    void testConstructorInvalidAmount() {
        Log testLog0 = new Log(2010, 1, "in", "salary",-1);
        assertEquals(2010, testLog0.getYear());
        assertEquals(1, testLog0.getMonth());
        assertEquals("in", testLog0.getType());
        assertEquals("salary", testLog0.getCategory());
        assertEquals(0, testLog0.getAmount());
    }

    @Test
    void testToStringIncome() {
        assertTrue(testLog.toString().contains("[ year = 2010, month = 1, type = income, "
                + "category = salary, amount = $8000.00 ]"));
    }

    @Test
    void testToStringSpending() {
        testLog = new Log(2010, 1, "out", "spend", 8000.00);
        assertTrue(testLog.toString().contains("[ year = 2010, month = 1, type = spending, "
                + "category = spend, amount = $8000.00 ]"));
    }

    @Test
    void testToStringInvalid() {
        testLog = new Log(2010, 1, "invalid", "spend", 8000.00);
        assertTrue(testLog.toString().contains("[ year = 2010, month = 1, type = invalid, "
                + "category = spend, amount = $8000.00 ]"));
    }
}
