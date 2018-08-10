import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;

public class MoveToFront {

	private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	char[] pos = initial();
    	while (!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		char index = 0;
    		while (pos[index] != c) {
    			index++;
    		}
    		for (char i = index; i > 0; i--) {
    			pos[i] = pos[i - 1];
    		}
    		pos[0] = c;
    		BinaryStdOut.write(index);
    	}
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	char[] pos = initial();
    	while (!BinaryStdIn.isEmpty()) {
    		char index = BinaryStdIn.readChar();
    		char c = pos[index];
    		for (char i  = index; i > 0; i--) {
    			pos[i] = pos[i - 1];
    		}
    		pos[0] = c;
    		BinaryStdOut.write(c, 8);
    	}
    	BinaryStdOut.close();
    }

    private static char[] initial() {
    	char[] pos = new char[R];
    	for (char i = 0; i < R; i++) {
    		pos[i] = i;
    	}
    	return pos;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
    	if (args[0].equals("-")) {
    		encode();
    	} else if (args[0].equals("+")) {
    		decode();
    	}
    }
}