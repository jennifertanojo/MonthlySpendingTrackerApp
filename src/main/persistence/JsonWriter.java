package persistence;

import model.Event;
import model.EventLog;
import model.LogCollection;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
// Code influenced by the JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private String destination;
    private PrintWriter writer;
    private static final int TAB = 4;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destinationFile) {
        this.destination = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the log collection to file
    public void write(LogCollection lc) {
        JSONObject json = lc.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved logs to file."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        this.writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
