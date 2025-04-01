package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class JsonTest {
    protected void checkTranscript(String tranName, String genSeq, ArrayList<Exon> exons, ArrayList<Intron> introns, Transcript tran) {
        assertEquals(genSeq, tran.getSequence());
        assertEquals(tranName, tran.getTranscriptName());
        try{
            for(int i = 0; i<exons.size(); i++) {
                assertEquals(exons.get(i).getSequence(), tran.getExon(i).getSequence());
                assertEquals(exons.get(i).getExonNum(), tran.getExon(i).getExonNum());
                assertEquals(exons.get(i).isReversed(), tran.getExon(i).isReversed());
                assertEquals(exons.get(i).isComplement(), tran.getExon(i).isComplement());
            }
            for(int i = 0; i<introns.size(); i++) {
                assertEquals(introns.get(i).getSequence(), tran.getIntron(i).getSequence());
                assertEquals(introns.get(i).getIntronNum(), tran.getIntron(i).getIntronNum());
                assertEquals(introns.get(i).isReversed(), tran.getIntron(i).isReversed());
                assertEquals(introns.get(i).isComplement(), tran.getIntron(i).isComplement());
            }
        }catch(NullPointerException e) {
            fail("length mismatch in either exon list or intron list.");
        }
    }
}
