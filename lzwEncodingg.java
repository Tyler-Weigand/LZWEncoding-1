//submit the link to the github repo
import java.util.*;
import java.io.*;
public class lzwEncodingg 
{
	

	/*public static String process(int x, String prev)
	{
		char current = (char)x;
		if(prev.equals(""))
		{
			prev = current;
			return prev
		}

		if(table.contains(prev))
	}*/

	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> table = new HashMap<String, Integer>();
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