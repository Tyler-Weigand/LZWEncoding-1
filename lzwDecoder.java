import java.io.*;
import java.util.*;

public class lzwDecoder {
	private HashMap<Integer, String> encodingTable;
	final static int INITIAL_TABLE_SIZE = 128;
	final static int MAX_TABLE_SIZE = 55296; //the maximum size of our table
	static int[] codeRecency = new int[MAX_TABLE_SIZE];
	static ArrayDeque<CodeNode> recentQueue = new ArrayDeque<CodeNode>();
	
	public lzwDecoder() {}
	
	public void decode(String inputFileName, String outputFileName) throws IOException {
		BufferedReader br = new BufferedReader (new FileReader(inputFileName));
		encodingTable = new HashMap<Integer, String>();
		PrintWriter pw = new PrintWriter(new File(outputFileName));
		//Fills table with the initial characters
		for(int a = 0; a < INITIAL_TABLE_SIZE; a++) {
			encodingTable.put(a, new String((char)a + ""));
		}
		
		//First iteration, priming variables to loop
		int nextValue = INITIAL_TABLE_SIZE;
		int currentCode = br.read(); //the char being decoded, represents an encoded string
		String previousStr = ""; //represents the most recently printed set of chars
		String currentStr = ""; //represents the set of chars to be printed & added to the hashmap
		
		//Puts first combination (the first two letters) into the hashmap
		currentStr = (char) currentCode +"";
		currentCode = br.read();
		currentStr += (char) currentCode +"";
		encodingTable.put(nextValue, currentStr);
		pw.print(currentStr);
		
		//Resets strings to accept the next encoding
		previousStr = currentStr.substring(1);
		currentStr = "";
		nextValue++;
		
		while(br.ready()) {
			CodeNode tempNode = new CodeNode("",'a');
			if (nextValue >= MAX_TABLE_SIZE) {
				tempNode = recentQueue.pollFirst();
				while(codeRecency[(int)tempNode.code] > 1) {
					codeRecency[tempNode.code]-=1;
					tempNode = recentQueue.pollFirst();
				}
			}
			if (previousStr.length() > 1){
				recentQueue.add(new CodeNode(previousStr, (char)currentCode));
				codeRecency[currentCode]++;
			}
			currentCode = br.read();
			if (encodingTable.containsKey(currentCode)) { //if current code is already in the hashmap, add previous characters + 1st letter of current to hashmap
				if (nextValue < MAX_TABLE_SIZE) {
					currentStr = encodingTable.get(currentCode);
					encodingTable.put(nextValue, previousStr + currentStr.substring(0,1));
					recentQueue.add(new CodeNode(previousStr + currentStr.substring(0,1), (char)nextValue));
				} else {
					currentStr = encodingTable.get(currentCode);
					encodingTable.remove(new Integer((int)tempNode.code));
					encodingTable.put(new Integer((int)tempNode.code), previousStr + currentStr.substring(0,1));
					recentQueue.add(new CodeNode(previousStr + currentStr.substring(0,1), tempNode.code));

				}
			} 
			else { //otherwise, add the previous string + its first letter
				if (nextValue < MAX_TABLE_SIZE) {
					currentStr = previousStr + previousStr.substring(0,1);
					encodingTable.put(nextValue, currentStr);
					recentQueue.add(new CodeNode(currentStr, (char)nextValue));
				} else {
					currentStr = previousStr + previousStr.substring(0,1);
					encodingTable.remove(new Integer((int)tempNode.code));
					encodingTable.put(new Integer((int)tempNode.code), currentStr);
					recentQueue.add(new CodeNode(currentStr, tempNode.code));
				}
			}


			pw.print(currentStr);//print the current string
			
			//Resets strings to accept the next encoding
			previousStr = encodingTable.get(currentCode);
			currentStr = "";
			nextValue++;
		}
		pw.close();
		br.close();
	}
}