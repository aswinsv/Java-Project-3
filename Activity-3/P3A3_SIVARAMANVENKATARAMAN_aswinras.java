/*

Author Name: Aswin ram S.V.

Creation Date: 08/03/2017

Description Date: 08/5/2017

Description: 1) The below file reads the log file and writes to a csv the following details:
					1) Year
					2) Top 20% users with highest commits

*/

import java.util.*;

import java.io.*;

import com.opencsv.*;

import java.math.*;

public class P3A3_SIVARAMANVENKATARAMAN_aswinras
{


	private int year=0;

	// the below variable declares the input source file
	private String inputFilename;

	// the below variable declares the output file where we are going to write the output

	private String overallreportFileName;

	private String dateMarker="date:";

	private String authorMarker="author:";

	private float subSetCount=0.2F;
	
	private int displayCount=0;

	// the below hashmap is used to user name along with the number of commits made by user

	private Map<String,Integer> userCommitcount=new HashMap<String,Integer>();

	// the below variable declares a CSVWriter object, that is responsible for writing report data to output csv file
	private CSVWriter csvWriter = null;


	public P3A3_SIVARAMANVENKATARAMAN_aswinras()
	{



	}

	/* 1) The function below generates the following data year wise:
			1) Year number
			2) Top 20% users with maximum commits for that respective year

	   2) It writes the output to a .csv file mentioned by the user	


	*/	

	public void overallreport()
	{

		System.out.println("Inside overall report function");

		BufferedReader br=null;

		FileReader fr=null;

		int n=0;

		String currentLine;

		String[] lineContent;

		String[] row;

		String dateContent,authorName;



		// This array is used for looping through year by year and finding top 20% users in terms of number of commits

		int[] years=new int[]{2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015};


		// the below array is used for csv's column headers
		String[] columnHeaders=new String[]{"Year","User(s) with Highest Commit"};

	try
  {
 	csvWriter=new CSVWriter(new FileWriter(overallreportFileName));

 	csvWriter.writeNext(columnHeaders);

 }	

  catch(Exception ex)
    {
    	System.out.println(ex.getMessage());
    }

for(int i=0;i<years.length;i++)
{

userCommitcount.clear();


	try
     {

     	fr=new FileReader(inputFilename);

     	br=new BufferedReader(fr);

     	// The below while loop reads the file line by line

     		while((currentLine=br.readLine())!=null)
	    {

     	if(currentLine.contains(String.valueOf(years[i])) && currentLine.contains(authorMarker))
     	{
     		    lineContent=currentLine.split(";");

				authorName=lineContent[1].replace("author:","");


				if(userCommitcount.containsKey(authorName))
				{
					userCommitcount.put(authorName, userCommitcount.get(authorName) + 1);
				}	

				else
				{
					userCommitcount.put(authorName,1);
				}

     	} // if loop	

     } 	// end of while loop


     	// the below sortedMap is used to store username,along with commit Count in decreasing order

        Map<String, Integer> sortedMap = sortByValue(userCommitcount);
      
        displayCount=(int)Math.ceil((subSetCount*sortedMap.size()));

        if(displayCount<1)
        {
        	displayCount=1;
        }

        
         int index=1;

         ArrayList<String> values=new ArrayList<String>();


        	for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {

         	if(! (index>displayCount))
            {
               	index++;

            	values.add(entry.getKey());

            	
            }
          }

           String res = String.join(",", values);

          row = new String[]{String.valueOf(years[i]),res};

           csvWriter.writeNext(row); 

        } // try

        catch(Exception ex)
        {
        	System.out.println(ex.getMessage());
        }
        
      }    


         
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



        // the below function sorts the map by value in descending order and returns the sorted map
        // Parameter- A hashamp having username as key and number of commits of that user as value

	 public Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


	public static void main(String[] args)
	{


		P3A3_SIVARAMANVENKATARAMAN_aswinras log= new P3A3_SIVARAMANVENKATARAMAN_aswinras();


		Scanner sc=new Scanner(System.in);


		boolean correctInput=false;

		boolean overallInput=false;

		boolean exit=false;

		int choice=0;



			while(!correctInput)
		  {		
			System.out.println("\nPlease enter the input file log file [Hint: emacs.log]");

			log.inputFilename=sc.next();

			System.out.println("\nPlease enter the output csv file's name with [.csv extension]");

			log.overallreportFileName=sc.next();

			if(log.inputFilename.equals("emacs.log") && (log.overallreportFileName.contains(".csv") && log.overallreportFileName.length()>4))
			{	
				correctInput=true;

				log.overallreport();

			}	

			else
            {
				correctInput=false;

				System.out.println("\nPlease enter the correct input/output filename: [Input filename=emacs.log / Output fileName=(.csv)]");
            }

		   } // while ends here




	}
}