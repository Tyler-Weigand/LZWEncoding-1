import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class lzwDecoder {
	public lzwDecoder() {}
	
	public void decode(String inputFileName) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader(inputFileName));
		HashMap<Integer, String> encodingtable = new HashMap<Integer, String>();
		PrintWriter pw = new PrintWriter(new File("output"));
		int current = 0;
		for(int a = 0; a < 128; a++) {
			current = a;
			encodingtable.put(current, (char)current + "");
		}
		int code; //current code
		String encoding = ""; //Tracks the string of letters to add to the encoding table
		while(br.ready()) {
			code = br.read();
			encoding+=(char)code + "";
			if (!(encodingtable.containsValue(encoding))) {
				encodingtable.put(current, encoding + "");
				current++;
				encoding = encoding.substring(encoding.length() - 1);
			}
			if (encodingtable.containsKey(code)) 
				pw.print(encodingtable.get(code));
			
		}
	}
}