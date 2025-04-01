# Gene Wizard 

## Look through a file for a gene, label and classify its exons and introns for modification.

**Project Functions**:
- *What will this application do?*  
    - This application will accept a FASTA file that has all of the information of a specific gene transcript then store the information for its' exons and introns. Once the information is stored, the program will then allow you to modify either the whole exon or intron, or a specific part of it to fit different requirements (i.e complimentary, reverse, or reverse compliment of the sequence).  
    - This application will also format the gene sequence into a file that contains the necessary information for other programs to use, and export it onto your computer.  
- *Who will use it*  
    - Although there are many programs online for the download of gene sequence files, the programs that allow for modification or export are decentralized and take a lot of time and reformatting of the sequences in order to be useful. This program will allow genetics researchers to change their information all in one centralized place, and consolidate the many different formats into one that can be used for a variety of different programs.  
- *Why is the project of interest?*  
    - I have been working in the bioinformatics field for about two years and the biggest time waster in testing and creating programs for genetic analysis is processing the files, and modifying the sequences to fit the exact requirements of each different program I create. So this program will allow me to save a lot of time spent manually reformatting information.

**User Stories**
- "I want to be able to add a gene to my gene list and access the exons, introns, and raw sequences."
- "I want to be able to view a list of exons and introns in a given gene."
- "I want to be able to modify my gene sequences to fit the necessary orientation or complimentarity."
- "I want to be able to view the list of genes I already have saved in my program."  
- "As a user, I want to be given the option to save my transcripts and transcript modifications to a file."  
- "As a user, I want to be given the option to load my previous transcripts and modifications from a file, or start a new instance of the program."   

**Instructions for End User**  
*How to add a gene to the transcript list*  
- First, run the project and you will see the "Gene Wizard" mascot upon opening, to create a new list of genes, select the "Main Menu" item on the menu bar and click on "New Gene List".  
- Next, there will be a blank scroll list displayed with the buttons, "View Sequence", "Add a new gene", "Complement Sequence", and "Reverse Sequence". Since you have no sequences added, select "Add a new gene" first, and a file selection window will appear.  
- Select the desired file from the initial directory opened when the file select screen appears, and press the "Open" button. Now you have added your first gene to the list!  
  
*You can find the subList of exons and introns for each gene added by (first required action related to adding Xs to a Y) :*   
- when you have your gene added, simply double click on the desired gene, and the list of exons and introns will appear!  

*you can view the sequences of the genomic sequence of the transcript, and the individual exons and introns by (second required action related to adding Xs to a Y):*  
- Navigate to the sequence you desire to view, and click on the "View Sequence" button to view the entire sequence of your desired transcript!  

*You can locate my visual component by:*  
- It is the first thing you see when opening the app! meant to be a splash screen before you select to load a list, or create a new one.  

*You can save the state of my application by:*  
-  click into the menu bar, and select "Save Gene List", this will save your current list of gene transcripts as a JSON.  

*You can reload the state of my application by:*  
- click into the menu bar, and select "Load Gene List", this will load the most recent gene list saved.  

*Additional functionality*
- You can modify any sequence you like i.e make it reversed or complementaary by selecting the item you want to change on the scroll list, and clicking either the "Complement Sequence", or "Reverse Sequence" buttons on the button panel.  

**Phase 4: Task 3**  
After creating my UML class diagram for my project, it is evident that some refactoring is needed in my project, particularly in the GUI and model stages.  
In my GUI stage, I would change my SequenceMenu and SequenceDisplay classes to directly edit an instance of my TranscriptList object rather than secondary lists created off of that, this would allow for less repetition needed in upodatign each list every time a different change is made. Additionally, I would refactor my MainFrame so I do not have a separate istance of a frame within my frame, which would create less confusion in scope of whether I am modifying my MainFrame frame or the frame field within it.  
For my model class, the abstraction of ModifiableSequence was I think a good idea, with imperfect execution. I would refactor my code to use another abstract class specifically for my exons and introns, as they are practically identical and use the exact same methods, this would make it easier to differentiate between the exon intron objects and transcript objects, in turn making the creation of my GUI more clean and needing a lot less repetitive code in my model folder.




