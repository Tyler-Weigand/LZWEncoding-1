import java.io.*;
import java.util.*;

public class lzwDecoder {
	private HashMap<Integer, String> encodingTable;
	final static int INITIAL_TABLE_SIZE = 128;
	
	public lzwDecoder() {}
	
	public void decode(String inputFileName, String outputFileName) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader(inputFileName));
		encodingTable = new HashMap<Integer, String>();
		PrintWriter pw = new PrintWriter(new File(outputFileName));
		
		for(int a = 0; a < INITIAL_TABLE_SIZE; a++) {
			encodingTable.put(a, new String((char)a + ""));
		}
		int nextValue = INITIAL_TABLE_SIZE;
		
		int currentCode = br.read();
		String previousStr = "";
		String currentStr = "";
		
		currentStr = (char) currentCode +"";
		currentCode = br.read();
		currentStr += (char) currentCode +"";
		
		encodingTable.put(nextValue, currentStr);
		pw.print(currentStr);
		
		previousStr = currentStr.substring(1);
		currentStr = "";
		nextValue++;
		
		while(br.ready()) {
			currentCode = br.read();
			if (encodingTable.containsKey(currentCode)) {
				currentStr = encodingTable.get(currentCode);
				encodingTable.put(nextValue, previousStr + currentStr.substring(0,1));
			} else {
				currentStr = previousStr + previousStr.substring(0,1);
				encodingTable.put(nextValue, currentStr);
			}
			pw.print(currentStr);

			previousStr = encodingTable.get(currentCode);
			currentStr = "";
			nextValue++;
		}
		pw.close();
		br.close();
	}
}