//package oop.ex6;
//
//import oop.ex6.SJavaRegex;
//import oop.ex6.symbol.Method;
//import oop.ex6.symbol.Variable;
//import oop.ex6.verifier.SemanticsVerifier;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class SemanticsVerifierTest {
//
//	private static final File input = new File("Tests/sample.sjavac");
//	private static FileInputStream fileInputStream;
//	private static String[] fileContent;
//
//	@BeforeClass
//	public static void setUp() {
//		try {
//			fileInputStream = new FileInputStream(input);
//			byte[] inputData = new byte[(int) input.length()];
//			fileInputStream.read(inputData);
//			fileInputStream.close();
//			fileContent = new String(inputData).split("\n");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void buildGlobalTable() throws VerifierExceptions {
////		SemanticsVerifier semanticsVerifier = new SemanticsVerifier(fileContent);
////		try {
////			HashMap<String, Variable> map = semanticsVerifier.setupGlobalScope();
////			//		System.out.println(map);
////			//		HashMap<String, Variable> map = linker.symbolTable.getFileSymbolTables().getLast();
////			if (map != null)
////				printVarTable(map);
//////				map.forEach((key, value) -> System.out.println(key + ":" + value));
////			if (semanticsVerifier.symbolTable.getMethodsTable() != null) {
////				System.out.println();
////				printMethodTable(semanticsVerifier.symbolTable.getMethodsTable());
////			}
////		} catch (VerifierExceptions e) {
////			return;
////		}
//	}
//
//
//	public void printVarTable(HashMap<String, Variable> table) {
//		int num = 1;
//		System.out.format("%-10s%-15s%-15s%-15s%-15s\n", " # ", "constant", "type", "name", "value");
//		System.out.format("%-10s%-15s%-15s%-15s%-15s\n", "---", "--------", "----", "----", "-----");
//		for (Map.Entry<String, Variable> entry: table.entrySet()) {
//			System.out.format("%-10d%-15s%-15s%-15s%-15s\n", num,
//							  entry.getValue().isConstant() ? "final" : "   -   ",
//							  entry.getValue().type(), entry.getValue().name(),
//							  entry.getValue().value() == null ? "  -  " : entry.getValue().value());
//			++num;
//		}
//	}
//
//	public void printMethodTable(HashMap<String, Method> table) {
//		int num = 1;
//		System.out.format("%-10s%-15s%-15s%-15s\n", "line", "# args", "name", "arguments");
//		System.out.format("%-10s%-15s%-15s%-15s\n", "----", "--------", "----", "----");
//		for (Map.Entry<String, Method> entry: table.entrySet()) {
//			System.out.format("%-10d%-15d%-15s%-15s\n", entry.getValue().lineNum(),
//							  entry.getValue().argsNum(), entry.getValue().name(),
//							  entry.getValue().args());
//			++num;
//		}
//	}
//
//	@Test
//	public void tester1() throws VerifierExceptions, VerifierExceptions {
//		SemanticsVerifier semanticsVerifier = new SemanticsVerifier(fileContent);
//		semanticsVerifier.verifyIfWhile("while (false ||  -1 && 0 || -5.34) {");
//	}
//
//	@Test
//	public void tester() {
//		String[] varOptions = {"final int num1 = -1;",
//								"int num2;",
//								"double if, num4;",
//								"num5 = num6;",
//								"int _while = 8;",
//								"int num8, num9 = 18, num10;"};
//
//		String keywords = "int|double|boolean|char|String|void|final|if|while|true|false|return";
//		Pattern varName = Pattern.compile(SJavaRegex.VARIABLE_NAME);
//		Pattern keyword = Pattern.compile(keywords);
//		for (String str: varOptions) {
//			System.out.println("== " + str + " ==");
//			Matcher varMatcher = varName.matcher(str);
//			while (varMatcher.find()) {
//				String name = str.substring(varMatcher.start(), varMatcher.end());
//				if (!keyword.matcher(name).matches())
//					System.out.println(str.substring(varMatcher.start(), varMatcher.end()));
//			}
//		}
//
//	}
//}


//private static int verifyBlock(String[] lines, int blockLineNum) throws VerifierExceptions {
//
//		int lineNum = blockLineNum + 1;
//		//		int innerBlocks = 1;
//		//		while (innerBlocks != 0 || !SJavaRegex.CLOSE_SUFFIX.matcher(lines[lineNum]).matches()) {
//
//		while (!SJavaRegex.CLOSE_SUFFIX.matcher(lines[lineNum]).matches()) {
//		String line = lines[lineNum];
//		Matcher methodMatcher = SJavaRegex.METHOD_DEFINITION.matcher(line);
//		// I. defining method in other than global scope is illegal
//		if (methodMatcher.matches()) {
//		throw new VerifierExceptions(String.format(VerifierExceptions.NESTED_METHOD,
//		methodMatcher.group("name")));
//		}
//		// II. skip empty and comment and return lines
//		//			Matcher empty = SJavaRegex.EMPTY_LINE.matcher(line);
//		//			Matcher comment = SJavaRegex.COMMENT_LINE.matcher(line);
//		//			Matcher returnMatcher = SJavaRegex.RETURN_STATEMENT.matcher(line);
//		//			if (empty.matches() || comment.matches() || returnMatcher.matches()) {
//		//				++lineNum;
//		//				continue;
//		//			}
//		// III. add variables to local table, update previous tables if needed
//		Matcher varDec = SJavaRegex.VARIABLE_DECLARATION.matcher(line);
//		Matcher varAssign = SJavaRegex.VARIABLE_ASSIGNMENT.matcher(line);
//		if (varDec.matches() || varAssign.matches()) {
//		addLineVariables(line);
//		//				++lineNum;
//		//				continue;
//		}
//		// IV. verify method call
//		Matcher methodCall = SJavaRegex.METHOD_CALL.matcher(line);
//		if (methodCall.matches()) {
//		verifyMethodCall(line);
//		//				++lineNum;
//		//				continue;
//		}
//		// V. verify if\while line and recursively verify its block
//		Matcher ifWhile = SJavaRegex.IF_WHILE_BLOCK.matcher(line);
//		if (ifWhile.matches()) {
//		verifyIfWhile(line);
//		lineNum += verifyBlock(lines, lineNum);
//		//				++innerBlocks;
//
//		}
//		//			Matcher closing = SJavaRegex.CLOSE_SUFFIX.matcher(line);
//		//			if (closing.matches()) {
//		//				//reverse
//		//				--innerBlocks;
//		//			}
//		++lineNum;
//		}
//		return lineNum;
//		}