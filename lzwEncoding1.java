//submit the link to the github repo
import java.util.*;
import java.io.*;
public class lzwEncoding 
{
	public static HashMap<String,Integer> init(HashMap<String,Integer> table)
	{
		for(int a = 0; a < 128; a++)
		{
			char current = (char)(a);
			// (pattern (string) + corresponding ascii (char))
			table.put(current+"",a);
		}
		return table;
	}

	public static void main(String[] args) throws IOException
	{
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		// holds the encoded message
		StringBuilder encoding = new StringBuilder("");

		// fill the table with the standard ascii 1-128
		init(table);

		BufferedReader br = new BufferedReader(new FileReader("lzw.txt"));
		int current = br.read();

		// last char of previous pattern
		String prev = (char)current + "";

		// the next available ascii
		int num = 128;

		while(current != -1)
		{
			current = br.read();
			// current portion of the text we're dealing with 
			String temp = prev + (char)current;

			// new pattern!
			if(!table.containsKey(temp))
			{
				// encode previous
				if(encoding.length() > 0)
					encoding.append(", ");
				encoding.append(table.get(prev));
				
				// add to the table
				table.put(temp, num);

				num++;
				// reset bc we've already encoded the previous
				prev = "";
			}
			// add to or set the previous
			prev += (char)current;
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter("lzwEncoded.txt"));
		bw.write(encoding.toString());
		bw.close();
	}
}
