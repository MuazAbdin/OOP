package filesprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A program thar filters the files in a given directory according to various conditions,
 * and orders the filenames that passed the filtering according to various properties.
 */
public class DirectoryProcessor {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	final static String FILTER_TITLE = "FILTER";
	final static String ORDER_TITLE = "ORDER";

	private final static String USAGE_ERROR = "Usage: java filesprocessing.DirectoryProcessor " +
											  "<sourcedir> <commandfile>";
	private final static String ACCESS_COMMAND_FILE_ERROR = "Can not access the Commands File.";
	private final static String SOURCE_DIRECTORY_ERROR = "Can not access the Source Directory.";
	private final static String FILTER_MISSING = "FILTER title is missing.";

	private final static String ORDER_MISSING = "ORDER title is missing.";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** A list of all files in the source directory */
	private File[] rawFiles;

	/** A list of all lines in the command file */
	private List<String> commandFileLines;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs the directory processor.
	 * @param sourceDir a directory name, in the form of a path.
	 * @param commandFile a name of a file, also in the form of a relative or absolute path.
	 * @throws TypeIIError Type II exception of processing
	 */
	DirectoryProcessor(String sourceDir, String commandFile) throws TypeIIError {
		try {
			File sourceDirPath = new File(sourceDir);
			File commandFilePath = new File(commandFile);
			if (!sourceDirPath.exists())
				throw new TypeIIError(SOURCE_DIRECTORY_ERROR);
			if (!commandFilePath.exists())
				throw new TypeIIError(ACCESS_COMMAND_FILE_ERROR);
			rawFiles = sourceDirPath.listFiles(File::isFile);
			commandFileLines = Files.readAllLines(Paths.get(commandFile));
		} catch (IOException e) {
			throw new TypeIIError(ACCESS_COMMAND_FILE_ERROR);
		} catch (NullPointerException e) {
			throw new TypeIIError(SOURCE_DIRECTORY_ERROR);
		}
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Creates an ArrayList of all sections of the command file.
	 * @return ArrayList of all sections of the command file.
	 * @throws TypeIIError ERROR Exceptions.
	 */
	ArrayList<Section> createSections() throws TypeIIError {
		ArrayList<Section> sections = new ArrayList<>();
		int idx = 0;
		try {
			while (idx < commandFileLines.size()) {
				String filterCommand, orderCommand;
				int filterLineNum, orderLineNum;
				// Check FILTER sub-section
				if (!commandFileLines.get(idx).equals(FILTER_TITLE))
					throw new TypeIIError(FILTER_MISSING);
				filterCommand = commandFileLines.get(idx + 1);
				filterLineNum = idx + 2;
				// Checks ORDER sub_section
				if (!commandFileLines.get(idx + 2).equals(ORDER_TITLE))
					throw new TypeIIError(ORDER_MISSING);
				// No command in ORDER sub-section
				if ((idx + 3 >= commandFileLines.size()) ||
					(commandFileLines.get(idx + 3).equals(FILTER_TITLE))) {
					orderCommand = "";
					orderLineNum = -1;
					idx += 3;  // section is 3 lines long
				} else {
					orderCommand = commandFileLines.get(idx + 3);
					orderLineNum = idx + 4;
					idx += 4; // section is 4 lines long
				}
				sections.add(new Section(filterCommand, filterLineNum,
										 orderCommand, orderLineNum));
			}
		} catch (IndexOutOfBoundsException e) {
			// there is only the FILTER title line in this section
			throw new TypeIIError(FILTER_MISSING);
		}
		return sections;
	}

	/**
	 * Executes sections one by one
	 * @throws TypeIIError ERROR Exceptions.
	 */
	private void execute() throws TypeIIError {
		ArrayList<Section> sections = createSections();
		for (Section section: sections) {
			section.executeCommands(rawFiles);
		}
	}

	// ##################### //
	// #### MAIN METHOD #### //
	// ##################### //

	/**
	 * Main function that runs the program
	 * @param args command line arguments to run the program
	 */
	public static void main(String[] args) {
		try {
			if (args.length != 2)
				throw new TypeIIError(USAGE_ERROR);
			DirectoryProcessor processor = new DirectoryProcessor(args[0], args[1]);
			processor.execute();
		} catch (TypeIIError exception) {
			System.err.println(exception.getMessage());
		}
	}
}
