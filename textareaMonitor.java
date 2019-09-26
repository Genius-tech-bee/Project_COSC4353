//package editorP;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class textareaMonitor implements DocumentListener
{
	String newline = "\n";

	/* Want to check if the user enters in an operator, boolean, string
	 * then change it to respective colors.
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	
	@Override
	/* Only works when other attributes are changed (font, size, but not text)
	 * verbatim from SO
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent docEvent) 
	{
	}

	// Need to worry about the bottom two events - if the user
	// tries to update something then we need to catch things.
	
	@Override
	public void insertUpdate(DocumentEvent docEvent)
	{
		Document source = docEvent.getDocument();
		// parseAndChangeFont(docEvent);
	}

	@Override
	public void removeUpdate(DocumentEvent docEvent) 
	{
		Document source = docEvent.getDocument();
		// parseAndChangeFont(docEvent);
	}
	
	// Parses the new input/edit to see if it one of the target string(s)/char(s)
	
	public void parseAndChangeFont (DocumentEvent docEvent)
	{
		Document source = docEvent.getDocument();
		
		try
		{
			String sourceText = source.getText(0, source.getLength());
			documentParser parser = new documentParser();
			// parser.parseAndDetermine(sourceText);
		}
		
		catch (BadLocationException badLocation)
		{
			System.out.println("Contents : unknown");
			System.exit(1);
		}
		
		catch (Exception e)
		{
			System.out.println("Unknown error caught!");
			System.exit(1);
		}
		
	}
}
	
