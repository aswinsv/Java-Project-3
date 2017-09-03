import java.io.FileWriter;

import com.opencsv.*;


public class WriteCSV_OpenCSV 
{
	public static void main(String args[])
	{
		CSVWriter csvWriter = null;
		try
		{
			//Create CSVWriter for writing to Employee.csv 
			csvWriter = new CSVWriter(new FileWriter("Months.csv"));
			//row1
			String[] row = new String[]{"month","number of days"};
			csvWriter.writeNext(row);
			//row2
			row = new String[]{"January","31"};
			csvWriter.writeNext(row);

			row = new String[]{"February","28"};
			csvWriter.writeNext(row);

			row = new String[]{"March","31"};
			csvWriter.writeNext(row);

			row = new String[]{"April","30"};
			csvWriter.writeNext(row);

			row = new String[]{"May","31"};
			csvWriter.writeNext(row);

			row = new String[]{"June","30"};
			csvWriter.writeNext(row);

			row = new String[]{"July","31"};
			csvWriter.writeNext(row);

			row = new String[]{"August","30"};
			csvWriter.writeNext(row);

			row = new String[]{"September","30"};
			csvWriter.writeNext(row);

			row = new String[]{"October","31"};
			csvWriter.writeNext(row);

			row = new String[]{"November","30"};
			csvWriter.writeNext(row);

			row = new String[]{"December","30"};
			csvWriter.writeNext(row);
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
}
