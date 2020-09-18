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
		HashMap<String, Integer> usedEncodings = new HashMap<String, Integer>(); //List of used encodings, number inside is always equal to 0.
		PrintWriter pw = new PrintWriter(new File("output"));
		int current = 0;
		for(int a = 0; a < 128; a++) {
			current = a;
			encodingtable.put(current, ((char)current + ""));
			usedEncodings.put(((char)current + ""), 0);
		}
		int code; //current code
		current++;
		String encoding = ""; //Tracks the string of letters to add to the encoding table
		while(br.ready()) {
			code = br.read();
			if (encodingtable.get(code) == null) {
				encoding+=(encoding.substring(0, 1));
				encodingtable.put(current, encoding);
				usedEncodings.put(encoding, 0);
				current++;
			}
			else
				encoding+=(encodingtable.get(code));
			if (!(usedEncodings.containsKey(encoding))) {
				encodingtable.put(current, encoding);
				usedEncodings.put(encoding, 0);
				current++;
				encoding = (encoding.substring(encoding.length()-1));
			}
			pw.print(encodingtable.get(code));
		}
		pw.close();
		br.close();
	}
}