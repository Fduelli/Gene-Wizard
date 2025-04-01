package model;

import org.json.*;

//The Intron class defines the gene sequence for a particular Intron in a transcript.
public class Intron extends ModifiableSequence {

    private int intronNum;

    // EFFECTS: Calls the superclass and sets the sequence to intSeq.
    // Sets the introns number to intronNum.
    public Intron(int intronNum, String intSeq) {
        super(intSeq);
        this.intronNum = intronNum;
    }

    public int getIntronNum() {
        return intronNum;
    }

    @Override
    public String getSequence() {
        EventLog.getInstance().logEvent(new Event("" + toString() + " viewed"));
        return super.getSequence();
    }

    // EFFECTS: returns a string with the exon number, and whether or not it is
    // reversed or complementary
    public String toString() {
        String ret = "Intron " + intronNum;
        ret += super.toString();
        return ret;
    }

    // EFFECTS: creates a clone of this object
    @Override
    public Intron clone() throws CloneNotSupportedException {
        return (Intron) super.clone();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("number", intronNum);
        return json;
    }

}
