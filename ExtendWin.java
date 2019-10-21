package editorP;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.UIManager.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.tree.*;
import javax.swing.table.*;
//import editorP.editor2.customDocumentFilter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.util.ArrayList;
import java.util.List;

class ExtendWin extends JFrame implements ActionListener{
	
  /////////////////////////////////////////////////////////////////
  // Field for Statitic Table
  JTable tab;
  JScrollPane scroll_barStatWindow;
  JSplitPane jSplitPaneRight;
  TableModel statTabModel;
  List<String[]> values;
  String[] columns;
  static final String keywords[] = { "abstract", "assert", "boolean",
      "break", "byte", "case", "catch", "char", "class", "const",
      "continue", "default", "do", "double", "else", "extends", "false",
      "final", "finally", "float", "for", "goto", "if", "implements",
      "import", "instanceof", "int", "interface", "long", "native",
      "new", "null", "package", "private", "protected", "public",
      "return", "short", "static", "strictfp", "super", "switch",
      "synchronized", "this", "throw", "throws", "transient", "true",
      "try", "void", "volatile", "while" };
      static int keywordsCount[] = new int [keywords.length];
      static int keywordsCountProject[] = new int [keywords.length];
      static boolean change = false;
  ///////////////////////////////////////////////////////////////////
    
    JTextPane text;
    Document doc;
    JFrame frame;
    JScrollPane scroll_bar;
    JButton b1, b2, b3;
    File O_File;
    String direct = "";
    String project = "";
    String file = "";
    File curFile;

    ExtendWin(File file){
        this.O_File = file;
        this.curFile = file;
        String fileName = O_File.toString();
        int idex = fileName.lastIndexOf("\\");
        String delfileName = fileName.substring(0, idex + 1);
        fileName = fileName.replace(delfileName, "");
        //System.out.println(fileName);

        frame = new JFrame(fileName);

        Font f = new Font("sans-serif", Font.PLAIN, 25);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("FileChooser.listFont",new
        javax.swing.plaf.FontUIResource(f));

        text = new JTextPane();
        ((AbstractDocument) text.getDocument()).setDocumentFilter(new customDocumentFilter());
        doc = text.getDocument();
        

        JMenuBar menubar = new JMenuBar();

        JMenu file_menu = new JMenu("File");
        JMenuItem fm_newFile = new JMenuItem("New File");
        JMenuItem fm_openFile = new JMenuItem("Open File");
        JMenuItem fm_saveFile = new JMenuItem("Save File");
        JMenuItem fm_saveFileAs = new JMenuItem("Save File As...");
        JMenuItem fm_deleteFile = new JMenuItem("Delete File");
        JMenuItem fm_exit = new JMenuItem("Exit");

        fm_newFile.addActionListener(this);
        fm_openFile.addActionListener(this);
        fm_saveFile.addActionListener(this);
        fm_saveFileAs.addActionListener(this);
        fm_deleteFile.addActionListener(this);
        fm_exit.addActionListener(this);

        file_menu.add(fm_newFile);
        file_menu.add(fm_openFile);
        file_menu.add(fm_saveFile);
        file_menu.add(fm_saveFileAs);
        file_menu.add(fm_deleteFile);
        file_menu.addSeparator();
        file_menu.add(fm_exit);

        JMenu edit_menu = new JMenu("Edit");
        JMenuItem em_undo = new JMenuItem("Undo");
        JMenuItem em_redo = new JMenuItem("Redo");
        JMenuItem em_cut = new JMenuItem("Cut");
        JMenuItem em_copy = new JMenuItem("Copy");
        JMenuItem em_paste = new JMenuItem("Paste");
        JMenuItem em_find = new JMenuItem("Find");

        em_undo.addActionListener(this);
        em_redo.addActionListener(this);
        em_cut.addActionListener(this);
        em_copy.addActionListener(this);
        em_paste.addActionListener(this);
        em_find.addActionListener(this);

        edit_menu.add(em_undo);
        edit_menu.add(em_redo);
        edit_menu.add(em_cut);
        edit_menu.add(em_copy);
        edit_menu.add(em_paste);
        edit_menu.add(em_find);

        JMenu proj_menu = new JMenu("Project");
        JMenuItem pm_debug = new JMenuItem("Debug");
        JMenuItem pm_run = new JMenuItem("Run");

        pm_debug.addActionListener(this);
        pm_run.addActionListener(this);

        proj_menu.add(pm_debug);
        proj_menu.add(pm_run);

        menubar.add(file_menu);
        menubar.add(edit_menu);
        menubar.add(proj_menu);

        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        scroll_bar = new JScrollPane(text);
        //Create a stat table and attach to splitpaneright
        statTableCreate();
        frame.setJMenuBar(menubar);
        frame.setLayout(new BorderLayout());
        
        frame.getContentPane().add(jSplitPaneRight,BorderLayout.CENTER);
        frame.setSize(1000,800);
        frame.setVisible(true);

        text.setFont(text.getFont().deriveFont(25f));
       
        try {
            String line ="", all_line = "";

            FileReader fr = new FileReader(O_File);
            BufferedReader br = new BufferedReader(fr);

            all_line = br.readLine();

            while ((line = br.readLine()) != null){
                all_line = all_line + "\n" + line;
            }
            br.close();
            text.setText(all_line);
            scroll_bar.setViewportView(text);
            
            String dir = O_File.getAbsolutePath();
            direct = dir;
        }   
        catch (Exception evt){
            JOptionPane.showMessageDialog(frame, evt.getMessage());
        }

        //frame.addWindowListener(new WindowCloser());
    }

