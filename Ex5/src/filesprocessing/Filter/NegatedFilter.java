package filesprocessing.Filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Decorator class for FileFilter
 */
public class NegatedFilter implements FileFilter {

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** The original filter to be negated */
	private final FileFilter originalFilter;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * constructs a new negated filter
	 * @param filter filter to be negated
	 */
	NegatedFilter(FileFilter filter) {
		originalFilter = filter;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Tests whether or not the specified abstract pathname should
	 * be included in a pathname list.
	 * @param pathname The abstract pathname to be tested
	 * @return true if and only if pathname	should be included
	 */
	@Override
	public boolean accept(File pathname) {
		return !originalFilter.accept(pathname);
	}
}
// End of NegatedFilter Decorator class
