package filesprocessing.Filter;

import filesprocessing.TypeIError;

import java.io.FileFilter;

/**
 * A factory class for filters used in Directory Processor
 */
public class FilterFactory {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	private static final double BYTES_IN_KB = 1024;

	/** filter types */
	private static final String GREATER_THAN_FILTER = "greater_than";
	private static final String BETWEEN_FILTER = "between";
	private static final String SMALLER_THAN_FILTER = "smaller_than";
	private static final String FILE_FILTER = "file";
	private static final String CONTAINS_FILTER = "contains";
	private static final String PREFIX_FILTER = "prefix";
	private static final String SUFFIX_FILTER = "suffix";
	private static final String WRITABLE_FILTER = "writable";
	private static final String EXECUTABLE_FILTER = "executable";
	private static final String HIDDEN_FILTER = "hidden";
	private static final String ALL_FILTER = "all";

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
	 * Returns the selected file filter
	 * @param filterName filter name
	 * @param filterValues filter parameters
	 * @param filterLine line number
	 * @param negatedFilter is the filter negated?
	 * @return The selected file filter
	 * @throws TypeIError WARNINGS
	 */
	public static FileFilter select(String filterName, String[] filterValues,
							 int filterLine, boolean negatedFilter) throws TypeIError {
		FileFilter filter;
		try {
			switch (filterName) {
			case GREATER_THAN_FILTER:
				filter = greaterThan(filterValues, filterLine);
				break;
			case BETWEEN_FILTER:
				filter = between(filterValues, filterLine);
				break;
			case SMALLER_THAN_FILTER:
				filter = smallerThan(filterValues, filterLine);
				break;
			case FILE_FILTER:
				filter = file(filterValues, filterLine);
				break;
			case CONTAINS_FILTER:
				filter = contains(filterValues, filterLine);
				break;
			case PREFIX_FILTER:
				filter = prefix(filterValues, filterLine);
				break;
			case SUFFIX_FILTER:
				filter = suffix(filterValues, filterLine);
				break;
			case WRITABLE_FILTER:
				filter = writable(filterValues, filterLine);
				break;
			case EXECUTABLE_FILTER:
				filter = executable(filterValues, filterLine);
				break;
			case HIDDEN_FILTER:
				filter = hidden(filterValues, filterLine);
				break;
			case ALL_FILTER:
				filter = all(filterValues, filterLine);
				break;
			default: // filter name not matching any case
				throw new TypeIError(filterLine);
			}
			// check if filter is negated
			return (negatedFilter) ? new NegatedFilter(filter): filter;
		} catch (TypeIError e) {
			throw new TypeIError(filterLine);
		}
	}

	// #### FILTERS OF THE PROCESSOR #### //

