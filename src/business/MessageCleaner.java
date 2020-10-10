package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import database.ListStorage;

//40430615
//Joel Degner-Budd
//Software Engineering

public class MessageCleaner
{
	private static MessageCleaner single_instance = null; //singleton object

	private MessageCleaner()
	{
		
	}
	
	//creates or retrieves singleton object
	public static MessageCleaner getMsgCleanerInstance()
	{
		if (single_instance == null)
		{
			single_instance = new MessageCleaner(); 
		}
		
        return single_instance; 
	}
	
	//Method - Clean message body based on type
	public String CleanMessage(String msgHead, String msg)
	{
        String hyperlinkPattern = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}\\.([a-z]+)?$";//regex pattern for hyper link e.g. https://www.google.com
        String idPattern = "(?<=^|(?<=[^a-zA-Z0-9-_\\.]))@([A-Za-z]+[A-Za-z0-9-_]+)";//regex pattern for mentions e.g. @mention
    	String hashtagPattern = "(?<=^|(?<=[^a-zA-Z0-9-\\.]))#([A-Za-z]+[A-Za-z0-9-]+)";//regex pattern for hashtags e.g. #hashtag
    	ListStorage LS = ListStorage.getListStorageInstance();//get List Storage instance
    	
		String[] msgArray = msg.split("\\s+");//split message body 'msg' in String array
		msg = "";//set msg to empty
		
		//if message type is SMS
		if(msgHead.contains("S"))
		{
	        ArrayList <String> abbList = LS.getAbbList(); //retrieves abbreviation data
	        //for the length of the message array
			for(int x = 0; x<msgArray.length;x++)
			{
				//for the length of the abbreviations list
				for(String y : abbList)
				{
					//if a string in msgArray matches with an abbreviation string
					if(msgArray[x].equalsIgnoreCase(y))
					{
						//call abbSearcher, idenfity abbreviation and expand abbreviation string
						msgArray[x] = AbbSearcher.getAbbSearcherInstance().AbbChecker(y);
					}
				}
			}
			
			//rebuild message
			for(String x : msgArray)
			{
				msg = msg +" "+ x;
			}

			return msg;//return cleaned message
		}
		
		//if message type is Email
		else if(msgHead.contains("E"))
		{
			//for the length of the message array
			for(int x = 0; x<msgArray.length;x++)
			{
				//if a string in msgArray matches a hyperlink
				if(msgArray[x].matches(hyperlinkPattern))
				{
					//replace string with <URL Quarantined>
					msgArray[x] = "<URL Quarantined>";
				}
			}
			
			//rebuild message
			for(String x : msgArray)
			{
				msg = msg +" "+ x;
			}
			
			return msg;//return cleaned message
		}
		//if message type is Tweet
		else if(msgHead.contains("T"))
		{
			ArrayList <String> abbList = LS.getAbbList(); //retrieves abbreviation data
			ArrayList <String> tempMentions = LS.getMentionsList(); //retrieves mentionlist
			ArrayList <String> tempTrend = new ArrayList<>();//temporary trending list
			
			//for the length of the message array
			for(int x = 0; x<msgArray.length;x++)
			{
				//for the length of the abbreviation list
				for(String y : abbList)
				{
					//if string in message array matches an abbreviation
					if(msgArray[x].equalsIgnoreCase(y))
					{
						//call abbSearcher, idenfity abbreviation and expand abbreviation string
						msgArray[x] = AbbSearcher.getAbbSearcherInstance().AbbChecker(y);
					}
				}
				//if msgArray string matches a mention
				if(msgArray[x].matches(idPattern))
				{
					//add to temporary mentions list
					tempMentions.add(msgArray[x]);
				}
				//if msgArray string matches a hashtag
				if(msgArray[x].matches(hashtagPattern))
				{
					//add to temporary trending list
					tempTrend.add(msgArray[x]);
				}
			}
			
			LS.setMentionsList(tempMentions);//set new mention list
			
			//DISABLED - The trending list has been disabled as it was preventing the system from working correctly
			//Twitter_Hashtag TH = new Twitter_Hashtag();//Build new trending list object
			//TH.hashtagListGenerator(tempTrend);//create and sort trendinglist
			
			//rebuild message
			for(String x : msgArray)
			{
				msg = msg +" "+ x;
			}
			
			return msg;//return clean message
		}
		
	    return msg;//default return
	}
}
	
