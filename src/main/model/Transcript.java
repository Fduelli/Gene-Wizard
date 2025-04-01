package model;

import java.util.*;
import java.io.*;
import org.json.*;

//This class represents a single gene transcript, containing the genomic sequence, exons and introns
//and modifies the various attributes including reversal and complementarity.
public class Transcript extends ModifiableSequence {
    private ArrayList<Exon> exons; // Contains the list of exons belonging to the transcript;
    private ArrayList<Intron> introns; // Contains the list of exons belonging to the transcript.
    private String transcriptName;

    // MODIFIES: this
    // EFFECTS: Sets the list of exons to exons;
    // sets the list of introns to introns;
    // constructs a sequence in the super class with a sequence of genomicSequence;
    // sets the name of the transcript to transcriptName.
    public Transcript(String transcriptName, String genomicSequence, ArrayList<Exon> exons, ArrayList<Intron> introns) {
        super(genomicSequence);
        this.transcriptName = transcriptName;
        this.exons = exons;
        this.introns = introns;
    }

    // MODIFIES: this
    // EFFECTS: Sets the list of exons to an empty ArrayList;
    // sets the list of introns to an empty ArrayList;
    // constructs a sequence in the super class with an empty sequence;
    // sets the name of the transcript to and empty string.
    public Transcript() {
        this("", "", new ArrayList<Exon>(), new ArrayList<Intron>());
    }

    // REQUIRES: exInd >= 0
    // MODIFIES: this, exons.get(exInd)
    // EFFECTS: makes the sequence of the exon in Exons with the index exInd its
    // complement,
    // returns true if the exon was made complementary and false if not.
    public boolean complementExon(int exInd) {
        if (exons.get(exInd).isComplement) {
            return false;
        }
        exons.get(exInd).complementSeq();
        EventLog.getInstance().logEvent(new Event(
                "Exon" + exons.get(exInd).getExonNum() + "of transcript " + transcriptName + " complemented"));
        return true;
    }

    // REQUIRES: exInd >= 0
    // MODIFIES: this, exons.get(exInd)
    // EFFECTS: reverses the sequence of the specified exon in Exons with the index
    // exInd,
    // returns true if the exon was reversed and false if not.
    public boolean reverseExon(int exInd) {
        if (exons.get(exInd).isReversed) {
            return false;
        }
        exons.get(exInd).reverseSeq();
        EventLog.getInstance().logEvent(
                new Event("Exon" + exons.get(exInd).getExonNum() + "of transcript " + transcriptName + " reversed"));
        return true;
    }

    // REQUIRES: exInd >= 0
    // MODIFIES: this, introns.get(intInd)
    // EFFECTS: reverses the sequence of the specified intron in Introns with the
    // index intInd,
    // returns true if the intron was reversed and false if not.
    public boolean reverseIntron(int intInd) {
        if (introns.get(intInd).isReversed) {
            return false;
        }
        introns.get(intInd).reverseSeq();
        EventLog.getInstance().logEvent(new Event(
                "Intron" + introns.get(intInd).getIntronNum() + "of transcript " + transcriptName + " reversed"));
        return true;
    }

