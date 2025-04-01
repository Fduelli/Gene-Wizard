package persistence;

import model.*;
import java.io.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            TranscriptList tList = reader.read();
            fail("IOException expected");
        }catch(IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyTranscriptList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTranscriptList.json");
        try {
            TranscriptList tList = reader.read();
            assertTrue(tList.isEmpty());
        } catch (IOException i) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTranscriptList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTranscriptList.json");
        try{
            ArrayList<Exon> exons = new ArrayList<Exon>();
            exons.add(new Exon(1, "AGGGCG"));
            ArrayList<Intron> introns = new ArrayList<Intron>();
            introns.add(new Intron(1, "GTTCGCGC"));
            TranscriptList tList = reader.read();
            ArrayList<Transcript> transcripts = tList.getList(); 
            assertEquals(2, transcripts.size());
            checkTranscript("testGene1", "GGGGGGGTGGC", exons, introns, transcripts.get(0));
            exons = new ArrayList<Exon>();
            introns = new ArrayList<Intron>();
            exons.add(new Exon(1, "GACTAC"));
            introns.add(new Intron(1, "ATTTGG"));
            checkTranscript("testGene2", "AAAAATACAC", exons, introns, transcripts.get(1));
        }catch(IOException i) {
            fail("Couldn't read from file");
        }
    }
}
