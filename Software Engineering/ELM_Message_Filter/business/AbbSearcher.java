package business;

//40430615
//Joel Degner-Budd
//Software Engineering

import java.util.ArrayList;

import database.ListStorage;

public class AbbSearcher 
{
	private static AbbSearcher single_instance = null; //singleton object
	
	private AbbSearcher()
	{
		
	}
	
	//creates or retrieves singleton object
	public static AbbSearcher getAbbSearcherInstance()
	{
		if (single_instance == null)
		{
			single_instance = new AbbSearcher(); 
		}
		
        return single_instance; 
	}
	
	//Method - finds abbreviations in text and tweet, expands content
	public String AbbChecker(String str)
	{
		int counter = 0;
		ListStorage ls = ListStorage.getListStorageInstance(); //retrieve list storage object
        ArrayList <String> abbList = ls.getAbbList(); //retrieves abbreviation data from list storage
        ArrayList <String> repList = ls.getRepList(); //retrieves abbreviation replacement data from list storage
        
        //for every object in abbreviation list
        for(String x : abbList)
        {
        	//if the passed in string is equal to an abbreviation
        	if(str.equalsIgnoreCase(x))
        	{
        		//search for expanded form
        		for(int y = 0; y<repList.size();y++)
        		{
        			//if found expanded form index
        			if(y == counter)
        			{
        				//replace and return the string in expanded form
        				str = str+"<"+repList.get(y)+">";
        				return str;
        			}
        		}
        	}
        	//else if abbreviation cannot be found
        	else if(counter+1 == abbList.size())
        	{
        		//return the string
        		return str;
        	}
        	
        	counter++; //increment counter
        }
		
		return str;
	}

}
