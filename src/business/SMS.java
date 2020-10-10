package business;

//40430615
//Joel Degner-Budd
//Software Engineering

public class SMS extends Message
{
	private String smsSender;
	
	public SMS(String msgHead, String msgBody, String smsSender) 
	{
		super(msgHead, msgBody);
		this.smsSender = smsSender;
	}

	//getters and setters
	public String getSmsSender() {return smsSender;}
	public void setSmsSender(String smsSender) {this.smsSender = smsSender;}
	
	//override toString method with custom toString
	@Override
    public String toString()
    {
        return "{" + this.getMsgHeader()
                + " : " + this.getMsgBody() 
                + " : " + this.getSmsSender() + '}';
    }
}
