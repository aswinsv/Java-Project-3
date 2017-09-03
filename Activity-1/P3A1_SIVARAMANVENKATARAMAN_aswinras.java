/*

Author Name: Aswin ram S.V.

Creation Date: 08/03/2017

Modification Date: 08/5/2017

Description: 1) The given file reads a file named emacs.log and prints the following details to console.
                   1) The total number of files in the log file
                   2) File(s) with the most number of revisions
                   3) File(s) with most users commiting
                   4) Earliest commit to a file
                   5) User with the most commits
                   6) It also displays filewise data i.e. filename,earliest commit to the file,users with most commits,number of commits by that user

             2) It also has a class named P3A1_SIVARAMANVENKATARAMAN_File_aswinras, that describes details pertaining to a file
             	object such as earliest commitDate, username, number of commits by that user etc.


*/

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.*;

import java.util.Iterator;

import java.text.SimpleDateFormat;  

import java.util.Date;  

import java.text.ParseException; 


// The below class describes a file object, the attributes of which have been mentioned in the program description

class P3A1_SIVARAMANVENKATARAMAN_File_aswinras
{

	private String fileName;

	private Date earliestCommitDate;

	private ArrayList<String> username;

	private int numberofCommits;


	public P3A1_SIVARAMANVENKATARAMAN_File_aswinras()
	{


	}


	public P3A1_SIVARAMANVENKATARAMAN_File_aswinras(String fileName,Date commitDate,ArrayList<String> username,int commits)
	{
		this.fileName=fileName;

		this.earliestCommitDate=commitDate;

		this.username=username;

		this.numberofCommits=commits;
	}

	public void setFileName(String fileName)
	{
		this.fileName=fileName;
	}

	public String getFileName()
	{
		return this.fileName;
	}

	public void setEarliestCommitDate(Date earliestCommitDate)
	{
		this.earliestCommitDate=earliestCommitDate;
	}

	public Date getEarliestCommitDate()
	{
		return earliestCommitDate;
	}

	public void setUsername(ArrayList<String> username)
	{
		this.username=username;
	}

	public ArrayList<String> getUsernames()
	{
		return this.username;
	}

	public void setnumberofCommits(int commits)
	{
		this.numberofCommits=commits;
	}	

	public int getnumberofCommits()
	{
		return this.numberofCommits;
	}
}



public class P3A1_SIVARAMANVENKATARAMAN_aswinras
{

	// The file objects obtained from the file data stored in the log file is stored in the below arraylist

	ArrayList<P3A1_SIVARAMANVENKATARAMAN_File_aswinras> listOfFiles=new ArrayList<P3A1_SIVARAMANVENKATARAMAN_File_aswinras>();

	private String inputFilename;

	private String fileMarker="Working file";

	private String revisionMarker="revision 1.";

	private String dateMarker="date:";

	private String endofFileContent="=============================================================================";

	private String authorMarker="author:";

	private ArrayList<String> fileNames=new ArrayList<>();

	private ArrayList<Date> dates=new ArrayList<Date>();

	// The below hashmap maps between File Name and Number of commits of that filename

	 private  HashMap<String,Integer> revisionsMap=new HashMap<String,Integer>();

	// The below hashmap maps between File Name and earliest commit date of that filename

	private HashMap<String,Date> dateMap=new HashMap<String,Date>();

	// The below hashmap maps between user Name and Number of commits made by that user

	private  HashMap<String,Integer> userCommitcount=new HashMap<String,Integer>();

	// The below hashmap maps between file Name and the number of users who commited to that file

	private  HashMap<String	,Integer> filewithMostUsers=new HashMap<String,Integer>();		


	// This hashmap also serves similar purpose as above	

	private  HashMap<String,Integer> fileWiseUserCount=new HashMap<String,Integer>();



	// This SimpleDateformat is to parse time in log file in the form of yyyy-MM-dd HH:mm:ss

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 


	// default constructor for the class
	
	public P3A1_SIVARAMANVENKATARAMAN_aswinras()
	{

	}

