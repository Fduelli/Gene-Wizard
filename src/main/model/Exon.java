package model;

import org.json.JSONObject;

//The Exon class defines the gene sequence for a particular exon in a transcript.
public class Exon extends ModifiableSequence {

    private int exonNum;

    // EFFECTS: Calls the superclass and sets the sequence to exSeq;
    // Sets the exons number to exonNum.
    public Exon(int exonNum, String exSeq) {
        super(exSeq);
        this.exonNum = exonNum;
    }

    public int getExonNum() {
        return exonNum;
    }

    @Override
    public String getSequence() {
        EventLog.getInstance().logEvent(new Event("" + toString() + " viewed"));
        return super.getSequence();
    }

    // EFFECTS: returns a string containing the exon number, and whether or not it
    // is reversed or complementary.
    public String toString() {
        String ret = "Exon " + exonNum;
        ret += super.toString();
        return ret;
    }

    // EFFECTS: creates a clone of this object
    @Override
    public Exon clone() throws CloneNotSupportedException {
        return (Exon) super.clone();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("number", exonNum);
        return json;
    }

}
