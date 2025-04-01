package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;

public class TestTranscript {
    
    Transcript testTran;
    ArrayList<Exon> testExons;
    ArrayList<Intron> testIntrons;
    Exon tExon1;
    Exon tExon2;
    Intron tIntron1;
    Intron tIntron2;

    @BeforeEach
    void runBefore() {
        tExon1 = new Exon(1, "CCTCGGA");
        //Complement: GGAGCCT
        //Reverse: AGGCTCC
        //Reverse Complement: TCCGAGG
        tExon2 = new Exon(2, "GGTACCAG");
        //Complement: CCATGGTC
        //Reverse: GACCATGG
        //Reverse Complement: CTGGTCC
        tIntron1 = new Intron(1, "AATTTTTTGC");
        //Complement: TTAAAAAACG
        //Reverse: CGTTTTTTAA
        //Reverse Complement: GCAAAAAATT
        tIntron2 = new Intron(2, "TTTTGCACAC");
        //Complement: AAAACGTGTG
        //Reverse: CACACGTTTT
        //Reverse Complement: GTGTGCAAAA
        testExons = new ArrayList<Exon>();
        testExons.add(tExon1);
        testExons.add(tExon2);
        testIntrons = new ArrayList<Intron>();
        testIntrons.add(tIntron1);
        testIntrons.add(tIntron2);
        testTran = new Transcript("GENE1", "CCTGGGCTATTGC", testExons, testIntrons);

    }

    @Test
    void testReverseSeq() {
        assertFalse(testTran.isReversed());
        assertTrue(testTran.reverseSeq());
        assertTrue(testTran.isReversed());
        assertEquals("CGTTATCGGGTCC", testTran.getSequence());
        assertFalse(testTran.reverseSeq());
        assertEquals("CGTTATCGGGTCC", testTran.getSequence());
        assertTrue(testTran.isReversed());

    }

    @Test
    void testComplementSeq() {
        assertFalse(testTran.isComplement());
        assertTrue(testTran.complementSeq());
        assertEquals("GGACCCGATAACG", testTran.getSequence());
        assertTrue(testTran.isComplement());
        assertFalse(testTran.complementSeq());
        assertEquals("GGACCCGATAACG", testTran.getSequence());
        assertTrue(testTran.isComplement());
    }

    @Test
    void testComplementExon() {
        assertTrue(testTran.complementExon(0));
        assertEquals("GGAGCCT", testTran.getExon(0).getSequence());
        assertFalse(testTran.complementExon(0));
    }

    @Test
    void testComplementIntron() {
        assertTrue(testTran.complementIntron(0));
        assertEquals("TTAAAAAACG", testTran.getIntron(0).getSequence());
        assertFalse(testTran.complementIntron(0));
    }

    @Test
    void testReverseExon() {
        assertTrue(testTran.reverseExon(0));
        assertEquals("AGGCTCC", testTran.getExon(0).getSequence());
        assertFalse(testTran.reverseExon(0));
    }

    @Test
    void testReverseIntron() {
        assertTrue(testTran.reverseIntron(0));
        assertEquals("CGTTTTTTAA", testTran.getIntron(0).getSequence());
        assertFalse(testTran.reverseIntron(0));
    }

    @Test
    void testComplementAllExonsWithoutComplements() {
        testTran.complementAllExons();
        assertEquals("GGAGCCT", testTran.getExon(0).getSequence());
        assertEquals("CCATGGTC", testTran.getExon(1).getSequence());
    }

    @Test
    void testComplementAllExonsWithComplements() {
        testTran.complementExon(1);
        testTran.complementAllExons();
        assertEquals("GGAGCCT", testTran.getExon(0).getSequence());
        assertEquals("CCATGGTC", testTran.getExon(1).getSequence());
    }

    @Test
    void testComplementAllIntronsWithoutComplements() {
        testTran.complementAllIntrons();
        assertEquals("TTAAAAAACG", testTran.getIntron(0).getSequence());
        assertEquals("AAAACGTGTG", testTran.getIntron(1).getSequence());
    }