    public void statTableCreate(){
        columns = new String[2];
        values = new ArrayList<String[]>();

        columns[0] = "Keywords";
		columns[1] = "Frequency";
        //keywordsCount, keywords
           // Dimension minimumSize = new Dimension(50, 50);
        // String[] []tableData = {{"If","4" },{"Else", "3"}};
        // String [] collumnsName = {"keywrords", "Count"};
        for (int i = 0 ; i < keywordsCount.length;i++){
            if( keywordsCount[i] >0){
            values.add(new String[] {keywords[i], String.valueOf(keywordsCount[i])} );
            }
        }
        statTabModel = new DefaultTableModel(values.toArray(new Object[][] {}) , columns);
        tab = new JTable(statTabModel);
        tab.setFont(new Font("Serif", Font.PLAIN, 18));
        scroll_barStatWindow = new JScrollPane(tab);
        //tab.setMaximumSize(minimumSize);
        jSplitPaneRight = new JSplitPane(SwingConstants.VERTICAL, scroll_bar,scroll_barStatWindow);
        jSplitPaneRight.setOneTouchExpandable(true);
        jSplitPaneRight.setDividerLocation(700);

    }
    public void updateStat(int [] keyCountArray, String [] keyArray){
        values = new ArrayList<String[]>();

        for (int i = 0 ; i < keyCountArray.length;i++){
            if( keyCountArray[i] >0){
            values.add(new String[] {keyArray[i], String.valueOf(keyCountArray[i])} );
            }
        }

        statTabModel = new DefaultTableModel(values.toArray(new Object[][] {}) , columns);
        tab.setModel(statTabModel);
    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();

        if (s.equals("Cut")){
            text.cut();
        }
        else if (s.equals("Copy")){
            text.copy();
        }
        else if (s.equals("Paste")){
            text.paste();
        }
        else if (s.equals("Exit")){
            frame.dispose();
            //System.exit(0);
        }
        else if (s.equals("Open File")){

            JFileChooser choose_file = new JFileChooser(direct);
            choose_file.setPreferredSize(new Dimension(900,700));
            int r = choose_file.showOpenDialog(null);
            String dir = "";
            if (r == JFileChooser.APPROVE_OPTION){
                File file = new File(choose_file.getSelectedFile().getAbsolutePath());

                try {
                    String line ="", all_line = "";

                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);

                    all_line = br.readLine();

                    while ((line = br.readLine()) != null){
                        all_line = all_line + "\n" + line;
                    }
                    br.close();
                    text.setText(all_line);
                    scroll_bar.setViewportView(text);
                    dir = file.getAbsolutePath();
                    curFile = file;

                    String fileName = file.toString();
                    int idex = fileName.lastIndexOf("\\");
                    String delfileName = fileName.substring(0, idex + 1);
                    fileName = fileName.replace(delfileName, "");

                    frame.setTitle(fileName);
                }   
                catch (Exception evt){
                    //JOptionPane.showMessageDialog(frame, evt.getMessage());
                }

                direct = dir;
            }
            else
                JOptionPane.showMessageDialog(frame, "No File Is Opened");

        }
        else if (s.equals("Save File")) {

            if (curFile != null)
            {
                File file = new File(curFile.getAbsolutePath());
                try{
                    FileWriter wr = new FileWriter(file, false);
                    BufferedWriter w = new BufferedWriter(wr);

                    w.write(text.getText());
                    w.flush();
                    w.close();

                    direct = file.toString();

                    curFile = file;
                }
                catch (Exception evt){
                    //JOptionPane.showMessageDialog(frame, evt.getMessage()); 
                } 
            }
            else
                JOptionPane.showMessageDialog(frame, "Unable to Save File");                  
            
        }
        else if (s.equals("Save File As...")) {

            JFileChooser choose_file = new JFileChooser(direct);
            choose_file.setPreferredSize(new Dimension(900,700));
            int r = choose_file.showSaveDialog(null);
            String dir = "";
            if (r == JFileChooser.APPROVE_OPTION){
                File file = new File(choose_file.getSelectedFile().getAbsolutePath());
                try{
                    FileWriter wr = new FileWriter(file, false);
                    BufferedWriter w = new BufferedWriter(wr);

                    w.write(text.getText());
                    w.flush();
                    w.close();
                    dir = file.getAbsolutePath();

                    direct = file.toString();

                    curFile = file;

                    String fileName = file.toString();
                    int idex = fileName.lastIndexOf("\\");
                    String delfileName = fileName.substring(0, idex + 1);
                    fileName = fileName.replace(delfileName, "");

                    frame.setTitle(fileName);
                }
                catch (Exception evt){
                    //JOptionPane.showMessageDialog(frame, evt.getMessage()); 
                }
            }
            else
                JOptionPane.showMessageDialog(frame, "No File Is Saved");

        }
        else if (s.equals("Delete File")) {
            
            if (curFile.delete())
            {
                text.setText(""); 
                JOptionPane.showMessageDialog(frame, "File Deleted");
                curFile = null;
                frame.setTitle("");
            }
            else
            {
                JOptionPane.showMessageDialog(frame, "Unable to Delete File");
            }
        }
        else if (s.equals("New File")){
            
            JFileChooser choose_file = new JFileChooser(direct);
            choose_file.setPreferredSize(new Dimension(900,700));
            int r = choose_file.showSaveDialog(null);
            String dir = "";
            if (r == JFileChooser.APPROVE_OPTION){
                File file = new File(choose_file.getSelectedFile().getAbsolutePath());
                try{
                    FileWriter wr = new FileWriter(file, false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.flush();
                    w.close();
                    dir = file.getAbsolutePath();

                    direct = file.toString();

                    curFile = file;

                    text.setText("");     
                    scroll_bar.setViewportView(text);

                    String fileName = file.toString();
                    int idex = fileName.lastIndexOf("\\");
                    String delfileName = fileName.substring(0, idex + 1);
                    fileName = fileName.replace(delfileName, "");

                    frame.setTitle(fileName);
                }
                catch (Exception evt){
                    JOptionPane.showMessageDialog(frame, evt.getMessage()); 
                }
            }
            else
                JOptionPane.showMessageDialog(frame, "Unable to Create New File");
            
        }
        else if (s.equals("Debug")){

            try{
                int file_i = direct.lastIndexOf("\\") + 1;
                String file_name = direct.substring(file_i);
                String file_dir = direct.substring(0, file_i);
                String command = String.format("cmd /c start cmd.exe /K \"pushd %s && javac %s\"", file_dir, file_name);
                Runtime.getRuntime().exec(command);
            }
            catch (Exception evt){ 
                evt.printStackTrace(); 
            } 
            
        }
        else if (s.equals("Run")){
            try{
                int file_ie = direct.indexOf(".java");
                int file_i = direct.lastIndexOf("\\") + 1;
                String file_name = direct.substring(file_i, file_ie);
                String file_dir = direct.substring(0, file_i);
                String command = String.format("cmd /c start cmd.exe /K \"pushd %s && java %s\"", file_dir, file_name);
                Runtime.getRuntime().exec(command);
            }
            catch (Exception evt){ 
                evt.printStackTrace(); 
            } 
        }
    }
    
  
   
    
    public void SaveFile(){
        File file = O_File;
        try{
            FileWriter wr = new FileWriter(file, false);
            BufferedWriter w = new BufferedWriter(wr);

            w.write(text.getText());
            w.flush();
            w.close();
        }
        catch (Exception evt){
            JOptionPane.showMessageDialog(frame, evt.getMessage()); 
        }
    }
    final class customDocumentFilter extends DocumentFilter
    {
    	
