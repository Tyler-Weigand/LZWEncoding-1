//submit the link to the github repo
import java.util.*;
import java.io.*;
public class lzwEncodingUnicode
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

	/*public static void decodeUni() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("lzwEncoded.txt"));
		int current = br.read();
		while(current != -1)
		{
			System.out.println(current);
			current=br.read();
		}
	}*/

	public static void encode(String input, String output) throws IOException
	{
		// the table containing the pattern and corresponding ascii - "a" -> 'a'
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		// holds the encoded message
		StringBuilder encoding = new StringBuilder("");

		// fill the table with the standard ascii 1-128
		init(table);

		BufferedReader br = new BufferedReader(new FileReader(input));
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
				//if(encoding.length() > 0)
				//	encoding.append(", ");
				encoding.append(Character.toString((char)(int)table.get(prev)));
				// add to the table
				if(num < 1111998)
					table.put(temp, num);
				num++;

				// reset bc we've already encoded the previous
				prev = "";
			}
			// add to or set the previous
			prev += (char)current;
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		bw.write(encoding.toString());
		bw.close();

		//decode();
	}
	public static void main (String[] args) throws IOException
	{
		encode("lzw.txt", "lzwEncoded1.txt");
	}
}