	// The function below has the responsibility of reading the log file and storing the data in respective hashmaps and objects.
	
	public void readFile()
	{
		
		BufferedReader br=null;

		FileReader fr=null;

		int n=0;

		String currentLine;

		String[] lineContent;

		String dateContent,authorName;

		try
     {

     	fr=new FileReader(inputFilename);

     	br=new BufferedReader(fr);

     	String fileName=new String();

     	int numberOfrevisions=0;

     	Date newDate;

		while((currentLine=br.readLine())!=null)
	{
			if(!currentLine.contains(endofFileContent))
		{		
			if(currentLine.contains(fileMarker))
			{
				lineContent=currentLine.split(":");

				fileNames.add(lineContent[1]);

				fileName=lineContent[1];
			}

			if(currentLine.contains(revisionMarker))
			{
				numberOfrevisions++;	
			}

			

			if(currentLine.contains(dateMarker))
			{
				lineContent=currentLine.split(";");


				dateContent=lineContent[0].replace("date:","");

            	 try
			{	
				newDate=df.parse(dateContent);


				dates.add(newDate);


				
     	}
			
			catch(ParseException ex)
			{
				System.out.println(ex.getMessage());
			}	

			if(currentLine.contains(authorMarker))
			{
				lineContent=currentLine.split(";");

				authorName=lineContent[1].replace("author:","");


				if(fileWiseUserCount.containsKey(authorName))
				{

					fileWiseUserCount.put(authorName, fileWiseUserCount.get(authorName) + 1);
				}

				else
				{
					fileWiseUserCount.put(authorName,1);
				}	



				if(userCommitcount.containsKey(authorName))
				{
					userCommitcount.put(authorName, userCommitcount.get(authorName) + 1);
				}	

				else
				{
					userCommitcount.put(authorName,1);
				}	
     		}	 // end of inner if

			
			} // end of if loop	

			

		}

		else
	  {

	  	
	  	revisionsMap.put(fileName,numberOfrevisions);

	  	Date minDate=Collections.min(dates);

	  	dateMap.put(fileName,minDate);

	    addFileRecord(fileName,minDate,fileWiseUserCount);

	    filewithMostUsers.put(fileName,fileWiseUserCount.size());

	  	numberOfrevisions=0;

	   	dates.clear();

	  	fileWiseUserCount.clear();

	  	//break;

	  	  	
	  }		
	} // end of while loop	


	 } // end of try
	 
	 catch(IOException ex)
	 {
	 	System.out.println(ex.getMessage());
	 }		

	 finally {

	 	try {

	 		if(br!=null)
	 			br.close();

	 		if(fr!=null)
	 			fr.close();
	 	}

	 	catch(IOException ex)
	 	{
	 		ex.printStackTrace();	
	 	}


	 } // end of finally
	} // end of readfile


	// 
	//@Parameter 1= fileName, which indicates the name to be given to file object
	//@Parameter 2= earliestCommit indicates the earliest commit date of the file object
	//@Paramater 3= The userCount hashmap provides user with the highest commits to that file and the number of commits made by that user
	// The file object after being created gets added to the List of file objects

	
	public void addFileRecord(String fileName,Date earliestCommit,HashMap<String,Integer> userCount)
	{

		ArrayList<String> users=new ArrayList<String>();

		int maxValue=(Collections.max(userCount.values()));

		 for (Map.Entry<String, Integer> entry : userCount.entrySet()) {  // Iterate through hashmap

            if (entry.getValue()==maxValue) {

            	entry.getKey().replaceAll("\\s+","");

            	users.add(entry.getKey());
            	
            }

	}


	P3A1_SIVARAMANVENKATARAMAN_File_aswinras file=new P3A1_SIVARAMANVENKATARAMAN_File_aswinras(fileName,earliestCommit,users,maxValue);

	listOfFiles.add(file);

}


// This function prints the total number of files in the log file
	public void findNumberofFiles()
	{
		System.out.println("Total Number of files:"+fileNames.size());
	}

// This function prints the name of file with most number of revisions	

