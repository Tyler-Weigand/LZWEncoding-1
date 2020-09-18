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
		HashMap<Integer, StringBuffer> encodingtable = new HashMap<Integer, StringBuffer>();
		PrintWriter pw = new PrintWriter(new File("output"));
		int current = 0;
		for(int a = 0; a < 128; a++) {
			current = a;
			encodingtable.put(current, new StringBuffer((char)current + ""));
		}
		int code; //current code
		StringBuffer encoding = new StringBuffer(); //Tracks the string of letters to add to the encoding table
		while(br.ready()) {
			code = br.read();
			if (encodingtable.get(code) == null) {
				encoding.append(encoding.substring(0, 1));
				encodingtable.put(current, encoding);
				current++;
			}
			else
				encoding.append(encodingtable.get(code) + "");
			if (!(encodingtable.containsValue(encoding))) {
				encodingtable.put(current, encoding);
				current++;
				encoding = new StringBuffer(encoding.substring(encoding.length()-1));
			}
			pw.print(encodingtable.get(code));
		}
		pw.close();
		br.close();
	}
}