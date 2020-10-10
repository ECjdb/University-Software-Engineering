package business;

import database.ListStorage;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Message_Factory
{
	private static Message_Factory single_instance = null;//singleton object
	
	//private variables to build Message objects
	private String msgHead = "";
	private String msgBody = "";
	private String smsNum = "";
	private String standardSubject = "";
	private String emailSender = "";
	private String sirSubject = "";
	private String centreCode = "";
	private String incidentNature = "";
	private String tweetId = "";
	
	private Message_Factory()
	{
		
	}
	
	public static Message_Factory getMessage_FactoryInstance()
	{
		if (single_instance == null)
		{
			single_instance = new Message_Factory(); 
		}
		
        return single_instance; 
	}
	
	public Message Msg_Factory()
	{	
		//if SMS message build and return SMS object
	    if(msgHead.contains("S"))
	    {
	        return new SMS(msgHead, msgBody, smsNum);
	    } 
	    //if Email message
	    else if(msgHead.contains("E"))
	    {
	    	//if sirSubject is empty build and return normal email object
	    	if(sirSubject.equalsIgnoreCase(""))
	    	{
	    		System.out.println(emailSender);
	    		System.out.println(standardSubject);
	    		
	    		return new Email_Standard(msgHead, msgBody, emailSender, standardSubject);
	    	}
	    	//else build and return sir emergency object
	    	else
	    	{
	    		return new Email_SIR(msgHead, msgBody, emailSender, sirSubject, centreCode, incidentNature);
	    	}
	    } 
	    //if Tweet message build and return Tweet object
	    else if(msgHead.contains("T"))
	    {
	        return new Tweet(msgHead, msgBody, tweetId);
	    }
	    
	    return null;
	};
	
	//Getters and Setters
	public String getMsgHead() {return msgHead;}
	public void setMsgHead(String msgHead) {this.msgHead = msgHead;}
	
	public String getMsgBody() {return msgBody;}
	public void setMsgBody(String msgBody) {this.msgBody = msgBody;}
	
	public String getSmsNum() {return smsNum;}
	public void setSmsNum(String smsNum) {this.smsNum = smsNum;}
	
	public String getStandardSubject() {return standardSubject;}
	public void setStandardSubject(String standardSubject) {this.standardSubject = standardSubject;}
	
	public String getEmailSender() {return emailSender;}
	public void setEmailSender(String emailSender) {this.emailSender = emailSender;}
	
	public String getSirSubject() {return sirSubject;}
	public void setSirSubject(String sirSubject) {this.sirSubject = sirSubject;}
	
	public String getCentreCode() {return centreCode;}
	public void setCentreCode(String centreCode) {this.centreCode = centreCode;}
	
	public String getIncidentNature() {return incidentNature;}
	public void setIncidentNature(String incidentNature) {this.incidentNature = incidentNature;}
	
	public String getTweetId() {return tweetId;}
	public void setTweetId(String tweetId) {this.tweetId = tweetId;}

}
