package filesprocessing;

import filesprocessing.Filter.FilterFactory;
import filesprocessing.Order.FileSort;
import filesprocessing.Order.OrderFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Represents a single section of command file
 */
public class Section {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	/** The delimiter between command parameters */
	private final static String DELIMITER = "#";

	/** The default order: by absolute name, in ascending order */
	private final static String DEFAULT_ORDER = "abs";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** The filer command line before parsing */
	private final String filterCommand;

	/** The parsed parts of the filter command */
	private final int filterLine;
	private String filterName;
	private String[] filterValues = new String[0];
	private boolean negatedFilter = false;

	/** The order command line before parsing */
	private final String orderCommand;

	/** The parsed parts of the order command */
	private final int orderLine;
	private String orderName = DEFAULT_ORDER;
	private boolean reversedOrder = false;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs a new section
	 * @param filterCmd filter command
	 * @param filterLineNum filter command line number
	 * @param orderCmd order command
	 * @param orderLineNum order command line number
	 */
	Section(String filterCmd, int filterLineNum,
			String orderCmd, int orderLineNum) {
		filterCommand = filterCmd;
		filterLine = filterLineNum;
		orderCommand = orderCmd;
		orderLine = orderLineNum;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Executing the current section commands
	 * @param rawFiles list of all files before processing
	 */
	void executeCommands(File[] rawFiles) {
		ArrayList<File> filteredFiles = new ArrayList<>();
		// filter files array
		FileFilter sectionFilter = getFilter();
		for (File pathname: rawFiles) {
			if (sectionFilter.accept(pathname))
				filteredFiles.add(pathname);
		}
		// sort files
		FileSort sorter = new FileSort(getOrder());
		if (filteredFiles.size() > 0)
			sorter.quickSort(filteredFiles, 0, filteredFiles.size() - 1);
		// print files names
		for (File pathname: filteredFiles)
			System.out.println(pathname.getName());
	}

	/**
	 * Gets the suitable file filter
	 * @return the file filter corresponding to the filter command
	 */
	private FileFilter getFilter() {
		try {
			parseFilter(filterCommand, filterLine);
			return FilterFactory.select(filterName, filterValues, filterLine, negatedFilter);
		} catch (TypeIError exception) {
			System.err.println(exception.getMessage());
			// Default filter (="all")
			return pathname -> true;
		}
	}

	/**
	 * Gets the suitable file order
	 * @return the file order corresponding to the order command
	 */
	private Comparator<File> getOrder() {
		try {
			// if order command is an empty line return default order (i.e. abs)
			if (orderCommand.equals("") && orderLine == -1)
				return Comparator.comparing(File::getAbsolutePath);
			parseOrder(orderCommand, orderLine);
			return OrderFactory.select(orderName, orderLine, reversedOrder);
		} catch (TypeIError exception) {
			System.err.println(exception.getMessage());
			// Default order (="abs")
			return Comparator.comparing(File::getAbsolutePath);
		}
	}

	/**
	 * Parsing the filter command.
	 * @param filterCommand filter command of this section
	 * @param filterLineNum filter command line
	 * @throws TypeIError WARNING
	 */
	private void parseFilter(String filterCommand, int filterLineNum) throws TypeIError {
		String[] commandParts = filterCommand.split(DELIMITER);
		// filter command is composed of 1-4 parts (includes NOT)
		if (commandParts.length < 1 || commandParts.length > 4) {
			throw new TypeIError(filterLineNum);
		}
		filterName = commandParts[0];
		if (commandParts.length > 1) {
			negatedFilter = commandParts[commandParts.length-1].equals("NOT");
			int argsNum = (negatedFilter) ? commandParts.length-2 : commandParts.length-1;
			filterValues = Arrays.copyOfRange(commandParts, 1, argsNum+1);
		}
	}

	/**
	 * Parsing the order command.
	 * @param orderCommand order command of this section
	 * @param orderLineNum order command line
	 * @throws TypeIError WARNING
	 */
	private void parseOrder(String orderCommand, int orderLineNum) throws TypeIError {
		String[] commandParts = orderCommand.split(DELIMITER);
		// If there is an order command, it is composed of 1-2 parts (includes REVERSE)
		if (commandParts.length == 1) {
			orderName = commandParts[0];
		} else if (commandParts.length == 2) {
			orderName = commandParts[0];
			reversedOrder = commandParts[1].equals("REVERSE");
			// the reverse part (2nd one) not equals to "REVERSE"
			if (!reversedOrder)
				throw new TypeIError(orderLineNum);
		} else if (commandParts.length > 2) {
			throw new TypeIError(orderLineNum);
		}
	}
}
// End of Section Class