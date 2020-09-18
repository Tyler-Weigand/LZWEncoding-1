import java.util.*;
import java.io.*;

public class lzwEncoding 
{
	final static int INITIAL_TABLE_SIZE = 128;//creates a final variable because 128 is a "magic number" that appears multiple times
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
		//hello
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
					//encoding.append(table.get(prev));
					//prints to output file instead of appending
					pw.print(table.get(prev));
					
					// max 256 bc the extended ascii table ends at 255, so we can't represent anything past 255
					//^^ changed to 65536 bc thats the largest char, the larger the table the more it compresses
					// add to the table
					if(num < 65536)
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