    @Test
    void testComplementAllIntronsWithComplements() {
        testTran.complementIntron(1);
        testTran.complementAllIntrons();
        assertEquals("TTAAAAAACG", testTran.getIntron(0).getSequence());
        assertEquals("AAAACGTGTG", testTran.getIntron(1).getSequence());
    }

    @Test
    void testReverseAllExonsWithoutReversals() {
        testTran.reverseAllExons();
        assertEquals("AGGCTCC", testTran.getExon(0).getSequence());
        assertEquals("GACCATGG", testTran.getExon(1).getSequence());
    }

    @Test
    void testReverseAllExonsWithReversals() {
        testTran.reverseExon(1);
        testTran.reverseAllExons();
        assertEquals("AGGCTCC", testTran.getExon(0).getSequence());
        assertEquals("GACCATGG", testTran.getExon(1).getSequence());
    }

    @Test
    void testReverseAllIntronsWithoutReversals() {
        testTran.reverseAllIntrons();
        assertEquals("CGTTTTTTAA", testTran.getIntron(0).getSequence());
        assertEquals("CACACGTTTT", testTran.getIntron(1).getSequence());
    }

    @Test
    void testReverseAllIntronsWithReversals() {
        testTran.reverseIntron(1);
        testTran.reverseAllIntrons();
        assertEquals("CGTTTTTTAA", testTran.getIntron(0).getSequence());
        assertEquals("CACACGTTTT", testTran.getIntron(1).getSequence());
    }

    @Test
    void testClone() throws CloneNotSupportedException{
        Transcript testClone = testTran.clone();
        assertEquals(testClone.getTranscriptName(), testTran.getTranscriptName());
        assertEquals(testClone.getExon(0).getSequence(), testTran.getExon(0).getSequence());
        assertEquals(testClone.getExon(1).getSequence(), testTran.getExon(1).getSequence());
        assertEquals(testClone.getIntron(0).getSequence(), testTran.getIntron(0).getSequence());
        assertEquals(testClone.getIntron(1).getSequence(), testTran.getIntron(1).getSequence());
    }

    @Test
    void testGetExIntList() {
        ArrayList<ModifiableSequence> tester = new ArrayList<ModifiableSequence>();
        tester.add(tExon1);
        tester.add(tIntron1);
        tester.add(tExon2);
        tester.add(tIntron2);
        ArrayList<ModifiableSequence> testList = testTran.getExIntList();
        for(int i = 0; i < tester.size(); i++) {
            assertEquals(tester.get(i), testList.get(i));
        }
    }

    @Test
    void testGetExIntListOddIntron() {
        ArrayList<ModifiableSequence> tester = new ArrayList<ModifiableSequence>();
        tester.add(tExon1);
        tester.add(tIntron1);
        tester.add(tExon2);
        tester.add(tIntron2);
        Intron tIntron3 = new Intron(3, "CAAAGAT");
        tester.add(tIntron3);
        testIntrons.add(tIntron3);
        testTran = new Transcript("GENE1", "CCTGGGCTATTGC", testExons, testIntrons);
        ArrayList<ModifiableSequence> testList = testTran.getExIntList();
        for(int i = 0; i < tester.size(); i++) {
            assertEquals(tester.get(i), testList.get(i));
        }
    }

    @Test
    void testGetExIntListOddExon() {
        ArrayList<ModifiableSequence> tester = new ArrayList<ModifiableSequence>();
        tester.add(tExon1);
        tester.add(tIntron1);
        tester.add(tExon2);
        tester.add(tIntron2);
        Exon tExon3 = new Exon(3, "CAAAGAT");
        tester.add(tExon3);
        testExons.add(tExon3);
        testTran = new Transcript("GENE1", "CCTGGGCTATTGC", testExons, testIntrons);
        ArrayList<ModifiableSequence> testList = testTran.getExIntList();
        for(int i = 0; i < tester.size(); i++) {
            assertEquals(tester.get(i), testList.get(i));
        }
    }

