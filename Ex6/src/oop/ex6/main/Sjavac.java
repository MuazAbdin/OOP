package oop.ex6.main;

import oop.ex6.verifier.FileVerifier;

/**
 * Main class: runs the verifier on the source file.
 */
public class Sjavac {

	/** wrong num of args */
	private static final String WRONG_USG = "USAGE: java oop.ex6.main.Sjavac <source_file_name>";

	/** valid num of args */
	private static final int NUM_OF_ARGS = 1;

	/**
	 * main method: starts the program
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		if (args.length != NUM_OF_ARGS){
			System.err.println(WRONG_USG);
			System.exit(1);
		}
		String sourceFile = args[0];
		FileVerifier.verifyCode(sourceFile);
	}
}