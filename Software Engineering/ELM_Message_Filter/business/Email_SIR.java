package business;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Email_SIR extends Message
{
	private String msgHead;
	private String msgBody;
	private String sender;
	private String sirSubject;
	private String sportsCenterCode;
	private String incident;

	public Email_SIR(String msgHead, 
					String msgBody, 
					String sender, 
					String subject, 
					String sportsCenterCode,
					String incident) 
	{
		super(msgHead,msgBody);
		this.sender = sender;
		this.sirSubject = subject;
		this.sportsCenterCode = sportsCenterCode;
		this.incident = incident;
	}
	
	//getters and setters
	public String getMsgHead() {return msgHead;}
	public void setMsgHead(String msgHead) {this.msgHead = msgHead;}

	public String getMsgBody() {return msgBody;}
	public void setMsgBody(String msgBody) {this.msgBody = msgBody;}

	public String getSender() {return sender;}
	public void setSender(String sender) {this.sender = sender;}

	public String getSirSubject() {return sirSubject;}
	public void setSirSubject(String sirSubject) {this.sirSubject = sirSubject;}

	public String getSportsCenterCode() {return sportsCenterCode;}
	public void setSportsCenterCode(String sportsCenterCode) {this.sportsCenterCode = sportsCenterCode;}

	public String getIncident() {return incident;}
	public void setIncident(String incident) {this.incident = incident;}
	
	//override toString method with custom toString
	@Override
    public String toString()
    {
        return "{" + this.getMsgHeader()
                + " : " + this.getMsgBody() 
                + " : " + this.getSender()
                + " : " + this.getSirSubject() 
                + " : " + this.getSportsCenterCode()
                + " : " + this.getIncident() + '}';
    }

}
