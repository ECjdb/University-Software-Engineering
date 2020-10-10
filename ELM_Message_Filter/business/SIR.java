package business;

public class SIR 
{
	private String centerCode = "";
	private String nature = "";
	
	public SIR(String centerCode, String nature)
	{
		this.centerCode = centerCode;
		this.nature = nature;
	}
	
	//getters and setters
	public String getCenterCode() {return centerCode;}
	public void setCenterCode(String centerCode) {this.centerCode = centerCode;}

	public String getNature() {return nature;}
	public void setNature(String nature) {this.nature = nature;}
	
	@Override
    public String toString()
    {
        return "{" + this.getCenterCode()
                + " : " + this.getNature() + '}';
    }

}
