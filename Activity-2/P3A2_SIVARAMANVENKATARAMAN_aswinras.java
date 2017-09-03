/*

Author Name: Aswin ram S.V.

Creation Date: 08/03/2017

Modification Date: 08/5/2017

Description: 1) The file writes the following the information to a csv file of user's choice
					1) Name of the file
					2) Number of commits made to that file
					3) First date of commit
					4) Last date of commit

			 2) It also has a class named P3A1_SIVARAMANVENKATARAMAN_File_aswinras, that describes details pertaining to a file
             	object such as earliest commitDate, username, number of commits by that user etc.	

*/

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.util.*;

import java.util.Iterator;

import java.text.SimpleDateFormat;  

import java.util.Date;  

import java.text.ParseException; 

import com.opencsv.*;



// The below class describes a file object, the attributes of which have been mentioned in the program description


class P3A2_SIVARAMANVENKATARAMAN_File_aswinras
{

	String fileName;

	int numberofCommits;

	private Date earliestCommitDate;

	private Date lastCommitDate;



	public P3A2_SIVARAMANVENKATARAMAN_File_aswinras()
	{


	}


	public P3A2_SIVARAMANVENKATARAMAN_File_aswinras(String fileName,Date earliestCommitDate,Date lastCommitDate,int commits)
	{
		this.fileName=fileName;

		this.earliestCommitDate=earliestCommitDate;

		this.lastCommitDate=lastCommitDate;

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

	public void setLatestCommitDate(Date lastCommitDate)
	{
		this.lastCommitDate=lastCommitDate;
	}

	public Date getLatestCommitDate()
	{
		return lastCommitDate;
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



public class P3A2_SIVARAMANVENKATARAMAN_aswinras
{

	// The file objects obtained from the file data stored in the log file is stored in the below arraylist

	ArrayList<P3A2_SIVARAMANVENKATARAMAN_File_aswinras> listOfFiles=new ArrayList<P3A2_SIVARAMANVENKATARAMAN_File_aswinras>();

	ArrayList<Date> dates=new ArrayList<Date>();

	private String inputFilename;

	private String outputFilename;

	private String fileMarker="Working file";

	private String dateMarker="date:";

	private String revisionMarker="revision 1.";

	private String endofFileContent="=============================================================================";

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

	public P3A2_SIVARAMANVENKATARAMAN_aswinras()
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

		String dateContent;

		try
     {

     	fr=new FileReader(inputFilename);

     	br=new BufferedReader(fr);

     	String fileName=new String();

     	Date minDate,maxDate,newDate;

     	int numberOfcommits=0;


		while((currentLine=br.readLine())!=null)
	{
			if(!currentLine.contains(endofFileContent))
		{	

			if(currentLine.contains(revisionMarker))
			{
				numberOfcommits++;	
			}

			if(currentLine.contains(fileMarker))
			{
				lineContent=currentLine.split(":");

				fileName=lineContent[1];
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

		}
     }

		else
	  {

	  	minDate=Collections.min(dates);

	  	maxDate=Collections.max(dates);

	  	addFileRecord(fileName,minDate,maxDate,numberOfcommits);

	  	numberOfcommits=0;
	  	
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


public void addFileRecord(String fileName,Date earliestCommitDate,Date lastCommitDate,int commits)
{
	P3A2_SIVARAMANVENKATARAMAN_File_aswinras file=new P3A2_SIVARAMANVENKATARAMAN_File_aswinras(fileName,earliestCommitDate,lastCommitDate,commits);

	listOfFiles.add(file);
}

// The below function writes data about the files to csv file 
// CSVWriter class's object is responsible for writing the data to csv file

public void writeToFile()
{

	CSVWriter csvWriter = null;

	String[] row;

	try
		{
			//Create CSVWriter for writing to Employee.csv 
			csvWriter = new CSVWriter(new FileWriter(outputFilename));

			row = new String[]{"File Name","Number of commits","First date of commit","Last date of commit"};

			csvWriter.writeNext(row);

			for(int i=0;i<listOfFiles.size();i++)
			{

				row = new String[]{listOfFiles.get(i).getFileName(),String.valueOf(listOfFiles.get(i).getnumberofCommits()),df.format(listOfFiles.get(i).getEarliestCommitDate()),df.format(listOfFiles.get(i).getLatestCommitDate())};

			    csvWriter.writeNext(row);

			}	

		}	

		catch(Exception ee)
		{
			ee.printStackTrace();
		}

		finally
		{
			try
			{
				//closing the writer
				csvWriter.close();
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}


}

public static void main(String[] args)
{

	P3A2_SIVARAMANVENKATARAMAN_aswinras log=new P3A2_SIVARAMANVENKATARAMAN_aswinras();

	boolean correctInput=false;

		Scanner sc=new Scanner(System.in);

		try
		{

			while(!correctInput)
		  {		
			System.out.println("\nPlease enter the input file log file [Hint: emacs.log]");

			log.inputFilename=sc.next();

			System.out.println("\nPlease enter the output csv file's name with [.csv extension]");

			log.outputFilename=sc.next();

			if(log.inputFilename.equals("emacs.log") && (log.outputFilename.contains(".csv") && log.outputFilename.length()>4))
			{	
				correctInput=true;

				log.inputFilename=+log.inputFilename;

			}	

			else
            {
				correctInput=false;

				System.out.println("\nPlease enter the correct input/output filename: [Input filename=emacs.log / Output fileName=(.csv)]");
            }

		   } // while ends here

		  } // try ends here

		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}	// catch ends here

		log.readFile();

		log.writeToFile();


}	

}