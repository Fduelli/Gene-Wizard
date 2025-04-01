package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try{
            TranscriptList tList = new TranscriptList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException i) {
            //pass
        }
    }

    @Test
    void testWriterEmptyTranscriptList() {
        try{
            TranscriptList tranList = new TranscriptList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTranscriptList.json");
            writer.open();
            writer.write(tranList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTranscriptList.json");
            tranList = reader.read();
            assertEquals(0, tranList.getList().size());
        }catch (IOException i) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTranscriptList() {
        try {
            ArrayList<Exon> exons1 = new ArrayList<Exon>();
            exons1.add(new Exon(1, "AGGGCG"));
            ArrayList<Intron> introns1 = new ArrayList<Intron>();
            introns1.add(new Intron(1, "GTTCGCGC"));
            TranscriptList tranList = new TranscriptList();
            tranList.addTranscript(new Transcript("testGene1", "CCCGGGC", exons1, introns1));
            ArrayList<Exon> exons2 = new ArrayList<Exon>();
            ArrayList<Intron> introns2 = new ArrayList<Intron>();
            exons2.add(new Exon(1, "GACTAC"));
            introns2.add(new Intron(1, "ATTTGG"));
            tranList.addTranscript(new Transcript("testGene2", "AAAAACCG", exons2, introns2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTranscriptList.json");
            writer.open();
            writer.write(tranList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTranscriptList.json");
            tranList = reader.read();
            assertEquals(2, tranList.getList().size());
            checkTranscript("testGene1", "CCCGGGC", exons1, introns1, tranList.getList().get(0));
            checkTranscript("testGene2", "AAAAACCG", exons2, introns2, tranList.getList().get(1));

        }catch (IOException i) {
            fail("Exception should not have been thrown");
        }
    }
}
