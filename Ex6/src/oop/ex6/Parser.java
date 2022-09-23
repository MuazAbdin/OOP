package oop.ex6;

import oop.ex6.symbol.Block;
import oop.ex6.symbol.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses each line into tokens
 */
public class Parser {

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	/** current parsed code line */
	String currentLine;
	/** current parsed token of the line */
	String currentToken;

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	/**
	 * Constructs a new line parser
	 * @param line valid code line (already syntactically checked)
	 */
	public Parser(String line) {
		this.currentLine = line;
	}

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * checks if line has more tokens.
	 * @return true it there is more tokens, else false.
	 */
	private boolean hasMoreTokens() {
		return currentLine.length() > 0;
	}

	/**
	 * advance to the next token.
	 */
	private void advance() {
		currentLine = currentLine.substring(currentToken.length()).trim();
	}

	/**
	 * parses a variable line into list of details lists, details list
	 * for every variable in this line:
	 * 	{{name, constant, type, value}, ...}
	 * @return ArrayList of variables details.
	 * @throws VerifierExceptions invalid line
	 */
	public ArrayList<String[]> parseVarLine() throws VerifierExceptions {
		ArrayList<String[]> lineVars = new ArrayList<>();
		// I. parse final and type
		String constant = null, type = null;
		Pattern varPattern =
				Pattern.compile("(?<final>^" + SJavaRegex.FINAL + ")(?<type>" + SJavaRegex.TYPE + ")");
		Matcher varMatcher = varPattern.matcher(currentLine);
		if (varMatcher.find()) {
			constant = varMatcher.group("final").trim();
			constant = constant.equals("") ? null : constant;
			type = varMatcher.group("type").trim();
			currentToken = varMatcher.group();
			advance();
		}
		// II. parse name and value
		while (hasMoreTokens()) {
			String[] NameVal = getNameAndValue();
			String name = NameVal[0], value = NameVal[1];;
			lineVars.add(new String[]{name, constant, type, value});
			advance();
		}
		return lineVars;
	}

	/**
	 * get 2 slot string array contains the variable's name and value if exists.
	 * @return String array of name nad value
	 */
	private String[] getNameAndValue() throws VerifierExceptions {
		// I. declaration only
		Pattern varDecPattern = Pattern.compile("^(?:" + SJavaRegex.VARIABLE_NAME + ")\\s*[;,]");
		Matcher varDecMatcher = varDecPattern.matcher(currentLine);
		if (varDecMatcher.find()) {
			currentToken = currentLine.substring(varDecMatcher.start(), varDecMatcher.end());
			String name = currentToken.replaceAll("[;,]|\\s*", "");
			if (SJavaRegex.KEYWORDS.matcher(name).matches())
				throw new VerifierExceptions(VerifierExceptions.ILLEGAL_NAME);
			return new String[]{name, null};
		}
		// II. assignment
		Pattern varAssignPattern = Pattern.compile("(?:\\s*" + SJavaRegex.VARIABLE_NAME + ")\\s*=\\s*" +
												   "(?:" + SJavaRegex.VARIABLE_VALUE + ")\\s*[,;]");
		Matcher varAssignMatcher = varAssignPattern.matcher(currentLine);
		if (varAssignMatcher.find()) {
			currentToken = currentLine.substring(varAssignMatcher.start(), varAssignMatcher.end());
			int idx = currentToken.indexOf('=');
			String name = currentToken.substring(0, idx).trim();
			String value = currentToken.substring(idx+1).replaceFirst("[;,]", "").trim();
			if (SJavaRegex.KEYWORDS.matcher(name).matches())
				throw new VerifierExceptions(VerifierExceptions.ILLEGAL_NAME);
			return new String[]{name, value};
		}
		return new String[2];
	}

	/**
	 * return method line details: name and (arguments)*
	 * syntax is already checked: void name(arg*) {
	 * @return method object contains method declaration details
	 * @throws VerifierExceptions illegal method
	 */
	public Block parseMethodSignature() throws VerifierExceptions {
		String methodName = null;
		ArrayList<Variable> arguments = new ArrayList<>();
		Pattern namePattern = Pattern.compile("^\\s*void\\s*(?<name>" + SJavaRegex.METHOD_NAME +")\\s*\\(");
		Matcher nameMatcher = namePattern.matcher(currentLine);
		if (nameMatcher.find())
			methodName = nameMatcher.group("name").trim();
		assert methodName != null;
		if (SJavaRegex.KEYWORDS.matcher(methodName).matches())
			throw new VerifierExceptions(VerifierExceptions.ILLEGAL_NAME);

		Pattern parameter = Pattern.compile("\\s*(?<final>final\\s+)?" +
											"(?<type>int|double|boolean|char|String)\\s+" +
											"(?<name>(?:_\\w|[a-zA-Z])\\w*)");
		Matcher paramMatcher = parameter.matcher(currentLine);
		while (paramMatcher.find()) {
			boolean constant = paramMatcher.group("final") != null;
			String type = paramMatcher.group("type").trim();
			String name = paramMatcher.group("name").trim();
			if (SJavaRegex.KEYWORDS.matcher(name).matches())
				throw new VerifierExceptions(VerifierExceptions.ILLEGAL_NAME);
			// check parameters with same name
			for (Variable arg : arguments) {
				if (arg.name().equals(name))
					throw new VerifierExceptions(String.format(VerifierExceptions.SAME_PARAMETERS,
															   methodName));
			}
			Variable arg = new Variable(name, type, null, constant);
			arg.markAsArg();
			arguments.add(arg);
		}
		return new Block(methodName, arguments);
	}

	/**
	 * return method call line details
	 * 	methodName((parameters,)* parameter?)
	 * 	parameter = name | value
	 * @return ArrayList contains the line details
	 */
	public ArrayList<String> parseCallLine() {
		ArrayList<String> lineDetails = new ArrayList<>();
		Pattern namePattern = Pattern.compile("^\\s*(?<name>" + SJavaRegex.METHOD_NAME +")\\s*\\(");
		Matcher nameMatcher = namePattern.matcher(currentLine);
		if (nameMatcher.find())
			lineDetails.add(nameMatcher.group("name"));
		currentToken = currentLine.substring(nameMatcher.start(), nameMatcher.end());
		advance();
		Pattern methodArg = Pattern.compile("(?<arg>" + SJavaRegex.ARGUMENT + ")[\\s*|,|\\)]");
		Matcher argMatcher = methodArg.matcher(currentLine);
		while (argMatcher.find()) {
			lineDetails.add(argMatcher.group("arg"));
		}
		return lineDetails;
	}

	/**
	 * Parses the if\while line into a list of its conditions
	 * @return ArrayList contains the line conditions
	 */
	public ArrayList<String> parseIfWhileLine() {
		ArrayList<String> lineDetails = new ArrayList<>();
		int idx = currentLine.indexOf('(');
		currentLine = currentLine.substring(idx);
		Pattern methodArg = Pattern.compile("(?<condition>" + SJavaRegex.CONDITION + ")");
		Matcher argMatcher = methodArg.matcher(currentLine);
		while (argMatcher.find()) {
			lineDetails.add(argMatcher.group("condition"));
		}
		return lineDetails;
	}

}
