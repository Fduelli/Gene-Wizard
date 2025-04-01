
/***************************************************************************************
 * Title: ListDemo source code
 * Author: Oracle
 * Date: 11/22/24
 * Availability: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
 *
 ***************************************************************************************/

package ui;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import model.*;

//Creates a panel displaying a list of modifiable sequences 
//and creates buttons for adding, viewing, or modifying sequences.
public class SequenceMenu extends JPanel implements ListSelectionListener {
    private MainFrame mainFrame;
    private JList<ModifiableSequence> list;
    private DefaultListModel<ModifiableSequence> listModel;
    private java.util.List<ModifiableSequence> seqList;

    private static final String reverseString = "Reverse Sequence";
    private static final String complementString = "Complement Sequence";
    private JButton viewButton;
    private JButton complementButton;
    private JButton reverseButton;
    private JButton newGeneButton;
    private final JFileChooser fc = new JFileChooser();

    // EFFECTS: creates a panel with a scroll list of all genes in the list,
    // and buttons for modifying the list.
    public SequenceMenu(MainFrame myMainFrame, java.util.List<ModifiableSequence> tranList) {
        super(new BorderLayout());
        mainFrame = myMainFrame;
        this.seqList = tranList;
        listModel = new DefaultListModel<ModifiableSequence>();
        if (!tranList.isEmpty()) {
            listModel = toListModel(listModel);
        }
        JScrollPane listScrollPane = createScrollPaneWithList();
        createButtons();
        JPanel buttonPane = createButtonPane();
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: creates a list of transcripts from a list of ModifiableSequence
    // objects
    public java.util.List<Transcript> makeListOfTranscript(java.util.List<ModifiableSequence> tranList) {
        ArrayList<Transcript> temp = new ArrayList<Transcript>();
        for (ModifiableSequence m : tranList) {
            temp.add((Transcript) m);
        }
        return temp;
    }

    // MODIFIES: this
    // EFFECTS: creates a scroll pane with the provided gene list.
    private JScrollPane createScrollPaneWithList() {
        list = new JList<ModifiableSequence>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.addMouseListener(new DetectEnterTranscript());
        return new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons for adding, viewing, and modifying sequences
    private void createButtons() {
        complementButton = new JButton(complementString);
        complementButton.setActionCommand(reverseString);
        complementButton.addActionListener(new ModifyListener(complementButton));

        viewButton = new JButton("View Sequence");
        viewButton.setActionCommand("View Sequence");
        viewButton.addActionListener(new ViewListener());

        reverseButton = new JButton(reverseString);
        reverseButton.setActionCommand(reverseString);
        reverseButton.addActionListener(new ModifyListener(reverseButton));

        newGeneButton = new JButton("Add a new gene");
        newGeneButton.setActionCommand("Add a new Gene Transcript");
        newGeneButton.addActionListener(new AddListener());
    }

    // MODIFIES: this
    // EFFECTS: Creates the pane for the layout of the buttons in the sequence
    // display.
    private JPanel createButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        if (!seqList.isEmpty() && (seqList.get(0) instanceof Exon || seqList.get(0) instanceof Intron)) {
            JButton backButton = new JButton("Go back to list of Genes");
            backButton.setActionCommand("Return to the Gene list");
            backButton.addActionListener(new BackListener());
            buttonPane.add(backButton);
        }
        buttonPane.add(viewButton);
        if ((!seqList.isEmpty() && !(seqList.get(0) instanceof Exon || seqList.get(0) instanceof Intron))
                || seqList.isEmpty()) {
            buttonPane.add(newGeneButton);
        }
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(complementButton);
        buttonPane.add(reverseButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return buttonPane;
    }

    // Listens for if the back button was pressed and returns to the menu of genes.
    class BackListener implements ActionListener {

        // MODIFIES: mainFrame
        // EFFECTS: returns to the previous menu displayed being the full gene list.
        public void actionPerformed(ActionEvent e) {
            mainFrame.toSequenceMenu(mainFrame.getTranscriptMenu());
        }
    }

    // Detects if an item in the list was double clicked,
    // and displays the exon intron list of selected gene transcript.
    class DetectEnterTranscript extends MouseAdapter {

        // MODIFIES: mainFrame
        // EFFECTS: if double clicked, opens a new sequenceMenu with a list of the exons
        // and introns in the gene.
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {
                mainFrame.openExonsIntronsList((Transcript) list.getSelectedValue());
            }
        }
    }

    // Detects if the add new gene button was pressed, opens a file select window to
    // choose the desired file,
    // And adds the gene file to the list.
    class AddListener implements ActionListener {

        // MODIFIES: this, mainFrame
        // EEFFECTS: opens a file select window and adds the selected file to the
        // transcript list.
        public void actionPerformed(ActionEvent e) {
            fc.setCurrentDirectory(new File("gene_files"));
            int returnVal = fc.showOpenDialog(SequenceMenu.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                    Transcript t = new Transcript();
                    t.readFile(fileReader);
                    seqList.add(t);
                    listModel.addElement(t);
                    mainFrame.getTranscriptList().addTranscript(t);
                } catch (IOException i) {
                    System.out.println("could not read selected file!");
                }
            }
        }
    }

    // Detects if the view gene button was pressed and opens a new view panel.
    class ViewListener implements ActionListener {

        // MODIFIES: mainFrame
        // EFFECTS: opens a new view panel for the selected transcript.
        public void actionPerformed(ActionEvent e) {
            ModifiableSequence m = list.getSelectedValue();
            mainFrame.changePaneToView(m);
        }
    }

    // Detects if a modify button was pressed and modifies transcripts accordingly.
    class ModifyListener implements ActionListener {
        private JButton button;

        // EFFECTS: assigns this.button to the button
        public ModifyListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: performs the specified action (complement or reverse) to the
        // selected gene sequence.
        public void actionPerformed(ActionEvent e) {
            ModifiableSequence m = list.getSelectedValue();
            if (button == complementButton) {
                m.complementSeq();
            } else {
                m.reverseSeq();
            }
            int index = list.getSelectedIndex(); // get selected index
            if (index == -1) { // no selection, so insert at beginning
                index = 0;
            } else { // add after the selected item
                index++;
            }
            // Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
            SequenceMenu.this.repaint();
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is no value selected in the list of transcripts,
    // disable the buttons that require a selected transcript
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                viewButton.setEnabled(false);
                complementButton.setEnabled(false);
                reverseButton.setEnabled(false);
            } else {
                viewButton.setEnabled(true);
                complementButton.setEnabled(true);
                reverseButton.setEnabled(true);
            }
        }
    }

    public java.util.List<ModifiableSequence> getSeqList() {
        return seqList;
    }

    // MODIFIES: this
    // EFFECTS: saves the contents of the list of modifiable sequences into a
    // ListModel.
    private DefaultListModel<ModifiableSequence> toListModel(DefaultListModel<ModifiableSequence> listModel) {
        for (ModifiableSequence m : seqList) {
            listModel.addElement(m);
        }
        return listModel;
    }

}
