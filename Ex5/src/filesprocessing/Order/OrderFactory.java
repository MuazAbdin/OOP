package filesprocessing.Order;

import filesprocessing.TypeIError;

import java.io.File;
import java.util.Comparator;

/**
 * A factory class for orders used in Directory Processor
 */
public class OrderFactory {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	private static final int EQUAL = 0;
	private static final String PERIOD_DELIMITER = ".";

	/** Order types */
	private static final String ABSOLUTE_NAME_ORDER = "abs";
	private static final String TYPE_ORDER = "type";
	private static final String SIZE_ORDER = "size";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Returns the selected file order
	 * @param orderName order name
	 * @param orderLine line number
	 * @param reversedOrder is the order reversed?
	 * @return The selected file order
	 * @throws TypeIError WARNINGS
	 */
	public static Comparator<File> select(String orderName, int orderLine,
								   boolean reversedOrder) throws TypeIError {
		Comparator<File> order;
		try {
			switch (orderName) {
			case ABSOLUTE_NAME_ORDER:
				order = abs();
				break;
			case TYPE_ORDER:
				order = type();
				break;
			case SIZE_ORDER:
				order = size();
				break;
			default: // order name not matching any case
				throw new TypeIError(orderLine);
			}
			// check if order is reversed
			return (reversedOrder) ? order.reversed() : order;
		} catch (TypeIError e) {
			throw new TypeIError(orderLine);
		}
	}

	// #### ORDERS OF THE PROCESSOR #### //

	/**
	 * File order according to its absolute name, from 'a' to 'z'.
	 * @return a file comparator according to absolute name.
	 */
	private static Comparator<File> abs() {
		return Comparator.comparing(File::getAbsolutePath);
	}

	/**
	 * File order by type, then by absolute name.
	 * @return a file comparator by type, then by absolute name.
	 */
	private static Comparator<File> type() {
		return (file1, file2) -> {
			int order = fileType(file1).compareTo(fileType(file2));
			if (order == EQUAL)
				return abs().compare(file1, file2);
			return order;
		};
	}

	/**
	 * File order by size, then by absolute name.
	 * @return a file comparator by size, then by absolute name.
	 */
	private static Comparator<File> size() {
		return (file1, file2) -> {
			int order = Long.compare(file1.length(), file2.length());
			if (order == EQUAL)
				return abs().compare(file1, file2);
			return order;
		};
	}

	/**
	 * Returns the file type (i.e. its extension)
	 * @param file the file to check
	 * @return the file extension.
	 */
	private static String fileType(File file) {
		int delimiterIDX = file.getName().lastIndexOf(PERIOD_DELIMITER);
		/* I. (delimiterIDX == -1) NOT_FOUND
		 * A file without a period in its name is considered to have
		 * the empty string as its extension.
		 * II. (delimiterIDX == 0)
		 * A file starts with a period and its name does not contain another
		 * period (as delimiter), it should be treated as if the file's
		 * type is the empty string. */
		if (delimiterIDX < 1)
			return "";
		int endIDX = file.getName().length();
		return file.getName().substring(delimiterIDX, endIDX);
	}
}
// End of OrderFactory