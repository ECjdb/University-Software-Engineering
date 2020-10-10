package business;

//40430615
//Joel Degner-Budd
//Software Engineering

public abstract class Message 
{
	private String msgHeader;
	private String msgBody;
	
	public Message(String msgHeader, String msgBody)
	{
		this.setMsgHeader(msgHeader);
		this.setMsgBody(msgBody);
	}

	//getters and setters
	public String getMsgHeader() {return msgHeader;}
	public void setMsgHeader(String msgHeader) {this.msgHeader = msgHeader;}
	
	public String getMsgBody() {return msgBody;}
	public void setMsgBody(String msgBody) {this.msgBody = msgBody;}
}
