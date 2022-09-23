package oop.ex6.verifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;

import oop.ex6.SJavaRegex;
import oop.ex6.symbol.Block;
import oop.ex6.symbol.Variable;
import oop.ex6.VerifierExceptions;
import oop.ex6.Parser;

/**
 * A verifier class for static semantics of the code:
 * checks valid assignments and referring for variables
 */
public class SemanticsVerifier {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	private static final String ERROR = "ERROR: line %d: %n\t";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** All lines of the code */
	private final String [] fileLines;
	/** counter for code lines */
	private int lineNum = 1;
	/** number of blocks (method definition of if\while) in the code */
	private int blockCount = 0;
	/** code symbol table: contains all variables and methods details */
	private SymbolTable symbolTable = new SymbolTable();


	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs a new Semantics Verifier
	 */
	public SemanticsVerifier(String[] fileLines) {
		this.fileLines = fileLines;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Building the global scope tables:
	 * (1) methods table: contains every method details: name and parameters.
	 * (2) global variables table: contains all of global variables details:
	 *     constant, type, name, and value.
	 * Then it puts global variables table as the root of the LinkedList of
	 * the scopes tables.
	 *    global -> local_1 -> local_2 -> ... -> local_n
	 * @throws VerifierExceptions illegal variable or method
	 */
	public void setupGlobalScope() throws VerifierExceptions {
		int lineNum = 1;
		// insert an empty global table
		HashMap<String, Variable> varTable = new HashMap<>();
		symbolTable.addVariableTable(varTable);
		// iterate over line codes to catch global variables and method declarations
		for (String line: fileLines) {
			// I. skip empty and comment lines
			if (SJavaRegex.EMPTY_LINE.matcher(line).matches() ||
				SJavaRegex.COMMENT_LINE.matcher(line).matches()) {
				++lineNum;
				continue;
			}
			// II. No if\while blocks in the global scope
			checkGlobalIfWhile(line);
			// III. add method to methods table
			Matcher method = SJavaRegex.METHOD_DEFINITION.matcher(line);
			if (method.matches()) {
				++blockCount;
				checkMethodSignature(line, lineNum);
				++lineNum;
				continue;
			}
			// IV. add global variable(s) to table
			if (blockCount == 0)
				checkGlobalVar(line);
			// V. closing block
			Matcher closing = SJavaRegex.CLOSE_SUFFIX.matcher(line);
			if (closing.matches())
				--blockCount;
			++lineNum;
		}
	}

	/**
	 * checks if there is a global if\while block, if it is not
	 * (i.e. it is inside a method) then increment the block counter.
	 * @param line line to check
	 * @throws VerifierExceptions illegal block
	 */
	private void checkGlobalIfWhile(String line) throws VerifierExceptions {
		Matcher ifWhile = SJavaRegex.IF_WHILE_BLOCK.matcher(line);
		if (ifWhile.matches()) {
			if (blockCount == 0) {
				throw new VerifierExceptions(String.format(ERROR + VerifierExceptions.GLOBAL_IF_WHILE,
														   lineNum));
			} else {
				++blockCount;
			}
		}
	}

	/**
	 * check the validity of method signature, and increment the block counter.
	 * @param line line to check
	 * @throws VerifierExceptions illegal method signature
	 */
	public void checkMethodSignature(String line, int lineNum) throws VerifierExceptions {
		try {
			Parser parser = new Parser(line);
			Block signature = parser.parseMethodSignature();
			symbolTable.defineMethod(signature, lineNum);
		} catch (VerifierExceptions methodExp) {
			throw new VerifierExceptions(String.format(ERROR + methodExp.getMessage(), lineNum));
		}
	}

	/**
	 * check global variable validity
	 * @param line line to check
	 * @throws VerifierExceptions iilegal variable
	 */
	private void checkGlobalVar(String line) throws VerifierExceptions {
		try {
			Matcher varDec = SJavaRegex.VARIABLE_DECLARATION.matcher(line);
			Matcher varAssign = SJavaRegex.VARIABLE_ASSIGNMENT.matcher(line);
			if (!varDec.matches() && !varAssign.matches()) {
				throw new VerifierExceptions(String.format(VerifierExceptions.BAD_SYNTAX_MSG, line));
			}
			addLineVariables(line);
		} catch (VerifierExceptions varExp) {
			throw new VerifierExceptions(String.format(ERROR + varExp.getMessage(), lineNum));
		}
	}

	/**
	 * add variables of the given line to the table
	 * @param line line to check
	 * @throws VerifierExceptions illegal variable
	 */
	private void addLineVariables(String line) throws VerifierExceptions {
		Parser parser = new Parser(line);
		ArrayList<String[]> lineVars = parser.parseVarLine();
		for (String[] varDetail: lineVars) {
			symbolTable.defineVariable(varDetail[0], varDetail[1],
									   varDetail[2], varDetail[3]);
		}
	}

	/**
	 * verify all of the local scopes (all methods and if\while blocks in the code)
	 * @throws VerifierExceptions illegal line
	 */
	public void verifyLocalScopes() throws VerifierExceptions {
		for (Block block : symbolTable.getMethodsTable().values()) {
			int endLine = verifyBlock(block);
			/* iterate reversely over lines until the first not empty or comment line,
			 * if it is not the return statement, then throw exception */
			for (int idx = endLine-1; idx >= block.lineNum() - 1; --idx) {
				Matcher empty = SJavaRegex.EMPTY_LINE.matcher(fileLines[idx]);
				Matcher comment = SJavaRegex.COMMENT_LINE.matcher(fileLines[idx]);
				if (empty.matches() || comment.matches())
					continue;
				Matcher returnMatcher = SJavaRegex.RETURN_STATEMENT.matcher(fileLines[idx]);
				if (returnMatcher.matches())
					break;
				throw new VerifierExceptions(String.format(ERROR + VerifierExceptions.NO_RETURN,
														   endLine, block.name()));
			}
		}
	}

	/**
	 * verify a single method and its inner blocks.
	 * @param block block to verify.
	 * @return line number the block ends at.
	 * @throws VerifierExceptions illegal block
	 */
	private int verifyBlock(Block block) throws VerifierExceptions {

		/* start building the local symbol table, which is a variable
		 * table because no nested method definition */
		HashMap<String, Variable> locVarTable = new HashMap<>();
		symbolTable.addVariableTable(locVarTable);
		// add args to local table, all are (final)? declared unassigned variables
		for (Variable arg: block.parameters()) {
			locVarTable.put(arg.name(), arg);
		}
		int lineNum = block.lineNum();
		try {
			while (!SJavaRegex.CLOSE_SUFFIX.matcher(fileLines[lineNum]).matches()) {
				String line = fileLines[lineNum];
				Matcher methodMatcher = SJavaRegex.METHOD_DEFINITION.matcher(line);
				// I. defining method in other than global scope is illegal
				if (methodMatcher.matches() && blockCount != 0) {
					throw new VerifierExceptions(String.format(ERROR + VerifierExceptions.NESTED_METHOD,
															   lineNum+1,
															   methodMatcher.group("name")));
				}
				// II. add variables to local table, update previous tables if needed
				else if (SJavaRegex.VARIABLE_DECLARATION.matcher(line).matches() ||
						 SJavaRegex.VARIABLE_ASSIGNMENT.matcher(line).matches())
					addLineVariables(line);
				// III. verify method call
				else if (SJavaRegex.METHOD_CALL.matcher(line).matches())
					verifyMethodCall(line);
				// IV. verify if\while line and recursively verify its block
				if (SJavaRegex.IF_WHILE_BLOCK.matcher(line).matches()) {
					Block ifWhileBlock = verifyIfWhile(line);
					ifWhileBlock.setStartLine(lineNum + 1);
					lineNum = verifyBlock(ifWhileBlock);
				}
				++lineNum;
			}
			symbolTable.removeBlockTable();
			return lineNum;
		} catch (VerifierExceptions e) {
			throw new VerifierExceptions(String.format(ERROR + e.getMessage(), lineNum+1));
		}
	}

	/**
	 * check method call line = name (args*)
	 * @param line code line
	 * @throws VerifierExceptions illegal method call
	 */
	private void verifyMethodCall(String line) throws VerifierExceptions {
		Parser parser = new Parser(line);
		ArrayList<String> lineDetails = parser.parseCallLine();
		Iterator<String> calledMethodDetails = lineDetails.iterator();
		String methodName = calledMethodDetails.next();
		// check if method is defined
		if (!symbolTable.getMethodsTable().containsKey(methodName))
			throw new VerifierExceptions(String.format(VerifierExceptions.NOT_DEFINED, methodName));
		Block block = symbolTable.getMethodsTable().get(methodName);
		// check number of parameters
		if (lineDetails.size() - 1 != block.argsNum())
			throw new VerifierExceptions(String.format(VerifierExceptions.DIFFERENT_ARGS, methodName));
		// check parameters compatibility
		Iterator<Variable> definedVars = block.parameters().iterator();
		while (calledMethodDetails.hasNext() && definedVars.hasNext()) {
			String definedArgType = definedVars.next().type();
			String passedArg = calledMethodDetails.next();
			symbolTable.checkVarCompatibility(definedArgType, passedArg);
		}
	}

	/**
	 * check if\while line = if\while (conditions*)
	 * @param line code line
	 * @return if\while verified block
	 * @throws VerifierExceptions illegal if\while line
	 */
	public Block verifyIfWhile(String line) throws VerifierExceptions {
		Parser parser = new Parser(line);
		ArrayList<Variable> arguments = new ArrayList<>();
		// check parameters compatibility
		for (String condition : parser.parseIfWhileLine()) {
			symbolTable.checkVarCompatibility("boolean", condition);
			if (!condition.matches(SJavaRegex.BOOL_VALUE)) {
				Variable conditionVar = new Variable(condition);
				conditionVar.markAsCondition();
				arguments.add(conditionVar);
			}
		}
		return new Block(arguments);
	}
}
