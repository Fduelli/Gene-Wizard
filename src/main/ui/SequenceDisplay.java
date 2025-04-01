package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Creates a panel for displaying the full sequence of a particular modifiable sequence
public class SequenceDisplay extends JPanel implements ActionListener {

    ModifiableSequence seq;
    JTextArea output;
    JScrollPane scrollPane;
    JButton backButton;
    MainFrame mainFrame;
    
    //EFFECTS: assigns the sequence being viewed to m
    //         and assigns the mainFram to myMainFrame.
    SequenceDisplay(MainFrame myMainFrame, ModifiableSequence m) {
        seq = m;
        mainFrame = myMainFrame;
    }

    //MODIFIES: this
    //EFFECTS: creates the panel containing a scroll pane with the view of the full gene sequence,
    //         and a back button to return to the full gene list.
    public JPanel createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        backButton = new JButton("Go Back");
        backButton.addActionListener(this);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        output.append(seq.getSequence());
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(backButton);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        return contentPane;
    }

    //MODIFIES: mainFrame
    //EFFECTS: returns to the full gene sequence menu.
    public void actionPerformed(ActionEvent e) {
        mainFrame.toSequenceMenu(mainFrame.getTranscriptMenu());
    }

}
