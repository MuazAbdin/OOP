package filesprocessing;

/**
 * Type II errors:
 * Invalid Usage, I/O problems, bad sub-section name
 */
public class TypeIIError extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Exception Constructor
	 * @param errorMessage Error message
	 */
	public TypeIIError(String errorMessage) {
		super("ERROR: " + errorMessage);
	}
}
