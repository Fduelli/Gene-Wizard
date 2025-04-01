/***************************************************************************************
 * Title: startMenu Demo source code
 * Author: Oracle
 * Date: 11/22/24
 * Availability: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
 *
 ***************************************************************************************/

package ui;

import java.io.*;

import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import persistence.*;

import model.*;

// Creates a start menu for the GUI with options for saving, loading, and creating new gene lists.
public class StartMenu implements ActionListener {
    private static final String JSON_STORE = "./data/transcriptList.json";
    private final JsonReader reader = new JsonReader(JSON_STORE);
    private final JsonWriter writer = new JsonWriter(JSON_STORE);
    JTextArea output;
    String newline = "\n";
    JScrollPane scrollPane;
    MainFrame mainFrame;

    // EFFECTS: creates a menu bar upon opening the window
    // provides options for adding new files, opening saved lists, and saving lists.
    public JMenuBar createMenuBar(MainFrame myMainFrame) {
        mainFrame = myMainFrame;
        JMenuBar menuBar;
        JMenu menu;
        menuBar = new JMenuBar();
        menu = new JMenu("Main Menu");
        createMenuItem(menu, "Save Gene List", KeyEvent.VK_S);
        createMenuItem(menu, "Load Gene List", KeyEvent.VK_L);
        createMenuItem(menu, "New Gene List", KeyEvent.VK_N);
        menuBar.add(menu);
        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: creates a new text-based menu item with a specific text and mnemonic
    // adds the menu item to menu.
    private void createMenuItem(JMenu menu, String text, int hotkeyNum) {
        JMenuItem temp = new JMenuItem(text, hotkeyNum);
        temp.setAccelerator(KeyStroke.getKeyStroke(
                hotkeyNum, ActionEvent.CTRL_MASK));
        temp.addActionListener(this);
        menu.add(temp);
    }

    // MODIFIES: mainFrame
    // EFFECTS: Detects which menu item was clicked,
    // if creat new gene list button chos
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        if (source.getText().contains("New")) {
            mainFrame.openTranscriptList(new TranscriptList());
        } else if (source.getText().contains("Load")) {
            readFile();
        } else if (source.getText().contains("Save")) {
            writeFile();
        }
    }

    // MODIFIES: mainFrame
    // EFFECTS: reads a file from a saved JSON file.
    private void readFile() {
        try {
            mainFrame.openTranscriptList(reader.read());
        } catch (IOException i) {
            System.out.println("Cant read file");
        }
    }

    // MODIFIES: mainFrame
    // EFFECTS: writes the information from this gene list to a saved JSON file.
    private void writeFile() {
        try {
            writer.open();
            writer.write((TranscriptList) mainFrame.getTranscriptList());
            writer.close();
        } catch (IOException i) {
            System.out.println("Can't write to file");
        }
    }
}