package database;

import java.util.ArrayList;

import business.Email_SIR;
import business.Email_Standard;
import business.SMS;
import business.Tweet;
import business.Twitter_Hashtag;

//40430615
//Joel Degner-Budd
//Software Engineering

public class ListStorage 
{
	private static ListStorage single_instance = null; //singleton object
	
	
	private SMS sms = null;
	private Email_Standard email = null;
	private Email_SIR sir = null;
	private Tweet tweet = null;
	
	//private String currHeader = "";
	//private String currBody= "";
	private ArrayList<String> abbList = new ArrayList<>(); //stores abbreviations
	private ArrayList<String> repList = new ArrayList<>();; //store abbreviation replacements
	private ArrayList<String> msgList = new ArrayList<>();; //stores all msgType objects
	private ArrayList<Twitter_Hashtag> trendingList = new ArrayList<>(); //stores list of hashtags and the number of occurences
	private ArrayList<String> mentionsList = new ArrayList<>();; //stores all mentions
	private ArrayList<String> sirList = new ArrayList<>();//stores all sir center codes and nature of incident
	
	private String messageFile = "messages.txt";
	private String mentionFile = "mentions.txt";
	private String trendingFile = "trending.txt";
	private String sirFile = "sir.txt";
	
	private ListStorage()
	{
		
	}
	
	//creates or retrieves singleton object
	public static ListStorage getListStorageInstance()
	{
		if (single_instance == null)
		{
			single_instance = new ListStorage(); 
		}
		
        return single_instance; 
	}
	
	//Method - load required programme files
	public void loadFiles()
	{
		 Reader_Writer RW = Reader_Writer.getRWInstance();
		 msgList = RW.readFile(msgList,messageFile);
		 mentionsList = RW.readFile(mentionsList,mentionFile);
		 //trendingList = RW.readFile(trendingFile);
		 sirList = RW.readFile(sirList,sirFile);
	}
	
	//Method - update and save required files at end of session
	public void saveFiles()
	{
		Reader_Writer RW = Reader_Writer.getRWInstance();
		RW.writeFile(msgList,messageFile);
		RW.writeFile(mentionsList, mentionFile);
		//RW.writeFile(trendingList,trendingFile);
		RW.writeFile(sirList ,sirFile);
		RW.createJsonFile(msgList);
	}
	//getters and setters
	public ArrayList<String> getAbbList() {return abbList;}
	public void setAbbList(ArrayList<String> abbList) {this.abbList = abbList;}

	public ArrayList<String> getRepList() {return repList;}
	public void setRepList(ArrayList<String> repList) {this.repList = repList;}

	public ArrayList<String> getMsgList() {return msgList;}
	public void setMsgList(ArrayList<String> msgList) {this.msgList = msgList;}

	public ArrayList<Twitter_Hashtag> getTrendingList() {return trendingList;}
	public void setTrendingList(ArrayList<Twitter_Hashtag> trendingList) {this.trendingList = trendingList;}

	public ArrayList<String> getMentionsList() {return mentionsList;}
	public void setMentionsList(ArrayList<String> mentionsList) {this.mentionsList = mentionsList;}

	public ArrayList<String> getSirList() {return sirList;}
	public void setSirList(ArrayList<String> sirList) {this.sirList = sirList;}
/*
	public String getCurrHeader() {return currHeader;}
	public void setCurrHeader(String currHeader) {this.currHeader = currHeader;}

	public String getCurrBody() {return currBody;}
	public void setCurrBody(String currBody) {this.currBody = currBody;}
	*/

	public SMS getSms() {
		return sms;
	}

	public void setSms(SMS sms) {
		this.sms = sms;
	}

	public Email_Standard getEmail() {
		return email;
	}

	public void setEmail(Email_Standard email) {
		this.email = email;
	}

	public Email_SIR getSir() {
		return sir;
	}

	public void setSir(Email_SIR sir) {
		this.sir = sir;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	
	
}
