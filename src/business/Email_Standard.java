package business;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Email_Standard extends Message 
{
	private String sender;
	private String subject;

	public Email_Standard(String msgHead, String msgBody, String sender, String subject)
	{
		super(msgHead, msgBody);
		this.sender = sender;
		this.subject = subject;
	}
	
	//getters and setters
	public String getSender() {return sender;}
	public void setSender(String sender) {this.sender = sender;}

	public String getSubject() {return subject;}
	public void setSubject(String subject) {this.subject = subject;}
	
	//override toString method with custom toString
	@Override
    public String toString()
    {
        return "{" + this.getMsgHeader()
                + " : " + this.getMsgBody() 
                + " : " + this.getSender()
                + " : " + this.getSubject() + '}';
    }
}
