import java.util.*;
import java.io.*;

public class lzwEncoding 
{
	final static int INITIAL_TABLE_SIZE = 128;//creates a final variable because 128 is a "magic number" that appears multiple times
	final static int MAX_TABLE_SIZE = 55296; //the maximum size of our table
	int[] codeRecency = new int[MAX_TABLE_SIZE];
	public static HashMap<String,Character> init(HashMap<String,Character> table)
	{
		// fill the table with the standard ascii 1-128
		for(int a = 0; a < INITIAL_TABLE_SIZE; a++)
		{
			char current = (char)(a);
			// (pattern (string) + corresponding ascii (char))
			table.put(current+"",current);
		}
		return table;
	}

	public static void encode (String input, String outputFileName) 
	{
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<String, Character> table = new HashMap<String, Character>();
		
		// fill the table with the standard ascii 1-128
		init(table);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(input));
			
			//prints directly to the outputFile instead of using a stringbuilder so it runs faster
			File file = new File(outputFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			
			int current = br.read();

			// last char of previous pattern
			String prev = (char)current + "";

			// the next available ascii/table slot
			int num = INITIAL_TABLE_SIZE;

			while(current != -1)
			{
				current = br.read();
				// current portion of the text being scanned for new patterns
				String temp = prev + (char)current;

				// pattern not found
				if(!table.containsKey(temp))
				{
					// encode previous
					//prints to output file instead of appending
					pw.print(table.get(prev));
					
					// max 256 bc the extended ascii table ends at 255, so we can't represent anything past 255
					//the larger the table the more it compresses, so we increased the max table size to a max of 2^15 (32768) (2^16 created some unreadable chars)
					// add to the table
					if(num < MAX_TABLE_SIZE)
						table.put(temp, (char)num);

					// increase the next available ascii/table slot
					num++;

					// reset bc we've already encoded the previous
					prev = "";
				}
				// add to or set the previous
				prev += (char)current;
			}
			br.close();
			pw.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
	}
}
