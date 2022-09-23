package oop.ex6;

/**
 * Exceptions class for the code verifier
 */
public class VerifierExceptions extends Exception{
	private static final long serialVersionUID = 1L;

	// ########################### //
	// #### SYNTAX EXCEPTIONS #### //
	// ########################### /

	public static final String BAD_SYNTAX_MSG = "bad syntax: %s %n";
	public static final String BAD_BRACKETS = "bad syntax: " +
												 "illegal closing bracket, no corresponding opening %n";
	public static final String BAD_BLOCKS_MSG = "ERROR: \n\tbad syntax, check blocks brackets. \n";
	public static final String GLOBAL_IF_WHILE = "if\\while block not allowed in the global scope %n";

	public static final String ILLEGAL_NAME = "Sjava keyword is not an illegal name %n";


	// ############################## //
	// #### VARIABLE EXCEPTIONS #### //
	// ############################# //

	public static final String BAD_REFERENCE = "referred to undeclared variable: %s %n";
	public static final String INCOMPATIBLE_TYPE_VALUE = "incompatible types: " +
														 "%s cannot be converted to %s type %n";
	public static final String UNASSIGNED_VAR = "using an unassigned variable: %s %n";
	public static final String REASSIGN_CONSTANT = "%s is constant.%n";
	public static final String UNINITIALIZED_CONSTANT = "constant variable must be initialized at " +
														"declaration time. \n";
	public static final String INCOMPATIBLE_TYPES = "incompatible types: %s and %s%n";
	public static final String REDECLARATION = "Variable %s is already defined in the scope%n";

	// ############################ //
	// #### METHODS EXCEPTIONS #### //
	// ############################ //

	public static final String REDEFINITION = "method %s is already definedv%n";
	public static final String NOT_DEFINED = "method %s never defined %n";
	public static final String NESTED_METHOD = "nested methods not allowed: in method %s";
	public static final String DIFFERENT_ARGS = "in method %s: " +
												"actual and formal argument lists differ in length. %n";
	public static final String NO_RETURN = "method %s had no return statement. %n";
	public static final String SAME_PARAMETERS = "method %s has parameters with the same name. %n";

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * constructs an new exception
	 * @param errorMsg informative error message
	 */
	public VerifierExceptions(String errorMsg) {
		super(errorMsg);
	}
}