	public void findFileswithMostRevisions()
	{

		System.out.println("\n*****************************Files with Maximum Number of Revisions***************************\n");
		int maxValue=(Collections.max(revisionsMap.values()));

		 for (Map.Entry<String, Integer> entry : revisionsMap.entrySet()) {  // Iterate through hashmap
            if (entry.getValue()==maxValue) {
            	System.out.println("------------------------------------------------------------");
                System.out.println("FileName:"+entry.getKey());// Print the key with max value
                System.out.println("------------------------------------------------------------");
            }
        }

        System.out.println("\n\n********************************************************************************************");
	}

	// This function prints the earliest commit date of a file


	public void findEarliestCommit()
	{

		System.out.println("\n\n*****************************Files with earliest commit date***************************\n");
		Date minDate=Collections.min(dateMap.values());

		 for (Map.Entry<String, Date> entry : dateMap.entrySet()) {  // Iterate through hashmap
            if (minDate.compareTo(entry.getValue())==0) {
            	System.out.println("------------------------------------------------------------");
                System.out.println("FileName:"+entry.getKey()+" "+"Date:"+df.format(entry.getValue()));// Print the key with min date
                System.out.println("------------------------------------------------------------");
            }
        }

        System.out.println("\n\n********************************************************************************************");



	}

	// This function prints the username with highest number of commits

	public void findUserwithHighestCommit()
	{
		System.out.println("\n\n*****************************User with Highest number of commits***************************\n");

		int maxValue=Collections.max(userCommitcount.values());

		 for (Map.Entry<String, Integer> entry : userCommitcount.entrySet()) {  // Iterate through hashmap
            if (entry.getValue()==maxValue) {
            	System.out.println("------------------------------------------------------------");
                System.out.println("UserName:"+entry.getKey()+"  "+"Number of commits:"+entry.getValue());// Print the key with max value
                System.out.println("------------------------------------------------------------");
            }
        }

        System.out.println("\n\n********************************************************************************************");



	}

	// This function prints the file with highest number of users

	public void findFilewithHighestUsers()
	{

		System.out.println("\n\n*****************************File with Highest number of users***************************\n");

		int maxValue=Collections.max(filewithMostUsers.values());

		 for (Map.Entry<String, Integer> entry : filewithMostUsers.entrySet()) {  // Iterate through hashmap
            if (entry.getValue()==maxValue) {
            	System.out.println("------------------------------------------------------------");
                System.out.println("FileName:"+entry.getKey());// Print the key with max value
                System.out.println("------------------------------------------------------------");
            }
        }

        System.out.println("\n\n********************************************************************************************");


	}

	// The function below displays the following data: <file name>, <earliest commit of this file>, <user(s) with most commits>, <num of commits by this user>"

	public void displayFileWiseData()
	{

		System.out.println("\nThe following data is displayed below: FileName, Earliest Commit Date of this file,user(s) with most commits,No. of commits\n\n");
		
		for(int i=0;i<listOfFiles.size();i++)
		{
			
			
			System.out.println("\n\n FileName:"+listOfFiles.get(i).getFileName()+",   Date:"+df.format(listOfFiles.get(i).getEarliestCommitDate())+",    User(s):"+listOfFiles.get(i).getUsernames()+",     No. of Commits(s):"+listOfFiles.get(i).getnumberofCommits());

		}	
	}


	public static void main(String[] args)
	{

		P3A1_SIVARAMANVENKATARAMAN_aswinras log=new P3A1_SIVARAMANVENKATARAMAN_aswinras();

		boolean correctInput=false;

		// The name of input file should be given as command line parameter

		try

    {	
    	log.inputFilename=args[0];
    }
    
    catch(Exception ex)
    {
    	System.out.println("Please provide the input filename as command line parameter");
    }	
		
		log.readFile();

		log.findNumberofFiles();

	    log.findFileswithMostRevisions();

	    log.findFilewithHighestUsers();

	    log.findEarliestCommit();

	    log.findUserwithHighestCommit();

	     log.displayFileWiseData();

	
	} // end of main

	
				
}