	/**
	 * Tests whether or not the specified abstract pathname size is
	 * strictly greater than the given number of k-bytes
	 * @param args size in KB
	 * @param filterLine line number
	 * @return true if and only if pathname size is strictly greater
	 * 		   than the given number of k-bytes
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter greaterThan(String[] args, int filterLine) throws TypeIError {
		try {
			if (args.length != 1)
				throw new TypeIError(filterLine);
			double size = Double.parseDouble(args[0]);
			if (size < 0)
				throw new TypeIError(filterLine);
			return pathname -> pathname.length()/BYTES_IN_KB > size;
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the specified abstract pathname size is
	 * between (inclusive) the given numbers (in k-bytes)
	 * @param args size bounds (inclusive)
	 * @param filterLine line number
	 * @return true if and only if pathname size is between (inclusive)
	 * 		   the given numbers (in k-bytes)
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter between(String[] args, int filterLine) throws TypeIError {
		try {
			if (args.length != 2)
				throw new TypeIError(filterLine);
			double lower = Double.parseDouble(args[0]);
			double upper = Double.parseDouble(args[1]);
			if (lower < 0 || upper < 0 || upper < lower)
				throw new TypeIError(filterLine);
			return pathname -> pathname.length()/BYTES_IN_KB >= lower &&
							   pathname.length()/BYTES_IN_KB <= upper;
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the specified abstract pathname size is
	 * strictly smaller than the given number of k-bytes
	 * @param args size in KB
	 * @param filterLine line number
	 * @return true if and only if pathname size is strictly smaller
	 * 		   than the given number of k-bytes
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter smallerThan(String[] args, int filterLine) throws TypeIError {
		try {
			if (args.length != 1)
				throw new TypeIError(filterLine);
			double size = Double.parseDouble(args[0]);
			if (size < 0)
				throw new TypeIError(filterLine);
			return pathname -> pathname.length()/BYTES_IN_KB < size;
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the specified abstract pathname last name
	 * equals the file name
	 * @param args file name to compare
	 * @param filterLine line number
	 * @return true if and only if the last name in the pathname
	 * 		   equals the given file name
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter file(String[] args, int filterLine) throws TypeIError {
		try {
			String fileName = (args.length == 0) ? "" : args[0];
			return pathname -> pathname.getName().equals(fileName);
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the given substring is contained in
	 * the last name of the specified abstract pathname
	 * @param args sub-string to search for
	 * @param filterLine line number
	 * @return true if and only if value is contained in the
	 * 		   file name (excluding path)
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter contains(String[] args, int filterLine) throws TypeIError {
		try {
			String value = (args.length == 0) ? "" : args[0];
			return pathname -> pathname.getName().contains(value);
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the given substring is the prefix
	 * of the file name (excluding path)
	 * @param args substring to check
	 * @param filterLine line number
	 * @return true if and only if value is the prefix of the
	 * 		   file name (excluding path)
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter prefix(String[] args, int filterLine) throws TypeIError {
		try {
			String prefix = (args.length == 0) ? "" : args[0];
			return pathname -> pathname.getName().startsWith(prefix);
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Tests whether or not the given substring is the suffix
	 * of the file name (excluding path)
	 * @param args substring to check
	 * @param filterLine line number
	 * @return true if and only if value is the suffix of the
	 * 		   file name (excluding path)
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter suffix(String[] args, int filterLine) throws TypeIError {
		try {
			String suffix = (args.length == 0) ? "" : args[0];
			return pathname -> pathname.getName().endsWith(suffix);
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Does file have writing permission? (for the current user)
	 * @param args permission
	 * @param filterLine line number
	 * @return true if and only if the file have writing permission
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter writable(String[] args, int filterLine) throws TypeIError {
		try {
			String value = args[0];
			if (!(value.equals("YES") || value.equals("NO")))
				throw new TypeIError(filterLine);
			boolean permission = value.equals("YES");
			return pathname -> (permission == pathname.canWrite());
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Does file have execution permission? (for the current user)
	 * @param args permission
	 * @param filterLine line number
	 * @return true if and only if the file have execution permission
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter executable(String[] args, int filterLine) throws TypeIError {
		try {
			String value = args[0];
			if (!(value.equals("YES") || value.equals("NO")))
				throw new TypeIError(filterLine);
			boolean permission = value.equals("YES");
			return pathname -> (permission == pathname.canExecute());
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * Is the file a hidden file?
	 * @param args YES\NO
	 * @param filterLine line number
	 * @return true if and only if the file is hidden file.
	 * @throws TypeIError WARNINGS
	 */
	private static FileFilter hidden(String[] args, int filterLine) throws TypeIError {
		try {
			String value = args[0];
			if (!(value.equals("YES") || value.equals("NO")))
				throw new TypeIError(filterLine);
			boolean permission = value.equals("YES");
			return pathname -> (permission == pathname.isHidden());
		} catch (Exception e) {
			throw new TypeIError(filterLine);
		}
	}

	/**
	 * all files are matched
	 * @return true for every pathname
	 */
	private static FileFilter all(String[] args, int filterLine) throws TypeIError {
		if (args.length != 0)
			throw new TypeIError(filterLine);
		return pathname -> true;
	}
}
// End of FilterFactory
