package ui;

import model.*;
import java.util.*;
import java.io.*;
import persistence.*;

//Creates a visual terminal interface for the user to access their modified transcripts
public class GeneWizard {
    TranscriptList transcripts;
    boolean isFinished;
    Scanner sc;
    private static final String JSON_STORE = "./data/transcriptList.json";
    JsonReader reader;
    JsonWriter writer;

    // MODIFIES: this
    // EFFECTS: creates a scanner instance for user input and runs runGeneWiz method
    public GeneWizard() {
        transcripts = new TranscriptList();
        sc = new Scanner(System.in);
        reader = new JsonReader(JSON_STORE);
        writer = new JsonWriter(JSON_STORE);
        runGeneWiz();
    }

    // EFFECTS: if there are no transcripts in the list, prompts the user to input a
    // transcript file,
    // otherwise displays a list of options for functions in the geneWiz
    public void runGeneWiz() {
        if (new File(JSON_STORE).exists()) {
            System.out.println("It looks like you have a transcript list already saved!" 
                                + " Would you like to load it?(Y/N)");
            if (sc.next().toUpperCase().equals("Y")) {
                loadTranscripts();
            }
        }
        if (transcripts.isEmpty()) {
            System.out.println("It looks like your gene list is empty. "
                    + "Add your first transcript file to start using Gene Wizard!");
            addTranscript();
        }
        while (!isFinished) {
            displayTranscriptList();
        }
        System.out.println("WAIT! Would you like to save your list so you can use it in the future? (Y/N)");
        if (sc.next().toUpperCase().equals("Y")) {
            saveTranscripts();
        }
        sc.close();
        System.exit(0);
    }

    // REQUIRES: !transcripts.isEmpty()
    // EFFECTS: displays a list of transcripts and modified transcripts and asks
    // user which transcript they want to view or modify. Or if they want to add a
    // new transcript.
    public void displayTranscriptList() {
        ArrayList<Transcript> tranList = transcripts.getList();
        System.out.println("Transcripts: ");
        for (int i = 0; i < tranList.size(); i++) {
            System.out.println(i + 1 + ". " + tranList.get(i).toString());
        }
        System.out.println(tranList.size() + 1 + ". ADD TRANSCRIPT");
        System.out.println(0 + ". EXIT PROGRAM");
        transcriptListInput(tranList);
    }

