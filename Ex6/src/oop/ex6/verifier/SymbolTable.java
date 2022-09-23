package oop.ex6.verifier;

import oop.ex6.SJavaRegex;
import oop.ex6.VerifierExceptions;
import oop.ex6.symbol.Block;
import oop.ex6.symbol.Variable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Class of symbol tables of the code
 */
public class SymbolTable {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	private static final String CONSTANT = "final";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** symbol table for code methods
	 *  <name: final, type, value> */
	private HashMap<String, Block> methodsTable = new HashMap<>();

	/** linked list of all variable tables (for each scope there is a table) */
	private LinkedList<HashMap<String, Variable>> variablesTables = new LinkedList<>();

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	// ################# //
	// #### METHODS #### //
	// ################# //

	/** get the method table */
	public HashMap<String, Block> getMethodsTable() {
		return methodsTable;
	}

	/**
	 * add a new symbol table of the current scope
	 * @param table variable symbol table
	 */
	public void addVariableTable(HashMap<String, Variable> table) {
		variablesTables.add(table);
	}

	/** remove the last variable table (of last scope) */
	public void removeBlockTable() {
		variablesTables.removeLast();
	}

	/**
	 * find the most inner scope the variable is declared in
	 * @param name variable name to search
	 * @return A variable table if found, else null
	 */
	public HashMap<String, Variable> variableScope(String name) {
		Iterator<HashMap<String, Variable>> iter = variablesTables.descendingIterator();
		while (iter.hasNext()) {
			HashMap<String, Variable> table = iter.next();
			if (table.containsKey(name)) {
				if (!table.get(name).isCondition() || table.get(name).value() != null)
					return table;
			}
		}
		return null;
	}

	/**
	 * Defines a variable with the given details, that is, adds it to
	 * the suitable scope table, if it is a valid variable.
	 * @param name variable's name
	 * @param constant final variable or not
	 * @param type variable's type
	 * @param value variable's value
	 * @throws VerifierExceptions illegal variable
	 */
	public void defineVariable(String name, String constant, String type, String value)
			throws VerifierExceptions {
		HashMap<String, Variable> currentTable = variablesTables.getLast();
		// I. variable declaration : (final)? type name (= val)?;
		if (type != null) {
			boolean constantVar = CONSTANT.equals(constant);
			// I.a. check redeclaration in the current scope
			if (currentTable.containsKey(name))
				throw new VerifierExceptions(String.format(VerifierExceptions.REDECLARATION, name));
			// I.b. check initializing constant variable = final type name;
			if (constantVar && value == null)
				throw new VerifierExceptions(VerifierExceptions.UNINITIALIZED_CONSTANT);
			// I.c. check valid assigning: type name = val
			if (value != null)
				checkVarCompatibility(type, value);

			currentTable.put(name, new Variable(name, type, value, constantVar));
		}
		// II. assignment of a variable : name = val;
		else {
			// II.a. check previous declaration
			HashMap<String, Variable> scope = variableScope(name);
			if (scope == null) {
				throw new VerifierExceptions(String.format(VerifierExceptions.BAD_REFERENCE, name));
			}
			Variable currentVar = scope.get(name);
			// II.b check reassigning a constant variable
			if (currentVar.isConstant()) {
				throw new VerifierExceptions(String.format(VerifierExceptions.REASSIGN_CONSTANT, name));
			}
			// II.c. check valid assigning: type name = val
			checkVarCompatibility(currentVar.type(), value);

			Variable newVar = new Variable(name, currentVar.type(), value, currentVar.isConstant());
			currentTable.put(name, newVar);
		}
	}

	/**
	 * defines a new method in the global scope (adds it to methods symbol table)
	 * @param blockSignature method signature
	 * @param line the line the methods starts at
	 * @throws VerifierExceptions illegal method signature.
	 */
	public void defineMethod(Block blockSignature, int line) throws VerifierExceptions{
		if (methodsTable.containsKey(blockSignature.name())) {
			throw new VerifierExceptions(String.format(VerifierExceptions.REDEFINITION,
													   blockSignature.name()));
		}
		blockSignature.setStartLine(line);
		methodsTable.put(blockSignature.name(), blockSignature);
	}

	/**
	 * check if the a variable of the given type compatible a given value
	 * @param type type of variable
	 * @param value a given value (may be a name ot other variable)
	 * @throws VerifierExceptions illegal variable
	 */
	public void checkVarCompatibility(String type, String value) throws VerifierExceptions {
		// I. value is a variable name: check assignment, and type
		if (Pattern.compile(SJavaRegex.VARIABLE_NAME).matcher(value).matches() &&
			!value.matches("true|false")) {
			HashMap<String, Variable> scope = variableScope(value);
			if (scope == null || scope.get(value).value() == null && !scope.get(value).isArg()) {
				throw new VerifierExceptions(String.format(VerifierExceptions.UNASSIGNED_VAR, value));
			}
			if (!isCompatibleTypes(type, scope.get(value).type())) {
				throw new VerifierExceptions(String.format(VerifierExceptions.INCOMPATIBLE_TYPES,
														  type, scope.get(value).type()));
			}
		}
		// II. value is not a variable name:
		else if (!isCompatibleTypeValue(type, value)) {
			throw new VerifierExceptions(String.format(VerifierExceptions.INCOMPATIBLE_TYPE_VALUE,
													  value, type));
		}
	}

	/**
	 * check if the given types ar compatible.
	 * @param type1 first type
	 * @param type2 second type
	 * @return true if compatible, false else.
	 */
	public boolean isCompatibleTypes(String type1, String type2) {
		return type1.equals(type2) || (type1.equals("double") && type2.equals("int")) ||
			   type1.equals("boolean") && ((type2.equals("int") || type2.equals("double")));
	}

	/**
	 * if the given value (not a variable name) compatible with the given type
	 * @param type variable type
	 * @param value some value
	 * @return true if compatible, false else.
	 */
	private boolean isCompatibleTypeValue(String type, String value) {
		switch (type) {
		case "int":
			return Pattern.compile(SJavaRegex.INT_VALUE).matcher(value).matches();
		case "double":
			return Pattern.compile(SJavaRegex.DOUBLE_VALUE).matcher(value).matches();
		case "boolean":
			return Pattern.compile(SJavaRegex.BOOL_VALUE).matcher(value).matches();
		case "char":
			return Pattern.compile(SJavaRegex.CHAR_VALUE).matcher(value).matches();
		case "String":
			return Pattern.compile(SJavaRegex.STRING_VALUE).matcher(value).matches();
		default:
			return false;
		}
	}

}
