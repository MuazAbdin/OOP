package oop.ex6.symbol;

/**
 * A class represents a variable
 */
public class Variable{

	public static final String BOOLEAN_TYPE = "boolean";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	private final String name;
	private final String type;
	private final String value;
	private final boolean constant;
	private boolean isArg = false;
	private boolean isCondition = false;

	// ###################### //
	// #### CONSTRUCTORS #### //
	// ###################### //

	/**
	 * Constructs a new variable from the given details.
	 * @param name variable name
	 * @param type variable type
	 * @param value variable value
	 * @param constant is variable final?
	 */
	public Variable(String name, String type, String value, boolean constant) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.constant = constant;
	}

	/**
	 * Constructs a new variable from name.
	 * @param name variable name
	 */
	public Variable(String name) {
		this(name, BOOLEAN_TYPE, null, false);
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/** gets variable name */
	public String name() {
		return name;
	}

	/** gets variable type */
	public String type() {
		return type;
	}

	/** gets variable value */
	public String value() {
		return value;
	}

	/** gets if variable final */
	public boolean isConstant() {
		return constant;
	}

	/** gets if variable is a method argument */
	public boolean isArg() {
		return isArg;
	}

	/** marks variable as argument */
	public void markAsArg() {
		isArg = true;
	}

	/** gets if variable is a if\while condition */
	public boolean isCondition() {
		return isCondition;
	}

	/** marks variable as condition */
	public void markAsCondition() {
		isCondition = true;
	}

}
