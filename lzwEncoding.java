//submit the link to the github repo
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class IZWEncoding{
	publc main 
	//an arraylist of every letter in the txt file
	public ArrayList letArray;


	//reading in the original file 
	public ArayList letters (){
	String file1 = "encodingfile.txt";
	FileReader fr = new FileReader ("encodingfile.txt");
	BufferedReader br = new BufferedReader (fr);
	for (int i = 0; i < file1.length (); i++ ){
		letArray.add (file1.toString (i, i+1)); 
	}

	}

}