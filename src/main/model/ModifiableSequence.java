package model;

import java.util.*;
import persistence.Writable;
import org.json.*;

//Abstract class that holds information that is shared among all objects that contain a modifiable sequence.
public abstract class ModifiableSequence implements Cloneable, Writable {

    protected String sequence;
    protected boolean isReversed;
    protected boolean isComplement;

    // EFFECTS: Sets isReversed and isComplement to false;
    // Sets the value of the sequence field to sequence.
    public ModifiableSequence(String sequence) {
        this.sequence = sequence;
        isReversed = false;
        isComplement = false;
    }

    // MODIFIES: this
    // EFFECTS: sets the sequence equal to sequence
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    // REQUIRES: isReversed = false, sequence only contains A, C, T, or G
    // MODIFIES: this
    // EFFECTS: reverses the sequence and sets isReversed to true;
    // returns true if the sequence was reversed and false if not.
    public boolean reverseSeq() {
        if (isReversed) {
            return false;
        }
        char[] seqArray = sequence.toCharArray();
        String revSeq = "";
        Stack<Character> temp = new Stack<Character>();
        for (char c : seqArray) {
            temp.push(c);
        }
        while (!temp.isEmpty()) {
            revSeq += temp.pop();
        }
        sequence = revSeq;
        isReversed = true;
        return true;
    }

    // REQUIRES: isCompliment = false, sequence contains only A, C, T, G's
    // MODIFIES: this
    // EFFECTS: turns the sequence into its compliment and sets isCompliment to
    // true;
    // returns true if the sequence was made complimentary and false if not.
    public boolean complementSeq() throws IllegalArgumentException {
        if (isComplement) {
            return false;
        }
        String compSeq = "";
        char[] seqArray = sequence.toCharArray();
        for (int i = 0; i < seqArray.length; i++) {
            switch (seqArray[i]) {
                case 'A': seqArray[i] = 'T';
                    break;
                case 'T': seqArray[i] = 'A';
                    break;
                case 'C': seqArray[i] = 'G';
                    break;
                default: seqArray[i] = 'C';
                    break;
            }
        }
        for (char c : seqArray) {
            compSeq += c;
        }
        sequence = compSeq;
        isComplement = true;
        return true;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public boolean isComplement() {
        return isComplement;
    }

    public String getSequence() {
        return sequence;
    }

    // MODIFIES: this
    // EFFECTS: sets isReversed to reversed
    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    // MODIFIES: this
    // EFFECTS: sets isComplement to complement
    public void setComplementary(boolean complement) {
        isComplement = complement;
    }

    // EFFECTS: returns a strong on whether the object is reversed or complementary
    public String toString() {
        String ret = "";
        if (isReversed || isComplement) {
            ret = " ";
        }
        if (isReversed) {
            ret += "reversed";
        }
        if (isComplement) {
            if (isReversed) {
                ret += ", ";
            }
            ret += "complementary";
        }
        return ret;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sequence", sequence);
        json.put("reversed", isReversed());
        json.put("complementary", isComplement());
        return json;
    }

}
