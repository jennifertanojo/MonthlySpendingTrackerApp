package persistence;

import model.Log;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code influenced by the JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {

    protected void checkLog(Log log, int year, int month, String type, String category, double amount) {
        assertEquals(year, log.getYear());
        assertEquals(month, log.getMonth());
        assertEquals(type, log.getType());
        assertEquals(category, log.getCategory());
        assertEquals(amount, log.getAmount());
    }
}