    	private final StyledDocument styledDocument = text.getStyledDocument();
    	private final StyleContext styleContext = StyleContext.getDefaultStyleContext();
    	
    	private final AttributeSet booleanAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    	private final AttributeSet arithmeticAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.RED);
    	private final AttributeSet quoteAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(0,102,0));
    	private final AttributeSet blackAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    	
    	Pattern patternBool = buildPatternBool();
    	Pattern patternQuotes = buildPatternQuotes();
    	
    	@Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
            super.insertString(fb, offset, text, attributeSet);

            handleTextChanged();
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);

            handleTextChanged();
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
            super.replace(fb, offset, length, text, attributeSet);

            handleTextChanged();
        }
        
        private Pattern buildPatternBool()
        {
        	Pattern regexBool = Pattern.compile("\\b(?:if|else|while|for)\\b");
        	
        	return regexBool;
        }
        
        private Pattern buildPatternQuotes()
        {
        	Pattern regexQuotes = Pattern.compile("\"([^\"]*)\"");
        	
        	return regexQuotes;
        }
        
        private void handleTextChanged()
        {
        	SwingUtilities.invokeLater(new Runnable() {
        		@Override
        		public void run() 
        		{updateTextStyles();}
        	});
        }
        
        private void updateTextStyles() 
        {
        	// Clears existing styles
        	styledDocument.setCharacterAttributes(0, text.getText().length(), blackAttributeSet, true);
        	
            String temp = text.getText();
            temp = temp.replace("\n", " ");
        	char [] tempCharArray = temp.toCharArray();
        	String [] tempStringArray = temp.split("\\s+");
        	
        	// Look for keywords within the textpane
        	lookforKeywords(tempStringArray);
        	
        	// Look for matching substrings and highlight them
        	Matcher matcherBool = patternBool.matcher(temp);
        	Matcher matcherQuotes = patternQuotes.matcher(temp);
        	
        	while (matcherBool.find())
        		styledDocument.setCharacterAttributes(matcherBool.start(), matcherBool.end()- matcherBool.start(), booleanAttributeSet, false);
        	
        	while (matcherQuotes.find())
        		styledDocument.setCharacterAttributes(matcherQuotes.start(), matcherQuotes.end() - matcherQuotes.start(), quoteAttributeSet, false);;
        	
        	
        	for (int i = 0; i < tempCharArray.length; i++)
        	{
        		switch(tempCharArray[i])
        		{
        		case '+':
        		case '-':
        		case '*':
        		case '/':
        		case '%':
        			//System.out.println(i);
        			styledDocument.setCharacterAttributes(i ,1, arithmeticAttributeSet, false);
        			
        		}
        		
        		// If conditional to check for || and && characters - we need to check for an extra character - this handles it
        		
        		
        		if (tempCharArray[i] == '|' || tempCharArray[i] == '&')
        		{
        			if (i+1 < tempCharArray.length)
        				
        				switch(tempCharArray[i+1])
        				{
        				case '|':
        				case '&':
        					styledDocument.setCharacterAttributes(i, 2, arithmeticAttributeSet, false);
        				}
        		}
        		
        	}
        	
        	
        }
        
        private void lookforKeywords(String [] text)
        {
        	int [] helpArray = new int [keywords.length];
        	Arrays.fill(helpArray, 0);
        	
        	// Iterate through the string array and check with static keywords array through binary Search
        	for (String word : text)
        	{
                word = word.toLowerCase();
        		int index = Arrays.binarySearch(keywords, word);
        		if (index >= 0) 
        		{
        			helpArray[index] += 1;
        		}
        		
        	}
        	
        	// Use library to compare Arrays - if we detect a change then we set static bool to true, else it is false
        	if (Arrays.equals(keywordsCount, helpArray) == true){change = false;}
        	else{change = true;}
        	
        	keywordsCount = helpArray;
        	
        	// If we detect a change then we should update the existing keywords count JFrame
        	if (change == true)
        	{updateStat(keywordsCount,keywords);}
        	
        }
    }
}