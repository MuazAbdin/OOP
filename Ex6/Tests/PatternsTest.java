//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.Arrays;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import oop.ex6.SJavaRegex;
//
//public class PatternsTest {
//
////	private static final String filePath = "/Users/Muadz/Downloads/HUJI/2020_21_sem_A/OOP/Exercises/Ex6" +
////										   "/Tests/sample.sjavac";
////	private static final File filePath = new File("Tests/sample.sjavac");
//	private static final File input = new File("Tests/sample.sjavac");
//	private static FileInputStream fileInputStream;
//	private static String[] fileContent;
////	private static String fileContent;
//
//	@BeforeClass
//	public static void setUp() {
//		try {
////			System.out.println(filePath.getAbsolutePath());
//			fileInputStream = new FileInputStream(input);
//			byte[] inputData = new byte[(int) input.length()];
//			fileInputStream.read(inputData);
//			fileInputStream.close();
//
//			fileContent = new String(inputData).split("\n");
////			int line_num = 1;
////			for (String line: fileContent) {
////				System.out.printf("%d. %s%n", line_num, line);
////				++line_num;
////			}
//
////			fileContent = new String(inputData);
////			System.out.println(fileContent);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void testRegex(String regex) {
//		Pattern pattern = Pattern.compile(regex);
//		int line_num = 1;
//		for (String line: fileContent) {
//			Matcher matcher = pattern.matcher(line);
//			if (matcher.matches()) {
//				System.out.printf("%d\t. %s%n", line_num, line);
//			}
//			++line_num;
//		}
//	}
//
//	private void testRegexNegation(String regex) {
//		Pattern pattern = Pattern.compile(regex);
//		int line_num = 1;
//		for (String line: fileContent) {
//			Matcher matcher = pattern.matcher(line);
//			if (!matcher.matches()) {
//				System.out.printf("%d\t. %s%n", line_num, line);
//			}
//			++line_num;
//		}
//	}
//
//	@Test
//	public void testComment() {
//		System.out.println("== COMMENT_LINE ==");
//		testRegex(SJavaRegex.COMMENT_LINE);
//	}
//
//	@Test
//	public void testIllegalComment() {
//		System.out.println("== ILLEGAL_COMMENT ==");
//		testRegex(SJavaRegex.ILLEGAL_COMMENT);
//	}
//
//	@Test
//	public void testEmptyLine() {
//		System.out.println("== EMPTY_LINE ==");
//		testRegex(SJavaRegex.EMPTY_LINE);
//	}
//
//	@Test
//	public void testIllegalCodeLine() {
//		System.out.println("== ILLEGAL_CODE_LINE ==");
//		testRegexNegation(SJavaRegex.LINE_SUFFIX);
//	}
//
//	@Test
//	public void testOperatorOrArrays() {
//		System.out.println("== OPERATOR_OR_ARRAYS ==");
//		Pattern opOrArr = Pattern.compile(SJavaRegex.OPERATOR_OR_ARRAYS);
//		Pattern comment = Pattern.compile(SJavaRegex.COMMENT_LINE);
//		Pattern illegalComment = Pattern.compile(SJavaRegex.ILLEGAL_COMMENT);
//		int line_num = 1;
//		for (String line: fileContent) {
//			Matcher opArrMatcher = opOrArr.matcher(line);
//			Matcher comMatcher = comment.matcher(line);
//			Matcher illComMatcher = illegalComment.matcher(line);
//			if (!comMatcher.matches() && !illComMatcher.matches() && opArrMatcher.find()) {
//				System.out.printf("%d\t. %s%n", line_num, line);
//			}
//			++line_num;
//		}
//	}
//
//	@Test
//	public void testVarName() {
//		System.out.println("== VARIABLE_NAME ==");
//		testRegex(SJavaRegex.VARIABLE_NAME);
//	}
//
//	private void testValues(String regex, String type) {
//		Pattern intVal = Pattern.compile(regex);
//		int line_num = 1;
//		for (String line: fileContent) {
//			Matcher mat = intVal.matcher(line);
//			while (mat.find()) {
//				System.out.printf("%d\t. %s , %s = %s%n", line_num, line, type,
//								  line.substring(mat.start(), mat.end()));
//			}
//			++line_num;
//		}
//	}
//
//	@Test
//	public void testIntVal() {
//		System.out.println("== INT_VALUE ==");
//		testValues(SJavaRegex.INT_VALUE, "int");
//	}
//
//	@Test
//	public void testDoubleVal() {
//		System.out.println("== DOUBLE_VALUE ==");
//		testValues(SJavaRegex.DOUBLE_VALUE, "double");
//	}
//
//	@Test
//	public void testBooleanVal() {
//		System.out.println("== BOOL_VALUE ==");
//		testValues(SJavaRegex.BOOL_VALUE, "boolean");
//	}
//
//	@Test
//	public void testCharVal() {
//		System.out.println("== CHAR_VALUE ==");
//		testValues(SJavaRegex.CHAR_VALUE, "char");
//	}
//
//	@Test
//	public void testStringVal() {
//		System.out.println("== STRING_VALUE ==");
//		testValues(SJavaRegex.STRING_VALUE, "string");
//	}
//
//	@Test
//	public void testVarDec() {
//		System.out.println("== VARIABLE_DECLARATION ==");
////		System.out.println(SJavaRegex.VARIABLE_DECLARATION);
////		testRegex(SJavaRegex.VARIABLE_DECLARATION);
//		Pattern pattern = Pattern.compile(SJavaRegex.VARIABLE_DECLARATION);
//		int line_num = 1;
//		for (String line: fileContent) {
//			Matcher matcher = pattern.matcher(line);
//			if (matcher.matches()) {
//				System.out.printf("%d\t. %s%n", line_num, line);
////				for (int i=1; i<= matcher.groupCount(); ++i) {
////					System.out.print(i + ") " + matcher.group(i) + " , ");
////				}
////				System.out.println();
//			}
//			++line_num;
//		}
//	}
//
//	@Test
//	public void testVarAssign1() {
//		System.out.println("== VARIABLE_ASSIGNMENT1 ==");
////		System.out.println(SJavaRegex.VARIABLE_ASSIGNMENT);
//		testRegex(SJavaRegex.VARIABLE_ASSIGNMENT);
//	}
//
//	@Test
//	public void testParameterList() {
//		System.out.println("== METHOD_PARAMETER_LIST ==");
////		System.out.println(SJavaRegex.METHOD_PARAMETER_LIST);
//		testRegex(SJavaRegex.METHOD_PARAMETER_LIST);
//	}
//
//	@Test
//	public void testMethodDef() {
//		System.out.println("== METHOD_DEFINITION ==");
////		System.out.println(SJavaRegex.METHOD_DEFINITION);
//		testRegex(SJavaRegex.METHOD_DEFINITION);
//	}
//
//	@Test
//	public void testArgs() {
//		System.out.println("== ARGUMENTS ==");
//		System.out.println(SJavaRegex.ARGUMENTS);
//		testRegex(SJavaRegex.ARGUMENTS);
//	}
//
//	@Test
//	public void testMethodCall() {
//		System.out.println("== METHOD_CALL ==");
////		System.out.println(SJavaRegex.METHOD_CALL);
//		testRegex(SJavaRegex.METHOD_CALL);
//	}
//
//	@Test
//	public void testIfWhile() {
//		System.out.println("== BLOCK_START ==");
//		System.out.println(SJavaRegex.IF_WHILE_BLOCK);
//		testRegex(SJavaRegex.IF_WHILE_BLOCK);
//	}
//
//}
