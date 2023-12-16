package persistence;

import model.Log;
import model.LogCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            LogCollection lc = new LogCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected, test passes
        }
    }

    @Test
    void testWriterEmptyLogCollection() {
        try {
            LogCollection lc = new LogCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLogCollection.json");
            writer.open();
            writer.write(lc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLogCollection.json");
            lc = reader.read();
            List<Log> logs = lc.getLogCollection();
            assertEquals(0, logs.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLogCollection() {
        try {
            LogCollection lc = new LogCollection();
            lc.addLog(new Log(2000,12,"in", "salary", 5000.00));
            lc.addLog(new Log(2000,12,"out", "rent", 1500.00));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLogCollection.json");
            writer.open();
            writer.write(lc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLogCollection.json");
            lc = reader.read();
            List<Log> logs = lc.getLogCollection();
            assertEquals(2, logs.size());
            checkLog(logs.get(0), 2000,12,"in", "salary", 5000.00);
            checkLog(logs.get(1), 2000,12,"out", "rent", 1500.00);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
