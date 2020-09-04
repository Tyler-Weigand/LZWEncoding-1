//submit the link to the github repo
import java.util.*;
import java.io.*;
public class lzwEncoding 
{
	public static HashMap<String,Integer> init(HashMap<String,Integer> table)
	{
		for(int a = 0; a < 126; a++)
		{
			char current = (char)a;
			table.put(current+"",a);
		}
		return table;
	}

	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		init(table);
		BufferedReader br = new BufferedReader(new FileReader("lzw.txt"));
		int current = br.read();
		String build = "";
		build += (char)current;
		String encoding = "";
		int num = 26;
		while(current != -1)
		{
			current = br.read();
			String temp = build + current;
			System.out.println(table.containsKey(temp));
			if(!table.containsKey(temp))
			{
				encoding += table.get(build);
				table.put(temp, num);
				num++;
				build = "";
			}
			build += current;
		}
		System.out.println(encoding);
	}
}