    @Test
    void testToString() {
        Exon tExon3 = new Exon (3, "ACCCAC");
        Intron tIntron3 = new Intron (3, "CCGTAGA");
        testExons.add(tExon3);
        testIntrons.add(tIntron3);
        testTran = new Transcript("GENE1", "CCTGGGCTATTGC", testExons, testIntrons);
        assertEquals("GENE1", testTran.toString());
        testTran.reverseSeq();
        assertEquals("GENE1 reversed", testTran.toString());
        testTran.complementSeq();
        assertEquals("GENE1 reversed, complementary", testTran.toString());
        testTran.complementExon(1);
        testTran.complementExon(2);
        assertEquals("GENE1 reversed, complementary Exon 2, 3 complementary ", testTran.toString());
        testTran.reverseExon(2);
        testTran.reverseExon(1);
        testTran.reverseExon(0);
        assertEquals("GENE1 reversed, complementary Exon 1, 2, 3 reversed  Exon 2, 3 complementary ", testTran.toString());
        testTran.complementIntron(0);
        testTran.complementIntron(1);
        testTran.complementIntron(2);
        assertEquals("GENE1 reversed, complementary Exon 1, 2, 3 reversed  Exon 2, 3 complementary  Intron 1, 2, 3 complementary", testTran.toString());
        testTran.reverseIntron(1);
        testTran.reverseIntron(2);
        assertEquals("GENE1 reversed, complementary Exon 1, 2, 3 reversed  Exon 2, 3 complementary  Intron 2, 3 reversed  Intron 1, 2, 3 complementary", testTran.toString());
    }

    @Test
    void testGetGeneSeq() {
        assertEquals("CCTGGGCTATTGC", testTran.getSequence());
    }

    @Test
    void testGetAllIntrons() {
        ArrayList<Intron> testIntrons = new ArrayList<Intron>();
        testIntrons.add(tIntron1);
        testIntrons.add(tIntron2);
        assertEquals(testIntrons, testTran.getAllIntrons());
    }

    @Test
    void testGetAllExons() {
        ArrayList<Exon> testerExons = new ArrayList<Exon>();
        testerExons.add(tExon1);
        testerExons.add(tExon2);
        assertEquals(testerExons, testTran.getAllExons());
    }

    @Test
    void testSetExons() {
        ArrayList<Exon> testerExons = new ArrayList<Exon>();
        testerExons.add(new Exon(1, "GCCG"));
        testerExons.add(new Exon(2, "ATTGC"));
        testTran.setExons(testerExons);
        assertEquals(testerExons, testTran.getAllExons());
    }

    @Test
    void testSetIntrons() {
        ArrayList<Intron> testerIntrons = new ArrayList<Intron>();
        testerIntrons.add(new Intron(1, "GCCG"));
        testerIntrons.add(new Intron(2, "ATTGC"));
        testTran.setIntrons(testerIntrons);
        assertEquals(testerIntrons, testTran.getAllIntrons());
    }

    @Test
    void testReadFile() throws IOException {
        Transcript testRead = new Transcript();
        BufferedReader r = new BufferedReader(new FileReader(new File("gene_files/testGene.fa")));
        testRead.readFile(r);
        assertEquals(">GENE1", testRead.getTranscriptName());
        assertEquals(testTran.getSequence(), testRead.getSequence());
        ArrayList<Exon> readExons = testRead.getAllExons();
        ArrayList<Intron> readIntrons = testRead.getAllIntrons();
        for(int i = 0; i < readExons.size(); i++) {
            assertEquals(testExons.get(i).getSequence(), readExons.get(i).getSequence());
        }
        for(int i = 0; i < readIntrons.size(); i++) {
            assertEquals(testIntrons.get(i).getSequence(), readIntrons.get(i).getSequence());
        }
        r.close();
    }
}
