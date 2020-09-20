import java.io.IOException;

public class Tester {
	public static void main (String[] args) throws IOException {
		lzwEncoding x = new lzwEncoding();
		x.encode("big.txt", "encoded.txt");
		lzwDecoder y = new lzwDecoder();
		y.decode("encoded.txt", "output.txt");
	}
}