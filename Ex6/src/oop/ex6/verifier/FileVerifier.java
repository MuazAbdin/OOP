package oop.ex6.verifier;

import oop.ex6.VerifierExceptions;
import oop.ex6.FileReader;

import java.io.IOException;

/**
 * A class combine all partial verifiers
 */
public class FileVerifier {

	// ################### //
	// #### CONSTANTS #### //
	// ################### //

	/** Code check results */
	private static final int LEGAL_CODE = 0;
	private static final int ILLEGAL_CODE = 1;
	private static final int IO_ERROR_CODE = 2;

	/** IO ERROR MESSAGE */
	private static final String IO_ERR_MSG = "ERROR: IO error occurred";

	// #################### //
	// #### ATTRIBUTES #### //
	// #################### //

	// ##################### //
	// #### CONSTRUCTOR #### //
	// ##################### //

	// ################# //
	// #### METHODS #### //
	// ################# //

	/**
	 * Verifies that code has no compilation errors.
	 * @param sourceFile source file (.sjava) to check.
	 * @return LEGAL_CODE (=0) if the code is legal,
	 * 		   ILLEGAL_CODE (=1) if code is illegal,
	 * 		   IO_ERROR_CODE (=2) if there is IO errors.
	 */
	public static int verifyCode(String sourceFile) {
		try {
			String[] lines = new FileReader(sourceFile).getFileContent();
			SyntaxVerifier syntaxVerifier = new SyntaxVerifier(lines);
			SemanticsVerifier semanticsVerifier = new SemanticsVerifier(lines);

			syntaxVerifier.verifySyntax();
			semanticsVerifier.setupGlobalScope();
			semanticsVerifier.verifyLocalScopes();

			System.out.println(LEGAL_CODE);
			return LEGAL_CODE;
		}
		catch (IOException e){
			System.out.println(IO_ERROR_CODE);
			System.err.println(IO_ERR_MSG);
			return IO_ERROR_CODE;
		}
		catch (VerifierExceptions e){
			System.out.println(ILLEGAL_CODE);
			System.err.print(e.getMessage());
			return ILLEGAL_CODE;
		}
	}

}