    // REQUIRES: intInd >= 0
    // MODIFIES: this, introns.get(intInd)
    // EFFECTS: turns the sequence of the specified intron in Introns with the index
    // intInd into its complement,
    // returns true if the inton was made into its complement and false if not.
    public boolean complementIntron(int intInd) {
        if (introns.get(intInd).isComplement) {
            return false;
        }
        introns.get(intInd).complementSeq();
        EventLog.getInstance().logEvent(new Event(
                "Intron" + introns.get(intInd).getIntronNum() + "of transcript " + transcriptName + " complemented"));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: reverses the sequence of all exons in the transcript, skipping those
    // already reversed.
    public void reverseAllExons() {
        for (int i = 0; i < exons.size(); i++) {
            if (exons.get(i).isReversed) {
                continue;
            }
            exons.get(i).reverseSeq();
        }
        EventLog.getInstance().logEvent(new Event("All exons " + "of transcript " + transcriptName + " reversed"));
    }

    // MODIFIES: this
    // EFFECTS: turns the sequence of all of the exons in the transcript into their
    // respective complements,
    // skipping the ones already complementary.
    public void complementAllExons() {
        for (int i = 0; i < exons.size(); i++) {
            if (exons.get(i).isComplement) {
                continue;
            }
            exons.get(i).complementSeq();
        }
        EventLog.getInstance().logEvent(new Event("All exons " + "of transcript " + transcriptName + " complemented"));
    }

    // MODIFIES: this
    // EFFECTS: reverses the sequence of all introns in the transcript, skipping the
    // ones already reversed.
    public void reverseAllIntrons() {
        for (int i = 0; i < introns.size(); i++) {
            if (introns.get(i).isReversed) {
                continue;
            }
            introns.get(i).reverseSeq();
        }
        EventLog.getInstance().logEvent(new Event("All introns " + "of transcript " + transcriptName + " reversed"));
    }

    // MODIFIES: this
    // EFFECTS: turns the sequence of all introns in the transcript into their
    // respective complements,
    // skipping those already made complementary.
    public void complementAllIntrons() {
        for (int i = 0; i < introns.size(); i++) {
            if (introns.get(i).isComplement) {
                continue;
            }
            introns.get(i).complementSeq();
        }
        EventLog.getInstance()
                .logEvent(new Event("All introns " + "of transcript " + transcriptName + " complemented"));
    }

    // EFFECTS: Creates a single list of exons and introns in order.
    public ArrayList<ModifiableSequence> getExIntList() {
        EventLog.getInstance().logEvent(new Event("Exons and introns for " + toString() + " viewed"));
        ArrayList<ModifiableSequence> compiledSeq = new ArrayList<ModifiableSequence>();
        Iterator<Exon> exonIter = exons.listIterator();
        Iterator<Intron> intronIter = introns.listIterator();
        while (exonIter.hasNext() && intronIter.hasNext()) {
            compiledSeq.add(exonIter.next());
            compiledSeq.add(intronIter.next());
        }
        while (intronIter.hasNext()) {
            compiledSeq.add(intronIter.next());
        }
        while (exonIter.hasNext()) {
            compiledSeq.add(exonIter.next());
        }
        return compiledSeq;
    }

    // MODIFIES: this
    // EFFECTS: Reverses this transcript's sequence
    @Override
    public boolean reverseSeq() {
        EventLog.getInstance().logEvent(new Event("Transcript " + transcriptName + " reversed"));
        return super.reverseSeq();
    }

    // MODIFIED: this
    // EFFECTS: Complements this transcript's sequence
    @Override
    public boolean complementSeq() {
        EventLog.getInstance().logEvent(new Event("Transcript " + transcriptName + " complemented"));
        return super.complementSeq();
    }

    public boolean isReversed() {
        return super.isReversed();
    }

    public boolean isComplement() {
        return super.isComplement();
    }

    public Exon getExon(int exInd) {
        return exons.get(exInd);
    }

    public Intron getIntron(int intInd) {
        return introns.get(intInd);
    }

    @Override
    public String getSequence() {
        EventLog.getInstance().logEvent(new Event("" + toString() + " viewed"));
        return super.getSequence();
    }

    public String getTranscriptName() {
        return transcriptName;
    }

    public ArrayList<Exon> getAllExons() {
        return exons;
    }

    public ArrayList<Intron> getAllIntrons() {
        return introns;
    }

    public void setIntrons(ArrayList<Intron> introns) {
        this.introns = introns;
    }

    public void setExons(ArrayList<Exon> exons) {
        this.exons = exons;
    }

    // EFFECTS: returns a string containing the name of the transcript,
    // and whether or not the genomic sequence is reversed or complimentary.
    @SuppressWarnings("methodlength")
    public String toString() {
        String ret = transcriptName;
        ret += super.toString();
        ArrayList<Exon> revExons = getReversedExons();
        ArrayList<Intron> revIntrons = getReversedIntrons();
        ArrayList<Exon> compExons = getComplementExons();
        ArrayList<Intron> compIntrons = getComplementIntrons();
        for (int i = 0; i < revExons.size(); i++) {
            ret += (i == 0) ? (" Exon " + revExons.get(i).getExonNum()) : (", " + revExons.get(i).getExonNum());
            ret += (i == revExons.size() - 1) ? (" reversed ") : ("");
        }
        for (int i = 0; i < compExons.size(); i++) {
            ret += (i == 0) ? (" Exon " + compExons.get(i).getExonNum()) : (", " + compExons.get(i).getExonNum());
            ret += (i == compExons.size() - 1) ? (" complementary ") : ("");
        }
        for (int i = 0; i < revIntrons.size(); i++) {
            ret += (i == 0) ? (" Intron " + revIntrons.get(i).getIntronNum())
                    : (", " + revIntrons.get(i).getIntronNum());
            ret += (i == revIntrons.size() - 1) ? (" reversed ") : ("");
        }
        for (int i = 0; i < compIntrons.size(); i++) {
            ret += (i == 0) ? (" Intron " + compIntrons.get(i).getIntronNum())
                    : (", " + compIntrons.get(i).getIntronNum());
            ret += (i == compIntrons.size() - 1) ? (" complementary") : ("");
        }
        return ret;
    }

    // EFFECTS: retrieves and returns a list of all of the reversed exons in the
    // transcript.
    public ArrayList<Exon> getReversedExons() {
        ArrayList<Exon> revExons = new ArrayList<Exon>();
        for (Exon e : exons) {
            if (e.isReversed()) {
                revExons.add(e);
            }
        }
        return revExons;
    }

    // EFFECTS: retrieves and returns a list of all of the complementary exons in
    // the transcript.
    public ArrayList<Exon> getComplementExons() {
        ArrayList<Exon> compExons = new ArrayList<Exon>();
        for (Exon e : exons) {
            if (e.isComplement()) {
                compExons.add(e);
            }
        }
        return compExons;
    }

    // EFFECTS: retrieves and returns a list of all of the reversed introns in the
    // transcript.
    public ArrayList<Intron> getReversedIntrons() {
        ArrayList<Intron> revIntrons = new ArrayList<Intron>();
        for (Intron i : introns) {
            if (i.isReversed()) {
                revIntrons.add(i);
            }
        }
        return revIntrons;
    }

    // EFFECTS: retrieves and returns a list of all of the complementary introns in
    // the transcript.
    public ArrayList<Intron> getComplementIntrons() {
        ArrayList<Intron> compIntrons = new ArrayList<Intron>();
        for (Intron i : introns) {
            if (i.isComplement()) {
                compIntrons.add(i);
            }
        }
        return compIntrons;
    }

    // MODIFIES: this
    // EFFECTS: sets the transcript name to name
    public void setTranName(String name) {
        transcriptName = name;
    }

    // MODIFIES: this
    // EFFECTS: Reads in sequences from a gene file and sets the values of this
    // transcript to them.
    @SuppressWarnings("methodlength")
    public void readFile(BufferedReader fileReader) throws IOException {
        boolean endOfFile = false;
        String seqType = fileReader.readLine();
        int exNum = 0;
        int intNum = 0;
        while (!endOfFile) {
            while (seqType.equals("")) {
                seqType = fileReader.readLine();
            }
            if (transcriptName.equals("")) {
                transcriptName = seqType.substring(0, seqType.indexOf(" "));
            }
            if (seqType.contains("exon")) {
                exNum++;
                String[] outputs = readSequence(fileReader);
                seqType = outputs[1];
                exons.add(new Exon(exNum, outputs[0].strip()));
            } else if (seqType.contains("intron")) {
                intNum++;
                String[] outputs = readSequence(fileReader);
                seqType = outputs[1];
                introns.add(new Intron(intNum, outputs[0].strip()));
            } else {
                seqType = fileReader.readLine();
                while (seqType != null) {
                    this.sequence += seqType.strip();
                    seqType = fileReader.readLine();
                }
                endOfFile = true;
            }
        }
    }

    // EFFECTS: Reads the sequence for a specified sequence in the file.
    public String[] readSequence(BufferedReader b) throws IOException {
        String currentLine = b.readLine();
        String sequence = "";
        while (!currentLine.contains(">") && !currentLine.equals("")) {
            sequence += currentLine;
            currentLine = b.readLine();
        }
        String[] temp = new String[2];
        temp[0] = sequence;
        temp[1] = currentLine;
        return temp;
    }

    // EFFECTS: creates a deep clone of this transcript object and returns it.
    @Override
    public Transcript clone() throws CloneNotSupportedException {
        Transcript transcriptClone = (Transcript) super.clone();
        ArrayList<Exon> exonClone = new ArrayList<Exon>();
        ArrayList<Intron> intronClone = new ArrayList<Intron>();
        for (Exon e : exons) {
            exonClone.add((Exon) e.clone());
        }
        for (Intron i : introns) {
            intronClone.add((Intron) i.clone());
        }
        transcriptClone.setExons(exonClone);
        transcriptClone.setIntrons(intronClone);
        return transcriptClone;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("name", transcriptName);
        json.put("exons", exonsToJson());
        json.put("introns", intronsToJson());
        return json;
    }

    private JSONArray exonsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exon e : exons) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    private JSONArray intronsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Intron i : introns) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}
