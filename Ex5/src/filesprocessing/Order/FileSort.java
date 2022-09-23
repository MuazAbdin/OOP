package filesprocessing.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * User-defined sorting class that uses QuickSort algorithm
 */
public class FileSort {

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** The file comparator used in this sorter */
	private Comparator<File> fileComparator;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs a new sorter
	 * @param order The file comparator used in this sorter
	 */
	public FileSort(Comparator<File> order) {
		fileComparator = order;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * The partition function helps the quick sorts.
	 * @param filesList list to be sorted.
	 * @param left left most index.
	 * @param right right most index.
	 * @return the index of the pivot, such that all objects in the left are
	 * 		   smaller than it, and all in the right are greater than it.
	 */
	private int partition(ArrayList<File> filesList, int left, int right) {
		int i = left, j = right;
		File tmp;
		File pivot = filesList.get((left + right) / 2);
		while (i <= j) {
			// filesList[i] < pivot
			while (fileComparator.compare(filesList.get(i), pivot) < 0)
				i++;
			// filesList[j] > pivot
			while (fileComparator.compare(filesList.get(j), pivot) > 0)
				j--;
			if (i <= j) {
				// swap
				tmp = filesList.get(i);
				filesList.set(i, filesList.get(j));
				filesList.set(j, tmp);
				i++;
				j--;
			}
		}
		return i;
	}

	/**
	 * The quick sort function (sorts in ascending order)
	 * @param filesList list to be sorted.
	 * @param left left most index.
	 * @param right right most index.
	 */
	public void quickSort(ArrayList<File> filesList, int left, int right) {
		int index = partition(filesList, left, right);
		if (left < index - 1)
			quickSort(filesList, left, index - 1);
		if (index < right)
			quickSort(filesList, index, right);
	}

}
// End of FileSort Class
