package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExon {

    Exon testExon;

    @BeforeEach
    public void runBefore() {
        testExon = new Exon(1, "GGCATTC");
        //Reverse: CTTACGG
        //Complement: CCGTAAG
    }

    @Test
    public void testReverseSeq() {
        assertFalse(testExon.isReversed());
        assertTrue(testExon.reverseSeq());
        assertEquals("CTTACGG", testExon.getSequence());
        assertTrue(testExon.isReversed());
        assertFalse(testExon.reverseSeq());
    }

    @Test
    public void testComplementSeq() {
        assertFalse(testExon.isComplement());
        assertTrue(testExon.complementSeq());
        assertEquals("CCGTAAG", testExon.getSequence());
        assertTrue(testExon.isComplement());
        assertFalse(testExon.complementSeq());
    }

    @Test
    public void testGetExonSeq() {
        assertEquals("GGCATTC", testExon.getSequence());
    }

    @Test
    public void testToString() {
        assertEquals("Exon 1", testExon.toString());
        testExon.reverseSeq();
        assertEquals("Exon 1 reversed", testExon.toString());
        testExon.complementSeq();
        assertEquals("Exon 1 reversed, complementary", testExon.toString());
        testExon = new Exon(1, "GCCTAG");
        testExon.complementSeq();
        assertEquals("Exon 1 complementary", testExon.toString());
    }

    @Test
    public void testClone() throws CloneNotSupportedException{
        Exon exonClone = testExon.clone();
        assertEquals(exonClone.getSequence(), testExon.getSequence());
        assertEquals(exonClone.getExonNum(), testExon.getExonNum());
        assertEquals(exonClone.isReversed(), testExon.isReversed());
        assertEquals(exonClone.isComplement(), testExon.isComplement());
    }


}
