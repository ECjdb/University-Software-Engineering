package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import business.ListBuilder;
import business.Message;

//40430615
//Joel Degner-Budd
//Software Engineering

public class Reader_Writer
{
	private static Reader_Writer single_instance = null; //singleton object
	
	private Reader_Writer()
	{
		
	}
	
	//creates or retrieves singleton object
	public static Reader_Writer getRWInstance()
	{
		if (single_instance == null)
		{
			single_instance = new Reader_Writer(); 
		}
		
        return single_instance; 
	}
	
	//JSON WRITER//
	public void createJsonFile(Object obj)
	{
		try (Writer writer = new FileWriter("D:\\TEST\\eclipse-workspace\\SoftEngAssignment1\\messages.json")) 
		{
			//create GSON object
		    Gson gson = new GsonBuilder().create();
		    //write to file
		    gson.toJson(obj, writer);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	//WRITE FILE//
	public void writeFile(ArrayList<String> objList, String fileName)
	{
		ArrayList<String> writeMsgList = objList;
		String myFile = fileName;//stores name of file
        File msgFile = new File(myFile);
        String value = "";
                  
        try
        {
            FileWriter fw = new FileWriter(msgFile);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(String x : writeMsgList)
            {
            	bw.write(x+",");
            }
            
            bw.flush();
            bw.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}
	
	//FILE READER//
	public ArrayList<String> readFile(ArrayList<String> list,String fileName)
	{
	     File myTextFile = new File(fileName);//stores name of file to read in
	     //ArrayList<String> msgList = new ArrayList<>();
	     String [] tempArr = null;
	     String row = "";
	     String message = "";
	     
	     try
	     {
	    	 FileReader fr = new FileReader(myTextFile);  
	         BufferedReader br = new BufferedReader(fr);
	         
	         BufferedReader csvReader = new BufferedReader(new FileReader(myTextFile));
	            
	         while ((row = csvReader.readLine()) != null) 
	         {
	        	 tempArr = row.split(",");
	         }
	            
	         for(String x : tempArr)
	         {
	        	 list.add(x);
	         }
	         
	         br.close();
	         
	         return list;
	     }  
	     catch(Exception e)
	     {
	    	 return list;
	     }	
	}
	
	//READ EXCEL ABBREVIATIONS FILE//
	public void readExcelFile()
	{
		try
		{
			ArrayList<String> abbList = new ArrayList<>();
			ArrayList<String> repList = new ArrayList<>();
			
			String filePath = "D:\\TEST\\eclipse-workspace\\SoftEngAssignment1\\textwords.csv"; //will likely have to change this
			Path directory = Paths.get(filePath);
			Path csvPath = directory.resolve(filePath);
			CSVParser csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(),CSVFormat.DEFAULT.withHeader("A","B"));
					
			csvParser.forEach(csvRecord ->{
				String tempA = csvRecord.get("A");
				String tempB = csvRecord.get("B");
				abbList.add(tempA);
				repList.add(tempB);

			});		
			
			ListStorage LS = ListStorage.getListStorageInstance();
			LS.setAbbList(abbList);
			LS.setRepList(repList);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
