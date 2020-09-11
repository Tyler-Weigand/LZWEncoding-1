import java.util.*;
import java.io.*;
public class lzwEncoding 
{
	public static HashMap<String,Character> init(HashMap<String,Character> table)
	{
		// fill the table with the standard ascii 1-128
		for(int a = 0; a < 128; a++)
		{
			char current = (char)(a);
			// (pattern (string) + corresponding ascii (char))
			table.put(current+"",current);
		}
		return table;
		//hello
	}

	public static void encode (String input, String output) 
	{
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<String, Character> table = new HashMap<String, Character>();
		// holds the encoded message
		StringBuilder encoding = new StringBuilder("");

		// fill the table with the standard ascii 1-128
		init(table);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(input));
			int current = br.read();

			// last char of previous pattern
			String prev = (char)current + "";

			// the next available ascii/table slot
			int num = 128;

			while(current != -1)
			{
				current = br.read();
				// current portion of the text being scanned for new patterns
				String temp = prev + (char)current;

				// pattern not found
				if(!table.containsKey(temp))
				{
					// encode previous
					encoding.append(table.get(prev));

					// max 256 bc the extended ascii table ends at 255, so we can't represent anything past 255
					// add to the table
					if(num < 256)
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
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			// write out the encoding
			bw.write(encoding.toString());
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
	}
}
