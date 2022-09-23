package oop.ex6;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reads whole file content to an array
 */
public class FileReader {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** Array contains all input file lines */
	private static String[] fileContent;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Construct A sjava file reader.
	 * Reads the complete file (all lines) at once into a string array.
	 * @param inputFile file to read
	 * @throws IOException Error in handling source file
	 */
	public FileReader(String inputFile) throws IOException {
		try {
			File input = new File(inputFile);
			FileInputStream fileInputStream = new FileInputStream(input);
			byte[] inputData = new byte[(int) input.length()];
			fileInputStream.read(inputData);
			fileInputStream.close();
			fileContent = new String(inputData).split("\n");

		} catch (Exception e) {
			throw new IOException();
		}
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * gets the file content array
	 * @return array of strings contains all file's lines
	 */
	public String[] getFileContent() {
		return fileContent;
	}

}
