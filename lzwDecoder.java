import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class lzwDecoder {
	public lzwDecoder() {}
	
	public void decode(String inputFileName) throws FileNotFoundException {
		BufferedReader br = new BufferedReader (new FileReader(inputFileName));
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		PrintWriter pw = new PrintWriter(new File("output"));
		for(int a = 0; a < 128; a++) {
			int current = (char)(a);
			map.put(current + "", current);
		}
		
	}
}