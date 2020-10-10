package business;

//40430615
//Joel Degner-Budd
//Software Engineering

import java.util.ArrayList;

import database.ListStorage;
import database.Reader_Writer;

public class CheckMsgBody 
{
	private String isValid = "";
	private String smsPhoneNum = "";
	private String senderEmail = "";
	private String subject = "";
	private String emergencySIR = "";
	private String centreNum = "";
	private String twitterID = "";
	
	public CheckMsgBody()
	{
		//blank constructor
	}
	
	//Message Factory Builder method, object used to set values for Message objects
	private Message_Factory msgFactoryBuilder()
	{
		Message_Factory MFB = Message_Factory.getMessage_FactoryInstance();
		return MFB;
	}
	
	//Method - Checks SMS body
	public String checkSMSBody(String msgHead, String msgBody)
	{   
	    try 
	    {
	    	String phonePattern = "^(\\+44\\s?\\d{11}?$)";//regex pattern for phone number e.g +4412345123451 etc.
	    	String[] msgArray = msgBody.split("\\s+");//split msgBody into String array
	        String subBody = "";//set subBody string to empty
	        
	        //if the first string in the array matches the phone number pattern
	        if(msgArray[0].matches(phonePattern))
	        {
	        	//store the value in smsPhoneNum
	        	smsPhoneNum = msgArray[0];
	        	//set subBody to rest of message body after the phone number
	        	subBody = msgBody.substring(msgArray[0].length()+1);
	        }
	        //else its not a valid phone number, return error to user
	        else
	        {
	        	return isValid = "Not a valid sender phone number";
	        }
        	//if the subBody string length is longer than 140 characters then return error to user
	        if(subBody.length() > 140)
        	{
        		return isValid = "invalid body: exceeds 140 character limit";
        	}
	        
	        //if there isn't at least a single character in the subBody then return error to user
        	else if(subBody.length() < 1)
        	{
        		return isValid = "invalid body: must contain at least 1 character";
        	}
	        //else message body is valid
        	else
        	{
        		Message_Factory MFB = msgFactoryBuilder();//get Message Factory Builder
        		MessageCleaner MC = MessageCleaner.getMsgCleanerInstance();//get Message Cleaner
        		String temp = MC.CleanMessage(msgHead,subBody);//pass in message header and body to be cleaned
        		
        		MFB.setMsgHead(msgHead);//pass message header to Message Factory
        		MFB.setMsgBody(temp);//pass cleaned message body to Message Factory
        		MFB.setSmsNum(smsPhoneNum);//pass phone number of sender to Message Factory#
        		SMS sms = (SMS) MFB.Msg_Factory();//return Message Factory object and cast as SMS message type
        		ListStorage.getListStorageInstance().setSms(sms);//set current SMS object in ListStorage for later retrieval
        		ListBuilder LSB = ListBuilder.getListBuilder();//get ListBuilder to construct list
        		LSB.msgStorageBuilder(sms.toString());//store message in List Storage message ArrayList as String
        		
        		return isValid = "successful";//return "successful" to user
        	}
	    } 
	    //catch any other errors that may occur
	    catch (Exception e) 
	    {
	        return isValid = "error: "+e;
	    }
	}
	
