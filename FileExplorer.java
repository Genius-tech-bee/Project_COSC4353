//package editorP;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.UIManager.*;
import javax.swing.JFrame;
import javax.swing.text.*;
import javax.swing.tree.*;
import java.util.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

class FileExplorer extends JPanel implements TreeSelectionListener {
    JTree fileTree;
    DefaultMutableTreeNode root;
    JScrollPane pane;
    File dir;
    ArrayList<ExtendWin> hashWin = new ArrayList<ExtendWin>();
    public FileExplorer() {
      root = new DefaultMutableTreeNode("root", true);
      getList(root, new File(""));
      setLayout(new BorderLayout());

      fileTree = new JTree(root);
      fileTree.setRootVisible(false);
      final Font currentFont = fileTree.getFont();
      final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 12);
      fileTree.setFont(bigFont);
      pane = new JScrollPane((JTree)fileTree);
      add(pane,"Center");
      //Where the tree is initialized:
        fileTree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        fileTree.addTreeSelectionListener(this);

    }

    public void valueChanged(TreeSelectionEvent e) {
    //Returns the last path element of the selection.
    //This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        fileTree.getLastSelectedPathComponent();
                        
        //System.out.println(node);

        TreePath tp = e.getNewLeadSelectionPath();

      //Nothing is selected
        if (node == null)     
            return;
        
        String direct = tp.toString();
        String d = direct.replace("[root, ", "");
        d = d.replace(", ",",");
        d = d.replace("]","");
        d = d.replace(",","\\");
        int subIn = d.indexOf("\\");
        String delSub = d.substring(0, subIn);
        d = d.replace(delSub, "");
        String temp = dir.toString();
        temp = temp + d;

        if (tp.toString().endsWith(".txt]") || tp.toString().endsWith(".java]")) {
            JFrame frame = new JFrame("Path Selected");
            File redir = new File(temp);
            ExtendWin window = new ExtendWin(redir);
            hashWin.add(window);
                //System.out.println(redir);
                //JOptionPane.showMessageDialog(frame, "TEXT FILE");

        } else {
                JFrame frame = new JFrame("Path Selected");
                JOptionPane.showMessageDialog(frame, node.getPath());
            
        }
        fileTree.clearSelection();
    }
  
    public Dimension getPreferredSize(){
      return new Dimension(250, 320);
    }
  
    public void getList(DefaultMutableTreeNode node, File f) {
       if(!f.isDirectory()) {
           // We keep only JAVA source file for display in this HowTo
           if (f.getName().endsWith("java") || f.getName().endsWith("txt") || f.getName().endsWith("class")) {
              //System.out.println("FILE  -  " + f.getName());
              DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
              node.add(child);
            }
        }
       else {
           //System.out.println("DIRECTORY  -  " + f.getName());
           DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
           node.add(child);
           File fList[] = f.listFiles();
           for(int i = 0; i  < fList.length; i++)
               getList(child, fList[i]);
        }
    }

    public void updateList(File file)
    {
        dir = file;
        removeAll();
        root = new DefaultMutableTreeNode("root", true);
        getList(root, file);
        setLayout(new BorderLayout());

        fileTree = new JTree(root);
        fileTree.setRootVisible(false);
        final Font currentFont = fileTree.getFont();
        final Font bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 12);
        fileTree.setFont(bigFont);
        pane = new JScrollPane((JTree)fileTree);
        add(pane,"Center"); 
        fileTree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        fileTree.addTreeSelectionListener(this);
        revalidate();
        repaint(); 
    }

    public ArrayList<ExtendWin> getHash(){
        return hashWin;
    }
}