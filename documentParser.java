//package editorP;

import java.awt.Font;

public class documentParser 
{
	// Parent Function
	/*
	 * We're recieving the entire document string into this function.
	 */
	
	public void parseAndDetermine(String sourceStringIn)
	{
		
		System.out.println(sourceStringIn);
		
		int counter = 1;
		
		// splits by newline characters
		
		String [] parsedWordsArray = sourceStringIn.split("\\r?\\n");
		
		// Here we want to iterate through the parsed array and determine the type and if it is eligible for font changing
		
		for (String s : parsedWordsArray)
		{
			
			if (s.indexOf("if") > -1 || s.indexOf("else if") > -1 || s.indexOf("for") > -1 || s.indexOf("while") > -1)
			{
				System.out.println("found on line : " + counter);
				System.out.println("Bool operator found - blue operators");
				
				checkBoolean(s);
				
			}
			
			if (s.indexOf('+') > -1 || s.indexOf('-') > -1 || s.indexOf('/') > -1 || s.indexOf("||") > -1)
			{
				System.out.println("found on line : " + counter);
				System.out.println("Arithmetic operator found - red operators");
				
				checkArithmeticOperation(s);
			}
			
			counter++;
		}
		
	}
	
	/*
	 * We need to check if the boolean(s) are
	 * correctly spaced in order to change the font color
	 * of the text-editor
	 * 
	 * We do this by splitting the String line by whitespaces
	 * and iterating through each word and setting it to the correct color
	 * 
	 * NOTE : \\s+ means parse ANY length of white space
	 * 
	 * Font colorFont = new Font("sans-serif", Font.PLAIN, 25);
	 * Color.BLUE Color.RED
	 */
	
	
	private void checkBoolean(String stringArrayPiece)
	{
		String [] tempArray = stringArrayPiece.split("\\s+");
				
		for (int i = 0; i < tempArray.length; ++i)
		{
			System.out.println(tempArray[i]);
			
			String temp =tempArray[i].replaceAll("[()]","");
			
			if (temp.equals("else"))
				System.out.println("Found an else statement");
			else if (temp.equals("if"))
				System.out.println("Found an if statement");
			else if (temp.equals("for"))
				System.out.println("Found a for statement");
			else if (temp.equals("while"))
				System.out.println("Found a while statement");
			
			
		}
	}
	
	/*
	Going to do the same here pretty much, but
	it has to be slightly different because I think
	we have to go through each character
	
	NOTE - for the || operator we have to check the next character to decide whether or not it
	is a valid || operator
	*/
	
	private void checkArithmeticOperation (String stringLinePiece)
	{
		char [] charArray = stringLinePiece.toCharArray();
		
		for (int i = 0; i < charArray.length; ++i)
		{
			if (charArray[i] == '+')
				System.out.println("Found a + sign");
			else if (charArray[i] == '-')
				System.out.println("Found a - sign");
			else if (charArray[i] == '*')
				System.out.println("Found a * sign");
			else if (charArray[i] == '/')
				System.out.println("Found a / sign");
			else if (charArray[i] == '%')
				System.out.println("Found a modulus operator");
			else if (charArray[i] == '|')
			{
				if (i+1 < charArray.length && charArray[i+1] == '|')
					System.out.println("Found a || operator");
			}
			
		}
		
		
	}

			
}