	//Method - Checks Email body
	public String checkEmailBody(String msgHead, String msgBody)
	{
		try 
	    {
	        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + //regex pattern for Email e.g. test@gmail.com
	                              "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{3}$";
	        String emergencySIRPattern = "^[0-3][0-9]/[0-3][0-9]/[0-9][0-9]$"; //regex for sir emergency data e.g. 11/11/98
	        String sportsCenterPattern = "^\\d{2}-\\d{3}-\\d{2}$"; //regex for sir emegency sports center code e.g. 11-111-11
	        
	        String[] msgArray = msgBody.split("\\s+");//split msgbody into message Array
	        String subBody = "";//set subBody string to empty

	        //if valid email address is found
	        if(msgArray[0].matches(emailPattern))
	        {
	        	senderEmail = msgArray[0];//store valid email address to senderEmail
	        }
	        //else email body not valid return error message to user
	        else
	        {
	        	return isValid = "Not a valid sender email address";
	        }
	        //if message index 1 is equal to SIR
	        if(msgArray[1].equalsIgnoreCase("SIR"))
	        {
	        	//and if message index 2 is equal to valid emergency date
	        	if(msgArray[2].matches(emergencySIRPattern))
	        	{
	        		emergencySIR = msgArray[1] +" "+ msgArray[2];//concatenate 'SIR' and date, store in emergencySIR
	        		
	        		//if the 4th string matches valid sports center code then SIR Email is valid
	        		if(msgArray[3].matches(sportsCenterPattern))
	        		{
	        			centreNum = msgArray[3];//store center code in centreNum
	        			subBody = msgBody.substring(msgArray[0].length()+1+msgArray[1].length()+1+msgArray[2].length()+1+msgArray[3].length()+1);//store rest of message after SIR variables as subBody String
	        			
	        			Message_Factory MFB = msgFactoryBuilder();//get Message Factory Builder
		        		MessageCleaner MC = MessageCleaner.getMsgCleanerInstance();//get Message Cleaner
		        		String temp = MC.CleanMessage(msgHead,subBody);//pass in message header and body to be cleaned
		        		
		        		MFB.setMsgHead(msgHead);//pass message header to Message Factory
		        		MFB.setMsgBody(temp);//pass cleaned message body to Message Factory
		        		MFB.setEmailSender(senderEmail);//pass sender email to Message Factory
		        		MFB.setSirSubject(emergencySIR);//pass SIR+date to Message Factory
		        		MFB.setCentreCode(centreNum);//pass center code to Message Factory
		        		MFB.setIncidentNature("");//set empty initially
		        		
		        		Email_SIR sir = (Email_SIR) MFB.Msg_Factory();//return Message Factory object and cast as Email message type
		        		ListStorage.getListStorageInstance().setSir(sir);//set current SIR object in ListStorage for later retrieval
		        		ListBuilder LSB = ListBuilder.getListBuilder();//get ListBuilder to construct list
		        		LSB.msgStorageBuilder(sir.toString());//store message in List Storage message ArrayList as String
		        		
	        			return isValid = "SIR";//return "SIR" to user requires user input for "nature of incident" 
	        		}
	        		//else not a valid sports code return error to user
	        		else 
	        		{
	        			return isValid = " not a valid sports center code";
	        		}
	        	}
	        	//is not a valid SIR date return error to user
	        	else
	        	{
	        		return isValid = "not a valid SIR date";
	        	}
	        }
	        //if not an SIR Email then email is treated as a standard email
	        else
	        {
	        	//subject must be between 1-20 characters long
	        	if(msgArray[1].length() > 0 && msgArray[1].length() <= 20)
	        	{
	        		subject = msgArray[1];//store subject
	        		subBody = msgBody.substring(msgArray[0].length()+1+msgArray[1].length()+1);//store rest of message after SIR variables as subBody String
	        	}
	        	//else subject is not valid length return error message to user
	        	else 
	        	{
	        		return isValid = "invalid subject: must be between 1 and 20 characters";
	        	}
	        	//if the message length is longer than 1028 characters then return error message to user
	        	if(subBody.length() > 1028)
	        	{
	        		return isValid = "invalid body: exceeds 1028 character limit";
	        	}
	        	//if the message length is not at least 1 character return error message to user
	        	else if(subBody.length() < 1)
	        	{
	        		return isValid = "invalid body: must contain at least 1 character";
	        	}
	        	//else Email is valid
	        	else
	        	{
	        		Message_Factory MFB = msgFactoryBuilder();//get Message Factory Builder
	        		MessageCleaner MC = MessageCleaner.getMsgCleanerInstance();//get Message Cleaner
	        		String temp = MC.CleanMessage(msgHead,subBody);//pass in message header and body to be cleaned
	        		
	        		MFB.setMsgHead(msgHead);//pass message header to Message Factory
	        		MFB.setMsgBody(temp);//pass cleaned message body to Message Factory
	        		MFB.setEmailSender(senderEmail);//pass sender email to Message Factory
	        		MFB.setStandardSubject(subject);//pass subject to Message Factory
	        		Email_Standard email = (Email_Standard) MFB.Msg_Factory();//return Message Factory object and cast as Email message type
	        		ListStorage.getListStorageInstance().setEmail(email);//set current Email object in ListStorage for later retrieval
	        		ListBuilder LSB = ListBuilder.getListBuilder();//get ListBuilder to construct list
	        		LSB.msgStorageBuilder(email.toString());//store message in List Storage message ArrayList as String
	        		
	        		return isValid = "successful";//return "successful" to user
	        	}
	        }
	    } 
		//catch any other errors that may occur
	    catch (Exception e) 
	    {
	        return isValid = "error: "+e;
	    }
	}
	
