package filesprocessing;

/**
 * Type I errors (Warnings):
 * Bad parameters in the FILTER/ORDER line
 */
public class TypeIError extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Exception Constructor
	 * @param lineNum number of line where the warning had be thrown
	 */
	public TypeIError(int lineNum) {
		super(String.format("Warning in line %d", lineNum));
	}
}
