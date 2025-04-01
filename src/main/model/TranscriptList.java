package model;

import java.util.*;
import org.json.*;
import persistence.Writable;

//Contains a list of all uploaded gene transcripts.
public class TranscriptList implements Writable {
    private ArrayList<Transcript> geneList;

    // EFFECTS: sets geneList to an empty ArrayList
    public TranscriptList() {
        geneList = new ArrayList<Transcript>();
    }

    // REQUIRES: transcriptInd < geneList.size()
    // MODIFIES: this
    // EFFECTS: removes the transcript at the specified index from the list
    public void removeTranscript(int transcriptInd) {
        geneList.remove(transcriptInd);
        EventLog.getInstance().logEvent(new Event("Removed a Transcript from list"));
    }

    // MODIFIES: this
    // EFFECTS: adds the specified transcript to the list.
    public void addTranscript(Transcript t) {
        geneList.add(t);
        EventLog.getInstance().logEvent(new Event("Added " + t.getTranscriptName() + " to list"));
    }

    // REQUIRES: tIndex < geneList.size()
    // EFFECTS: retrieves the transcript from a specific index in geneList.
    public Transcript getTranscript(int tranIndex) {
        return geneList.get(tranIndex);
    }

    // REQUIRES: transcriptName != null
    // EFFECTS: returns a list of all instances of a specific transcript, including
    // reversed, complimentary, and reverse complimentary transcripts.
    public ArrayList<Transcript> getAll(String transcriptName) {
        ArrayList<Transcript> transcriptMods = new ArrayList<Transcript>();
        for (Transcript t : geneList) {
            if (t.getTranscriptName().contains(transcriptName)) {
                transcriptMods.add(t);
            }
        }
        return transcriptMods;
    }

    public void setList(List<Transcript> list) {
        geneList = (ArrayList<Transcript>) list;
    }

    public ArrayList<Transcript> getList() {
        return geneList;
    }

    public boolean isEmpty() {
        return geneList.isEmpty();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transcripts", transcriptsToJson());
        return json;
    }

    // EFFECTS: returns transcripts in the transcriptList as a JSONArray
    private JSONArray transcriptsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Transcript t : geneList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
