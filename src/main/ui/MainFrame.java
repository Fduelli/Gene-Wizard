package ui;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import model.*;
import model.Event;

// Creates the main JFrame that displays each of the different panels in the application
public class MainFrame extends JFrame implements WindowListener {
    private final JFrame frame = new JFrame("Gene Wizard");
    private JLabel photoLabel;
    private SequenceMenu sequenceMenuTranscripts;
    private SequenceMenu sequenceMenuExonsIntrons;
    private TranscriptList tl;

    // EFFECTS: run the createAndShowGUI method.
    public MainFrame() {
        createAndShowGUI();
    }

    // MODIFIES: this
    // EFFECTS: creates the window on the desktop with only the start menu
    // and a splash screen image displayed.
    void createAndShowGUI() {
        ImageIcon imageIcon = new ImageIcon("images/GeneWiz.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(480, 480, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        photoLabel = new JLabel(imageIcon);
        // Create and set up the window.
        frame.addWindowListener(this);

        // Create and set up the content pane.
        StartMenu menu = new StartMenu();
        frame.setIconImage(newimg);
        frame.setJMenuBar(menu.createMenuBar(this));
        frame.setContentPane(photoLabel);

        // Display the window.
        frame.setSize(450, 260);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: changes the current pane being viewed to a view SequenceDisplay
    // pane.
    void changePaneToView(ModifiableSequence m) {
        this.dispose();
        frame.setContentPane(new SequenceDisplay(this, m).createContentPane());
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: opens the specified transcript list and displays it in a
    // SequenceMenu pane.
    void openTranscriptList(TranscriptList tranList) {
        tl = tranList;
        java.util.List<Transcript> tranSequences = tranList.getList();
        java.util.List<ModifiableSequence> modSequences = new ArrayList<>(tranSequences);
        sequenceMenuTranscripts = new SequenceMenu(this, modSequences);
        toSequenceMenu(sequenceMenuTranscripts);
    }

    // MODIFIES: this
    // EFFECTS: displays the provided SequenceMenu pane and makes it visible.
    void toSequenceMenu(SequenceMenu s) {
        JComponent newContentPane = s;
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates a SequenceMenu object for the list of exons and introns in
    // the provided transcript.
    void openExonsIntronsList(Transcript t) {
        sequenceMenuExonsIntrons = new SequenceMenu(this, t.getExIntList());
        toSequenceMenu(sequenceMenuExonsIntrons);
    }

    public void setVisible(boolean tf) {
        frame.setVisible(tf);
    }

    SequenceMenu getTranscriptMenu() {
        return sequenceMenuTranscripts;
    }

    public TranscriptList getTranscriptList() {
        return tl;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    // EFFECTS: prints out all logged events upon window closing
    @Override
    public void windowClosing(WindowEvent e) {
        EventLog log = EventLog.getInstance();
        System.out.println("Event log: ");
        for (Event next : log) {
            System.out.println(next.toString() + "\n\n");
        }
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
