package business;

import java.util.ArrayList;

import database.ListStorage;

//40430615
//Joel Degner-Budd
//Software Engineering

public class ListBuilder 
{
	private static ListBuilder single_instance = null;//singleton object
	
	private ArrayList<String>tempMsgList = new ArrayList<>();
	private ListStorage LS = ListStorage.getListStorageInstance();
	
	private ListBuilder()
	{
		
	}
	
	public static ListBuilder getListBuilder()
	{
		if (single_instance == null)
		{
			single_instance = new ListBuilder(); 
		}
		
        return single_instance; 
	}
	
	//Method - get List Storage and build message list
	public void msgStorageBuilder(String msg)
	{
		tempMsgList = LS.getMsgList();//retrieve stored list
		tempMsgList.add(msg);//add new message to list
		LS.setMsgList(tempMsgList);//set new list
	}
	
	//Method - get List storage and build mentions list
	public void mentionListBuilder(String mention)
	{
		tempMsgList = LS.getMentionsList();//retrieve stored list
		tempMsgList.add(mention);//add new mention to list
		LS.setMentionsList(tempMsgList);//set new list
	}
}
