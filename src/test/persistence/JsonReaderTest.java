package persistence;

import model.Log;
import model.LogCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            LogCollection lc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected, test passes
        }
    }

    @Test
    void testReaderEmptyLogCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLogCollection.json");
        try {
            LogCollection lc = reader.read();
            List<Log> logs = lc.getLogCollection();
            assertEquals(0, logs.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLogCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLogCollection.json");
        try {
            LogCollection lc = reader.read();
            List<Log> logs = lc.getLogCollection();
            assertEquals(2, logs.size());
            checkLog(logs.get(0), 2000,12,"in", "salary", 5000.00);
            checkLog(logs.get(1), 2000,12,"out", "rent", 1500.00);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
