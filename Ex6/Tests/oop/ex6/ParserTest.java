package oop.ex6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserTest {

	@Test
	public void parseFinal() {

		String[] varOptions = {"final int num1 = -1;",
				"int num2;",
				"double if, num4;",
				"num5 = num6;",
				"int _while = 8;",
				"int num8, num9 = 18, num10;"};

		String keywords = "int|double|boolean|char|String|void|final|if|while|true|false|return";

		Pattern keyword = Pattern.compile(keywords);
		Pattern finalPattern = Pattern.compile("final");
		Pattern typePattern = Pattern.compile(SJavaRegex.TYPE);
		Pattern varDecPattern = Pattern.compile("(?:" + SJavaRegex.VARIABLE_NAME + ")\\s*[;,]");
		Pattern varAssignPattern = Pattern.compile("(?:" + SJavaRegex.VARIABLE_NAME + ")\\s*=\\s*" +
												   "(?:" + SJavaRegex.VARIABLE_VALUE + ")\\s*[,;]");

		for (String str: varOptions) {
			System.out.println("== " + str + " ==");
//			Matcher matcher = finalPattern.matcher(str);
//			Matcher matcher = typePattern.matcher(str);
//			Matcher matcher = varDecPattern.matcher(str);
			Matcher matcher = varAssignPattern.matcher(str);

			while (matcher.find()) {
//				String name = str.substring(matcher.start(), matcher.end()).replaceAll("[;,]|\\s*", "");

				String assign = str.substring(matcher.start(), matcher.end());
				int idx = assign.indexOf('=');
				String name = assign.substring(0, idx).trim();
				String value = assign.substring(idx+1).replaceFirst("[;,]", "").trim();

				if (!keyword.matcher(name).matches() && !keyword.matcher(value).matches())
					System.out.println(name + " = " + value);
			}
		}
	}

	@Test
	public void varParseTest() throws VerifierExceptions {
		String[] varOptions = {"final int num1 = -1;",
				"int num2;",
				"double num, num4;",
				"num5 = num6;",
				"int _while = 8;",
				"int num8, num9 = 18, num10;"};

		for (String str: varOptions) {
			Parser parser = new Parser(str);
			ArrayList<String[]> details = parser.parseVarLine();
			for (String[] det: details ) {
				System.out.print(Arrays.toString(det) + " , ");
			}
			System.out.println();
//			System.out.println(parser.parseVarLine());
		}

	}

	@Test
	public void methodParseTest() throws VerifierExceptions {
//		System.out.println(SJavaRegex.METHOD_DEFINITION);
//		Matcher m = SJavaRegex.METHOD_DEFINITION.matcher("void test() { ");
//		System.out.println(m.matches());

//		Parser parser = new Parser("	void 	print ( final	 int ID ,String name,int age){");
		Parser parser = new Parser("void test(int ID) { ");
		System.out.println(parser.parseMethodSignature());
	}

	@Test
	public void callParseTest() {
//		System.out.println(SJavaRegex.METHOD_CALL);
//		Matcher m = SJavaRegex.METHOD_CALL.matcher("test(\"hello\", 5);");
//		System.out.println(m.matches());

		//		Parser parser = new Parser("	void 	print ( final	 int ID ,String name,int age){");
		Parser parser = new Parser("  test   (\"hello\"  ,5.67   ) ;  ");
		System.out.println(parser.parseCallLine());
	}

	@Test
	public void ifWhileParseTest() {
		//		System.out.println(SJavaRegex.METHOD_CALL);
		//		Matcher m = SJavaRegex.METHOD_CALL.matcher("test(\"hello\", 5);");
		//		System.out.println(m.matches());

		//		Parser parser = new Parser("	void 	print ( final	 int ID ,String name,int age){");
//		Parser parser = new Parser("if (true) {");
//		Parser parser = new Parser("if (false) {");
//		Parser parser = new Parser("if (0) {");
//		Parser parser = new Parser("if (5.32) {");
//		Parser parser = new Parser("if (false || true || age && -1 && 0 || 5.34) {");
		Parser parser = new Parser("while (false || age && -1 && 0 || -5.34) {");
		System.out.println(parser.parseIfWhileLine());


//		Pattern t = Pattern.compile("-?\\d+(?:\\.\\d++)?");
//		Matcher m = t.matcher("5.32");
//		if (m.matches()) {
//			System.out.print("match:  ");
//			System.out.println("5.32".substring(m.start(), m.end()));
//		}
//		while(m.find()) {
//			System.out.print("find:  ");
//			System.out.println("5.32".substring(m.start(), m.end()));
//		}

	}

	@Test
	public void hasMoreTokens() {
	}

	@Test
	public void advance() {
	}

	@Test
	public void back() {
	}

	@Test
	public void nextLine() {
	}

	@Test
	public void lineKind() {
	}

//	@Test
//	public void getType() {
//		Parser p = new Parser();
//		assertEquals("int", p.getType("int age;"));
//		System.out.println(p.getType("int age;"));
//		assertEquals("int", p.getType("	int age;"));
//		System.out.println(p.getType("	int age;"));
//		assertNull(p.getType(" intage;"));
//		System.out.println(p.getType("    intage;"));
//		System.out.println(p.getType("int int=0;"));
//
//	}
//
//	@Test
//	public void getName() {
//		Parser p = new Parser();
//		System.out.println(p.getName("age;"));
//		System.out.println(p.getName("age = 0;"));
//		System.out.println(p.getName("age= 9;"));
//		System.out.println(p.getName("age =9;"));
//		System.out.println(p.getName("age=-43;"));
//		System.out.println(p.getName("age=8 ; "));
//		System.out.println(p.getName("		age  ; "));
//
//		System.out.println("==" + "man ;".substring(5) + "==");
//	}
//
//	@Test
//	public void getValue() {
//		Parser p = new Parser();
//		System.out.println(p.getValue("final int age;"));
//		System.out.println(p.getValue("= 5;"));
//		System.out.println(p.getValue("=\"HELLO\";"));
//		System.out.println(p.getValue("='c';"));
//		System.out.println(p.getValue("= true ;"));
//	}
//
//	@Test
//	public void isConstant() {
//		Parser p = new Parser();
//		System.out.println(p.isConstant("final int age;"));
//		System.out.println(p.isConstant("age = 0;"));
//		System.out.println(p.isConstant("final int age, string name) {"));
//	}
}