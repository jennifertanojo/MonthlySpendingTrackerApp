package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Event;
import model.EventLog;
import model.Log;
import model.LogCollection;
import org.json.*;

// Represents a reader that reads log collection from JSON data stored in file
// Code influenced by the JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads log collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LogCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded previous logs."));
        return parseLogCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses log collection from JSON object and returns it
    private LogCollection parseLogCollection(JSONObject jsonObject) {
        LogCollection lc = new LogCollection();
        addLogs(lc, jsonObject);
        return lc;
    }

    // MODIFIES: lc
    // EFFECTS: parses logs from JSON object and adds them to log collection
    private void addLogs(LogCollection lc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("logs");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addLog(lc, nextLog);
        }
    }

    // MODIFIES: lc
    // EFFECTS: parses log from JSON object and adds it to log collection
    private void addLog(LogCollection lc, JSONObject jsonObject) {
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        String type = jsonObject.getString("type");;
        String category = jsonObject.getString("category");
        double amount = jsonObject.getDouble("amount");
        Log log = new Log(year, month, type, category, amount);
        lc.addLog(log);
    }

}
