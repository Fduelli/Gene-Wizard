package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIntron {

    Intron testIntron;

    @BeforeEach
    void runBefore() {
        testIntron = new Intron(1, "AAACCTGCG");
        //reverse: GCGTCCAAA
        //complementary: TTTGGACGC
    }

    @Test
    public void testReverseSeq() {
        assertFalse(testIntron.isReversed());
        assertTrue(testIntron.reverseSeq());
        assertEquals("GCGTCCAAA", testIntron.getSequence());
        assertTrue(testIntron.isReversed());
        assertFalse(testIntron.reverseSeq());
    }

    @Test
    public void testComplementSeq() {
        assertFalse(testIntron.isComplement());
        assertTrue(testIntron.complementSeq());
        assertEquals("TTTGGACGC", testIntron.getSequence());
        assertTrue(testIntron.isComplement());
        assertFalse(testIntron.complementSeq());
    }

    @Test
    public void testGetIntronSeq() {
        assertEquals("AAACCTGCG", testIntron.getSequence());
    }

    @Test
    public void testToString() {
        assertEquals("Intron 1", testIntron.toString());
        testIntron.reverseSeq();
        assertEquals("Intron 1 reversed", testIntron.toString());
        testIntron.complementSeq();
        assertEquals("Intron 1 reversed, complementary", testIntron.toString());
    }

    @Test
    public void testClone() throws CloneNotSupportedException{
        Intron intronClone = testIntron.clone();
        assertEquals(intronClone.getSequence(), testIntron.getSequence());
        assertEquals(intronClone.getIntronNum(), testIntron.getIntronNum());
        assertEquals(intronClone.isReversed(), testIntron.isReversed());
        assertEquals(intronClone.isComplement(), testIntron.isComplement());
    }

    
}
