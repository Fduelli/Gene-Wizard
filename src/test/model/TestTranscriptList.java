package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTranscriptList {

    TranscriptList testList;
    Transcript testTran1;
    Transcript testTran2;

    @BeforeEach
    void runBefore() {
        testList = new TranscriptList();
        testTran1 = new Transcript("GENE1", "GGCTTATG", new ArrayList<Exon>(), new ArrayList<Intron>());
        testTran2 = new Transcript("GENE2", "CCGATATC", new ArrayList<Exon>(), new ArrayList<Intron>());
    }

    @Test
    void testAddTranscript() {
        testList.addTranscript(testTran1);
        assertEquals(testTran1, testList.getTranscript(0));
    }

    @Test
    void testAddMultipleTranscripts() {
        testList.addTranscript(testTran1);
        testList.addTranscript(testTran2);
        assertEquals(testTran1, testList.getTranscript(0));
        assertEquals(testTran2, testList.getTranscript(1));
    }

    @Test
    void testRemoveTranscript() {
        testList.addTranscript(testTran1);
        testList.addTranscript(testTran2);
        testList.removeTranscript(0);
        assertEquals(testTran2, testList.getTranscript(0));
    }

    @Test
    void testGetAll() throws CloneNotSupportedException{
        Transcript testClone = testTran1.clone();
        ArrayList<Transcript> testGet = new ArrayList<Transcript>();
        testGet.add(testTran1);
        testGet.add(testClone);
        testList.addTranscript(testTran1);
        testList.addTranscript(testTran2);
        testList.addTranscript(testClone);
        assertEquals(testGet, testList.getAll("GENE1"));
    }

    @Test
    void testGetList() {
        ArrayList<Transcript> testGet = new ArrayList<Transcript>();
        testList.addTranscript(testTran1);
        testList.addTranscript(testTran2);
        testGet.add(testTran1);
        testGet.add(testTran2);
        assertEquals(testGet, testList.getList());
    }

    @Test
    void testIsEmpty() {
        assertTrue(testList.isEmpty());
        testList.addTranscript(testTran1);
        assertFalse(testList.isEmpty());
    }
}
