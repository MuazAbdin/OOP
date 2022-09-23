package oop.ex6;

import java.util.regex.Pattern;

/**
 * class contains all of regex patterns of the code.
 */
public class SJavaRegex {
	// The metacharacters are: <([{\^-=$!|]})?*+.>

	// ########################
	// ##  SJAVA LINE REGEX  ##
	// ########################

	private final static String CODE_KEYWORDS = "int|double|boolean|char|String|void|final|" +
										       "if|while|true|false|return";

	private final static String EMPTY_LINE_REGEX = "^\\s*$";

	private final static String COMMENT_LINE_REGEX = "^//.*\\s*$";
	private final static String ILLEGAL_INLINE_COMMENT = "^.+//.*$";
	private final static String ILLEGAL_MULTILINE_COMMENT = "^.*(?:(?:/\\*|\\s*\\*|.*\\*/).*$)";
	private final static String ILLEGAL_COMMENT = ILLEGAL_INLINE_COMMENT + "|" +
												 ILLEGAL_MULTILINE_COMMENT;

	private final static String LINE_SUFFIX = "\\s*$";
	private final static String CLOSE_SUFFIX_REGEX = "^\\s*\\}\\s*$";

	private final static String ARRAYS_BRACKETS = "\\[\\]";
	private final static String ILLEGAL_OPERATORS = "\\+\\-\\*/%!";
	private final static String OPERATOR_OR_ARRAYS = "[" + ILLEGAL_OPERATORS + ARRAYS_BRACKETS + "]";

	// ######################
	// ##  VARIABLE REGEX  ##
	// ######################

	public final static String FINAL = "\\s*(?:final\\s+)?";

	public final static String TYPE = "(?:int|double|boolean|char|String)\\s+";

	public final static String VARIABLE_NAME = "(?:_\\w|[a-zA-Z])\\w*";

	private final static String ASSIGNING_OPERATOR = "\\s*\\=\\s*";

	public final static String INT_VALUE = "-?\\d++";
	public final static String DOUBLE_VALUE = "-?\\d+(?:\\.\\d++)?";
	public final static String BOOL_VALUE = "true|false|(?:" + DOUBLE_VALUE + ")";
	public final static String CHAR_VALUE = "'.'";
	public final static String STRING_VALUE = "\".*\"";

	public final static String VARIABLE_VALUE =
			INT_VALUE + "|" + DOUBLE_VALUE + "|(?:" + BOOL_VALUE + ")|" + CHAR_VALUE + "|" + STRING_VALUE +
			"|(?:" + VARIABLE_NAME + ")";

	public final static String OPTIONAL_ASSIGNMENT = "(?:(" + VARIABLE_NAME + ")(?:" + ASSIGNING_OPERATOR +
													 "(" + VARIABLE_VALUE + "))?)";
	private final static String VAR_DEC_REGEX =
			"(" + FINAL + ")(" + TYPE + ")(?:\\s*" + OPTIONAL_ASSIGNMENT + "\\s*,)*\\s*"
			+ OPTIONAL_ASSIGNMENT + "\\s*;\\s*$";

	private final static String VAR_ASSIGN_REGEX =
			"^\\s*(?:" + VARIABLE_NAME + ")" + ASSIGNING_OPERATOR + "(?:" + VARIABLE_VALUE + ")\\s*;\\s*$";

	// ####################
	// ##  METHOD REGEX  ##
	// ####################

	public final static String METHOD_NAME = "[a-zA-Z]\\w*";
	public final static String METHOD_PARAMETER = FINAL + TYPE + VARIABLE_NAME;
	private final static String METHOD_PARAMETER_LIST = "\\((?:" + METHOD_PARAMETER + "\\s*,)*\\s*(?:" +
													   METHOD_PARAMETER + ")?\\s*\\)";
	private final static String METHOD_DEF_REGEX = "^\\s*void\\s*(?<name>" + METHOD_NAME + ")\\s*" +
												   METHOD_PARAMETER_LIST + "\\s*\\{\\s*$";

	public final static String ARGUMENT = VARIABLE_VALUE + "|(?:" + VARIABLE_NAME + ")";
	private final static String ARGUMENTS = "\\(\\s*(?:(?:\\s*(?:" + ARGUMENT + ")\\s*,)*\\s*" +
										   "\\s*(?:" + ARGUMENT + "))?\\s*\\)";

	private final static String METHOD_CALL_REGEX = "^\\s*" + METHOD_NAME + "\\s*" +
											 ARGUMENTS + "\\s*;\\s*$";
	private final static String RETURN = "^\\s*return\\s*;\\s*$";

	// ######################
	// ##  IF/WHILE REGEX  ##
	// ######################

	private final static String BLOCK = "(?:if|while)";
	public final static String CONDITION = "(?:" + BOOL_VALUE + "|(" + VARIABLE_NAME + "))";
	private final static String AND_OR = "(?:\\|\\||&&)";
	private final static String MULTIPLE_CONDITION =
			"(?:(" + CONDITION + "\\s*" + AND_OR + "\\s*)*\\s*" + CONDITION + ")";
	private final static String IF_WHILE_REGEX = "^\\s*" + BLOCK + "\\s*\\(\\s*" + MULTIPLE_CONDITION +
											 "\\s*\\)" + "\\s*\\{\\s*$";


	// ############################
	// ##  LEGAL LINES PATTERNS  ##
	// ############################
	/* Legal lines are:
	   (1) comment: //
	   (2) varDec: type keyword or final (many separated by commas)
	   (3) varAssign: name (dont allow name to be keyword)
	   (4) varRef: varDec or varAssign
	   (5) methodDef: void keyword
	   (6) methodCall: name()
	   (7) returnLine: return
	   (8) blocks: if \ while keyword
	   (9) closingBracket: }
	 */

	/** KEYWORDS */
	public static final Pattern KEYWORDS = Pattern.compile(SJavaRegex.CODE_KEYWORDS);

	/** LEGAL LINES */
	public static final Pattern EMPTY_LINE = Pattern.compile(SJavaRegex.EMPTY_LINE_REGEX);
	public static final Pattern COMMENT_LINE = Pattern.compile(SJavaRegex.COMMENT_LINE_REGEX);
	public static final Pattern VARIABLE_DECLARATION = Pattern.compile(SJavaRegex.VAR_DEC_REGEX);
	public static final Pattern VARIABLE_ASSIGNMENT = Pattern.compile(SJavaRegex.VAR_ASSIGN_REGEX);
	public static final Pattern METHOD_DEFINITION = Pattern.compile(SJavaRegex.METHOD_DEF_REGEX);
	public static final Pattern METHOD_CALL = Pattern.compile(SJavaRegex.METHOD_CALL_REGEX);
	public static final Pattern RETURN_STATEMENT = Pattern.compile(SJavaRegex.RETURN);
	public static final Pattern IF_WHILE_BLOCK = Pattern.compile(SJavaRegex.IF_WHILE_REGEX);
	public static final Pattern CLOSE_SUFFIX = Pattern.compile(SJavaRegex.CLOSE_SUFFIX_REGEX);

	/** Legal patterns array */
	public static final Pattern[] LEGAL_PATTERN = {EMPTY_LINE, COMMENT_LINE,
													VARIABLE_DECLARATION, VARIABLE_ASSIGNMENT,
													METHOD_DEFINITION, METHOD_CALL,
													RETURN_STATEMENT, CLOSE_SUFFIX,
													IF_WHILE_BLOCK};


}
