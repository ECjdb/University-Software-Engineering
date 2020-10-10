package business;

//40430615
//Joel Degner-Budd
//Software Engineering

import java.util.ArrayList;

public class CheckMsgHeader
{
	public CheckMsgHeader()
	{
		//blank constructor
	}
	
	public String checkMessage(String msgHead, String msgBody)
	{
		//Checks if either String is empty
		if(msgHead.isEmpty() || msgBody.isEmpty())
		{
			return "Please enter a message header and message body";
		}
		
		//checks and removes whitespace from header
		msgHead = removeHeaderWhiteSpace(msgHead);
		
		//Checks length of String is 10 characters
		if(msgHead.length() != 10)
		{
			return "Header length must be exactly 10 characters long";
		}
		//else correct number of characters
		else if(msgHead.length() == 10)
		{
			String firstChar = msgHead.substring(0,1); //substring first character - e.g. S, E or T etc.
			String numStr = msgHead.substring(1 , msgHead.length()); //substring the 9 characters after the first
			boolean valHeader; //checks if header is valid
			
			//check to see if the header is valid and store return value in valHeader
			valHeader = checkMsgHeader(firstChar,numStr);
			
			//if true then header is a valid type
			if(valHeader == true)
			{
				msgHead = msgHead.toUpperCase();//ensure casing is upper case
				CheckMsgBody bodyChk = new CheckMsgBody();//create msgBody object
				String errorChecker = "";//string used to return error messages to user, and message "success"
				
				//if header is SMS message
				if(msgHead.contains("S"))
				{
					errorChecker = bodyChk.checkSMSBody(msgHead, msgBody);//check the message body
					return errorChecker;//return message to user
				}
				//if header is Email message
				else if(msgHead.contains("E"))
				{
					errorChecker = bodyChk.checkEmailBody(msgHead, msgBody);//check the message body
					return errorChecker;//return message to user
				}
				//if header is Tweet message
				else if(msgHead.contains("T"))
				{
					errorChecker = bodyChk.checkTweetBody(msgHead, msgBody);//check the message body
					return errorChecker;//return message to user
				}
			}
			//else header is not valid
			else
			{
				return "not a valid header e.g 'S123456789'";
			}
		}

		return null; //default return
	}
	
	//Method - removes white space characters from messageHeader
	private String removeHeaderWhiteSpace(String msgHead)
	{
		msgHead = msgHead.trim();
		msgHead = msgHead.replaceAll("\\s", "");
		return msgHead;
	}
	
	//Method - checks first character of message header, checks characters after first are all numbers
	private boolean checkMsgHeader(String firstChar, String numStr)
	{
		//if the first character is equal to a valid message character e.g. S, E, or T
		if((firstChar.equalsIgnoreCase("s") || firstChar.equalsIgnoreCase("e")||firstChar.equalsIgnoreCase("t")))
		{
			int limit1 = 48;   //ascii code for '0'
		    int limit2 = 57;   //ascii code for '9'
		    
		    //checks to see the 9 characters following the first are numbers
		    for(int x=0; x<numStr.length(); x++)
		    {
		       char letter = numStr.charAt(x);
		       int number = (int)letter;
		       
		       //if a character is not a number return false
		       if((number < limit1 || number > limit2))
		       {
		    	   return false;
		       }
		    }
		    
		    return true;
		}
		//else is not a valid header
		else
		{
			return false;
		}
	}
}