	//Method - Checks Tweet body
	public String checkTweetBody(String msgHead, String msgBody)
	{
		try 
	    {
	    	String idPattern = "(?<=^|(?<=[^a-zA-Z0-9-_\\.]))@([A-Za-z]+[A-Za-z0-9-_]+)";//regex pattern for twitter id e.g. @myID
	    	
	    	String[] msgArray = msgBody.split("\\s+");//split msgBody into String arry
	        String subBody = "";//set subBody string to empty
	        
	        //if first index of msgArry matches the ID pattern
	        if(msgArray[0].matches(idPattern))
	        {
	        	//if the length is greater than 15 characters return error to user
	        	if(msgArray[0].length() > 15)
	        	{
	        		return isValid = "invalid body: twitter ID exceeds 15 character limit";
	        	}
	        	//else if the length is less than 1 return error to user
	        	else if(msgArray[0].length() < 1)
	        	{
	        		return isValid = "invalid body: twitter ID must contain atleast 1 character ";
	        	}
	        	//else twitter ID is valid
	        	else
	        	{
	        		twitterID = msgArray[0];//store in twitterID
	        		subBody = msgBody.substring(msgArray[0].length()+1);//set subBody to msgBody after twitterID
	        	}
	        }
	        //else not a valid twitter ID return error to user
	        else
	        {
	        	return isValid = "Invalid twitter ID";
	        }
	        
	        //if the message body length is greater than 140 characters return error to user
	        if(subBody.length() > 140)
	        {
	        	return isValid = "invalid body: twitter ID exceeds 140 character limit";
	        }
	        //else if the the message body is less than 1 character then return error to user
	        else if(subBody.length() < 1)
	        {
	        	return isValid = "invalid body: tweet body must contain atleast 1 character ";
	        }
	        //else message is valid
	        else
	        {
	        	Message_Factory MFB = msgFactoryBuilder();//get Message Factory Builder
        		MessageCleaner MC = MessageCleaner.getMsgCleanerInstance();//get Message Cleaner
        		String temp = MC.CleanMessage(msgHead,subBody);//pass in message header and body to be cleaned

        		MFB.setMsgHead(msgHead);//pass message header to Message Factory
        		MFB.setMsgBody(temp);//pass cleaned message body to Message Factory
        		MFB.setTweetId(twitterID);//pass twitterID to Message Factory
        		Tweet tweet = (Tweet) MFB.Msg_Factory();//return Message Factory object and cast as Tweet message type
        		ListStorage.getListStorageInstance().setTweet(tweet);//set current Tweet object in ListStorage for later retrieval
        		ListBuilder LSB = ListBuilder.getListBuilder();//get ListBuilder to construct list
        		LSB.msgStorageBuilder(tweet.toString());//store message in List Storage message ArrayList as String
	        	
	        	return isValid = "successful";//return "successful" to user
	        }
	    }
		//catch any other errors that may occur
	    catch (Exception e) 
	    {
	        return isValid = "error: "+e;
	    }
	}
}
