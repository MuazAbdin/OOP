package oop.ex6;

import oop.ex6.verifier.SyntaxVerifier;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class SyntaxVerifierTest {

	private static final File input = new File("Tests/sample.sjavac");
	private static FileInputStream fileInputStream;
	private static String[] fileContent;

	@BeforeClass
	public static void setUp() {
		try {
			fileInputStream = new FileInputStream(input);
			byte[] inputData = new byte[(int) input.length()];
			fileInputStream.read(inputData);
			fileInputStream.close();
			fileContent = new String(inputData).split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifySyntax() throws VerifierExceptions {
		SyntaxVerifier syntaxVerifier = new SyntaxVerifier(fileContent);
		syntaxVerifier.verifySyntax();
	}
}