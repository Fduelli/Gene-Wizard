package persistence;

import model.*;
import java.io.*;
import org.json.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.stream.Stream;
import java.util.*;

/***************************************************************************************
 * Title: JsonSerializationDemo source code
 * Author: Carter, P
 * Date: 10/18/24
 * Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***************************************************************************************/
// Represents a reader that reads a transcript list from JSON data stored in
// file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TranscriptList from file and returns it;
    // throws IOException if an error occurs reading data
    public TranscriptList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTranscriptList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses transcriptlist from JSON object and returns it.
    private TranscriptList parseTranscriptList(JSONObject jsonObject) {
        TranscriptList tranList = new TranscriptList();
        addTranscripts(tranList, jsonObject);
        return tranList;
    }

    // MODIFIES: tranList
    // EFFECTS: Parses transcripts from JSON Object and adds them to TranscriptList.
    private void addTranscripts(TranscriptList tranList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transcripts");
        for (Object json : jsonArray) {
            JSONObject nextTranscript = (JSONObject) json;
            addTranscript(tranList, nextTranscript);
        }
    }

    // MODIFIES: tranList
    // EFFECTS: Parses a transcript from JSON Object and adds it to TranscriptList
    private void addTranscript(TranscriptList tranList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String sequence = jsonObject.getString("sequence");
        boolean isReversed = jsonObject.getBoolean("reversed");
        boolean isComplement = jsonObject.getBoolean("complementary");
        Transcript t = new Transcript();
        t.setSequence(sequence);
        t.setTranName(name);
        t.setComplementary(isComplement);
        t.setReversed(isReversed);
        addSequenceObjects(t, jsonObject);
        tranList.addTranscript(t);
    }

    // MODIFIES: t
    // EFFECTS: Parses exons and introns from JSON Object and adds it to transcript
    private void addSequenceObjects(Transcript t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exons");
        ArrayList<Exon> exons = new ArrayList<Exon>();
        ArrayList<Intron> introns = new ArrayList<Intron>();
        for (Object exon : jsonArray) {
            exons.add(addExon((JSONObject) exon));
        }
        jsonArray = jsonObject.getJSONArray("introns");
        for (Object intron : jsonArray) {
            introns.add(addIntron((JSONObject) intron));
        }
        t.setExons(exons);
        t.setIntrons(introns);
    }

    // EFFECTS: Parses an exon from JSON object and returns a new exon
    private Exon addExon(JSONObject jsonObject) {
        int exonNum = jsonObject.getInt("number");
        String exonSeq = jsonObject.getString("sequence");
        boolean isReversed = jsonObject.getBoolean("reversed");
        boolean isComplement = jsonObject.getBoolean("complementary");
        Exon temp = new Exon(exonNum, exonSeq);
        temp.setReversed(isReversed);
        temp.setComplementary(isComplement);
        return temp;
    }

    // EFFECTS: Parses an intron from JSON object and returns a new intron
    private Intron addIntron(JSONObject jsonObject) {
        int intronNum = jsonObject.getInt("number");
        String intronSeq = jsonObject.getString("sequence");
        boolean isReversed = jsonObject.getBoolean("reversed");
        boolean isComplement = jsonObject.getBoolean("complementary");
        Intron temp = new Intron(intronNum, intronSeq);
        temp.setReversed(isReversed);
        temp.setComplementary(isComplement);
        return temp;
    }

}
