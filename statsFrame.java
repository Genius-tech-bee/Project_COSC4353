//package editorP;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class statsFrame
{
	JFrame Frame;
	JTextArea textArea;
	
	
	public statsFrame(int [] keyCountArray, String [] keyArray, String nameLabel)
	{
		String textString = "";
		for (int i = 0; i < keyCountArray.length; i++)
		{
			if (keyCountArray[i] > 0)
				textString += keyArray[i] + " " + keyCountArray[i] + "\n";
		}
		
		
		JLabel label = new JLabel(nameLabel);
		
		Frame = new JFrame(nameLabel);
		Frame.setDefaultCloseOperation(Frame.HIDE_ON_CLOSE);
		textArea = new JTextArea(300,500);
		textArea.setEditable(false);
		textArea.append(textString);
		Frame.add(textArea);
		Frame.setSize(400, 600);
		Frame.setLocation(600, 300);
		Frame.setVisible(true);
	}
	
	public void update(int [] keyCountArray, String [] keyArray)
	{
		String textString = "";
		for (int i = 0; i < keyCountArray.length; i++)
		{
			if (keyCountArray[i] > 0)
				textString += keyArray[i] + " " + keyCountArray[i] + "\n";
		}
		
		textArea.setText("");
		textArea.append(textString);
		Frame.add(textArea);
		
	}
	
	public void updateFrame()
	{
		if (Frame != null)
		{Frame.setVisible(true);}
	}
	
}
