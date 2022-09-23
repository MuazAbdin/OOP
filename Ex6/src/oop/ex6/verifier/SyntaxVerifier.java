package oop.ex6.verifier;

import oop.ex6.SJavaRegex;
import oop.ex6.VerifierExceptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** a class verities syntax correctness */
public class SyntaxVerifier {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	private static final String ERROR = "ERROR: line %d: %n\t";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### /
	/** All lines of the code */
	private final String [] fileLines;

	/** code blocks (method definition ot if\while) */
	private int blocks = 0;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs a new Semantics Verifier
	 * @param fileLines all lines of the code file
	 */
	public SyntaxVerifier(String[] fileLines) {
		this.fileLines = fileLines;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * checks line syntax validity.
	 * @param line code line to check.
	 * @return true if valid, else false.
	 */
	private boolean isValidLine(String line) {
		for (Pattern pattern: SJavaRegex.LEGAL_PATTERN) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) {
				if (pattern == SJavaRegex.METHOD_DEFINITION ||
					pattern == SJavaRegex.IF_WHILE_BLOCK)
					++blocks;
				else if (pattern == SJavaRegex.CLOSE_SUFFIX)
					--blocks;
				return true;
			}
		}
		return false;
	}

	/**
	 * verifying the code syntax
	 * @throws VerifierExceptions bad syntax
	 */
	public void verifySyntax() throws VerifierExceptions {
		int lineNum = 1;
		for (String line: fileLines) {
			if (!isValidLine(line)) {
				throw new VerifierExceptions(String.format(ERROR + VerifierExceptions.BAD_SYNTAX_MSG,
														   lineNum, line));
			}
			else if (blocks < 0)
				throw new VerifierExceptions(String.format(ERROR + VerifierExceptions.BAD_BRACKETS,
														   lineNum));
			++lineNum;
		}
		if (blocks != 0) {
			throw new VerifierExceptions(VerifierExceptions.BAD_BLOCKS_MSG);
		}
	}
}
