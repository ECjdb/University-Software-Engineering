package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import database.ListStorage;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Twitter_Hashtag 
{
	private int rank;
	private int occurrence;
	private String hashtag;
	
	public Twitter_Hashtag()
	{
		
	}
	
	private Twitter_Hashtag(int rank ,int occurrence,String hashtag)
	{
		this.rank = rank;
		this.occurrence = occurrence;
		this.hashtag = hashtag;
	}
	
	//DISABLED - The trending list has been disabled as it was preventing the system from working correctly
	
	public void hashtagListGenerator(ArrayList<String> strList)
	{
		ListStorage LS = ListStorage.getListStorageInstance();
		//ArrayList<String> tempStrList = new ArrayList<>();
		ArrayList <Twitter_Hashtag> tempTrendingList = LS.getTrendingList(); //load existing trending list
		int counter = 1;
		
		for(Twitter_Hashtag th : tempTrendingList)
		{
			System.out.println(th.hashtag+th.occurrence+th.rank);
		}
		
		//loop through passed in list for existing hashtags and new ones
		for(String x : strList)
		{
			//if tempTrendingList isn't empty loop for element 
			for(Twitter_Hashtag TH : tempTrendingList)
			{
				//if string matches existing hashtag
				if(x.equalsIgnoreCase(TH.hashtag))
				{
					//add one to occurence for current object
					TH.occurrence = TH.occurrence++;
				}
				//if string doesnt match element
				if(counter == tempTrendingList.size())
				{
					//add to new list
					int r = 0;
					int o = 1;
					String h = x;
					
					//add new hashtag to new hashtag list.
					Twitter_Hashtag tag = new Twitter_Hashtag(r,o,h);
					tempTrendingList.add(tag);
				}
					
				counter++;
			}
		}
		
		//if new list is not empty
		boolean checker = tempTrendingList.isEmpty();
		if(checker == true)
		{
			//remove list duplicates
			Set<String> set = new HashSet<>(strList);
			strList.clear();
			strList.addAll(set);
			
			//create new hashtags
			for(String x : strList)
			{
				int r = 0;
				int o = 1;
				String h = x;
				
				//add new hashtag to new hashtag list.
				Twitter_Hashtag tag = new Twitter_Hashtag(r,o,h);
				tempTrendingList.add(tag);
			}
		}
		
		//sort order of trending list
		tempTrendingList = setRank(tempTrendingList);
		
		//testing
		for(Twitter_Hashtag x : tempTrendingList)
		{
			System.out.println("After sort: "+x.hashtag+" "+x.rank+" "+x.occurrence);
		}
		//set new trending list
		LS.setTrendingList(tempTrendingList);
	}
	
	//Sort and Set Rank
	private ArrayList<Twitter_Hashtag> setRank(ArrayList<Twitter_Hashtag> trendingList)
	{
		int rankSetter = 1;
		
		Collections.sort(trendingList, new Comparator<Twitter_Hashtag>()
		{
			public int compare(Twitter_Hashtag th1, Twitter_Hashtag th2) 
			{
				return Integer.valueOf(th2.occurrence).compareTo(th1.occurrence);
			}
			
		});
		
		for(Twitter_Hashtag x : trendingList)
		{
			x.setRank(rankSetter);
			rankSetter++;
		}
		return trendingList;
	}
	
	//getters and setters
	public int getRank() {return rank;}
	public void setRank(int rank) {this.rank = rank;}
	
	public int getOccurrence() {return occurrence;}
	public void setOccurrence(int occurrence) {this.occurrence = occurrence;}

	public String getHashtag() {return hashtag;}
	public void setHashtag(String hashtag) {this.hashtag = hashtag;}
	
	@Override
    public String toString()
    {
        return "{" + this.getRank()
                + " : " + this.getOccurrence()
                + " : " + this.getHashtag() + '}';
    }
}
