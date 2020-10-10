package business;

import java.util.ArrayList;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Tweet extends Message
{
	
	private String twitterID;
	
	public Tweet(String msgHeader, String msgBody, String twitterID) 
	{
		super(msgHeader, msgBody);
		this.setTwitterID(twitterID);
	}

	//getters and setters
	public String getTwitterID() {return twitterID;}
	public void setTwitterID(String twitterID) {this.twitterID = twitterID;}
	
	//override toString method with custom toString
	@Override
    public String toString()
    {
        return "{" + this.getMsgHeader()
                + " : " + this.getMsgBody() 
                + " : " + this.getTwitterID() + '}';
    }
}