    // EFFECTS: Reads the input from the user after displaying the transcript list
    // and executes functions accordingly.
    public void transcriptListInput(ArrayList<Transcript> tranList) {
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Please enter which number gene you would like to access or modify:");
            try {
                int input = sc.nextInt();
                if (input == 0) {
                    isFinished = true;
                    return;
                } else if (input <= tranList.size()) {
                    displayExonsIntronsGeneSeqMenu(tranList.get(input - 1));
                } else if (input == tranList.size() + 1) {
                    addTranscript();
                } else {
                    throw new InputMismatchException();
                }
                isValid = true;
            } catch (InputMismatchException i) {
                System.out.println("invalid input, please enter a number between 0 and " + (tranList.size() + 1) + ".");
                sc.nextLine();
            }
        }
    }

    // REQUIRES: file exists in gene_files directory
    // MODIFIES: this
    // EFFECTS: prompts the user to enter the name of the file in gene_files
    // directory
    // reads in the transcripts' name, sequence, introns, and exons and saves them
    // to the transcriptList.
    public void readFile(File f) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
            Transcript t = new Transcript();
            t.readFile(fileReader);
            transcripts.addTranscript(t);
        } catch (IOException i) {
            System.out.println(
                    "There was an unexpected error with the file!"
                            + " Please retry with a new transcript file or redownload from ensembl.");
            return;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new transcript from a file into the list of transcripts
    @SuppressWarnings("methodlength")
    public void addTranscript() {
        boolean doAgain = true;
        while (doAgain) {
            System.out.println("Please input the name of the gene in gene_files you would like to import.");
            readFile(new File("gene_files/" + sc.next()));
            boolean valid = false;
            while (!valid) {
                System.out.println(
                        "Would you like to input another transcript? "
                                + "(if you havent successfuly added any transcripts, "
                                + "the program will automatically quit) (Y/N)");
                String isDoAgain = sc.next();
                if (isDoAgain.substring(0, 1).toUpperCase().equals("N")) {
                    if (transcripts.isEmpty()) {
                        sc.close();
                        System.exit(0);
                    }
                    doAgain = false;
                    valid = true;
                } else if (isDoAgain.substring(0, 1).toUpperCase().equals("Y")) {
                    valid = true;
                } else {
                    System.out.println("Invalid entry, please re-enter.");
                    sc.nextLine();
                }
            }
        }
    }

    // EFFECTS: Displays a menu of modifiable sequences and actions for the
    // transcript.
    public void displayExonsIntronsGeneSeqMenu(Transcript t) {
        System.out.println("List of Exons and Introns, "
                + "select what you want to view or modify by inputing the corresponding number.");
        ArrayList<ModifiableSequence> exIntList = t.getExIntList();
        System.out.println("1. Genomic DNA sequence " + (t.isReversed() ? "reversed" : "")
                + (t.isComplement() ? "complementary" : ""));
        for (int i = 0; i < exIntList.size(); i++) {
            System.out.println((i + 2) + ". " + exIntList.get(i).toString());
        }
        System.out.println("0. GO BACK");
        System.out.print("Which sequence would you like to view or modify?:");
        exonsIntronsGeneSeqMenuInput(exIntList, t);
    }

    // EFFECTS: recieves the input by the user from th exon intron gene sequence
    // list
    // and executes based on input.
    public void exonsIntronsGeneSeqMenuInput(ArrayList<ModifiableSequence> exIntList, Transcript t) {
        boolean isValid = false;
        while (!isValid) {
            try {
                int choiceNum = sc.nextInt();
                if (choiceNum == 0) {
                    return;
                } else if (choiceNum == 1) {
                    modOrViewMenu(t, t);
                } else if (choiceNum < exIntList.size() + 2) {
                    modOrViewMenu(exIntList.get(choiceNum - 2), t);
                } else {
                    throw new InputMismatchException();
                }
                isValid = true;
            } catch (InputMismatchException i) {
                System.out.println("The input you submitted is not valid, please try again.");
                sc.nextLine();
            }
        }
    }

    // EFFECTS: displays the selected exon, intron, or gene sequence name, and asks
    // if the user would like to modify it or view it.
    public void modOrViewMenu(ModifiableSequence m, Transcript t) {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("You are now viewing " + m.toString() + "!");
            System.out.println(
                    "Would you like to reverse this sequence, make it complementary, or view it?\n"
                            + "Each time you choose to modify this exon, "
                            + "a new copy of the transcript will be created with your specified modifications.");
            System.out.println("1. REVERSE\n2. COMPLEMENT\n3. VIEW SEQUENCE\n0. GO BACK");
            goBack = modOrViewMenuInput(m, t);
        }
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: Reads the input from the user after modify or view list is displayed
    // and executes functions accordingly
    public boolean modOrViewMenuInput(ModifiableSequence m, Transcript t) {
        boolean isValid = false;
        while (!isValid) {
            try {
                switch (sc.nextInt()) {
                    case 1:
                        reverseSequence(m, t);
                        break;
                    case 2:
                        complementSequence(m, t);
                        break;
                    case 3:
                        System.out.println(m.getSequence());
                        break;
                    case 0:
                        return true;
                    default:
                        throw new InputMismatchException();
                }
                isValid = true;
            } catch (InputMismatchException i) {
                System.out.println("Invalid input detected, please try again.");
                sc.nextLine();
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: creates a new copy of the gene with the modified sequence and adds
    // to the transcript list.
    public void reverseSequence(ModifiableSequence m, Transcript t) {
        try {
            Transcript tranClone = t.clone();
            boolean isReversed = true;
            int primeTranscriptInd = transcripts.getList().indexOf(t);
            if (m instanceof Exon) {
                m = (Exon) m;
                int exonInd = t.getAllExons().indexOf(m);
                isReversed = tranClone.reverseExon(exonInd);
            } else if (m instanceof Intron) {
                m = (Intron) m;
                int intronInd = t.getAllIntrons().indexOf(m);
                isReversed = tranClone.reverseIntron(intronInd);
            } else if (m instanceof Transcript) {
                isReversed = tranClone.reverseSeq();
            }
            if (isReversed == true) {
                transcripts.getList().add(primeTranscriptInd, tranClone);
            } else {
                System.out.println("Selected sequence already reversed!");
            }

        } catch (CloneNotSupportedException c) {
            c.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new copy of the gene with the complementary sequence and
    // adds to the transcript list.
    public void complementSequence(ModifiableSequence m, Transcript t) {
        try {
            boolean isComplement = true;
            Transcript tranClone = t.clone();
            int primeTranscriptInd = transcripts.getList().indexOf(t);
            if (m instanceof Exon) {
                m = (Exon) m;
                int exonInd = t.getAllExons().indexOf(m);
                isComplement = tranClone.complementExon(exonInd);
            } else if (m instanceof Intron) {
                m = (Intron) m;
                int intronInd = t.getAllIntrons().indexOf(m);
                isComplement = tranClone.complementIntron(intronInd);
            } else if (m instanceof Transcript) {
                isComplement = tranClone.complementSeq();
            }
            if (isComplement == true) {
                transcripts.getList().add(primeTranscriptInd, tranClone);
            } else {
                System.out.println("Sequence already complementary!");
            }
        } catch (CloneNotSupportedException c) {
            c.printStackTrace();
        }
    }

    // EFFECTS: saves the TranscriptList file
    private void saveTranscripts() {
        try {
            writer.open();
            writer.write(transcripts);
            writer.close();
            System.out.println("Saved transcript list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadTranscripts() {
        try {
            transcripts = reader.read();
            System.out.println("Loaded transcript list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
