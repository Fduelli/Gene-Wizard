package persistence;

import model.*;
import org.json.JSONObject;

import java.io.*;

/***************************************************************************************
*    Title: JsonSerializationDemo source code
*    Author: Carter, P
*    Date: 10/18/24
*    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
*
***************************************************************************************/
// Represents a writer that writes JSON representation of transcript list to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    
    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of transcriptList to file
    public void write(TranscriptList tranList) {
        JSONObject json = tranList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
