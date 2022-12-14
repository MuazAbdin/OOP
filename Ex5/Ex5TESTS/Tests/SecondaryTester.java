import filesprocessing.DirectoryProcessor;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SecondaryTester {

//	private static final String DIR_PATH = "src\\test\\java\\";
	private static final String DIR_PATH = "Ex5TESTS/";
	private static final String COMMAND_FILES_PATH = DIR_PATH + "MyCommandFiles/";
	private static final String OUTPUT_FILES_PATH = DIR_PATH + "MyOutputFiles/";
	private static final String TEST_FILES_PATH = DIR_PATH + "Test_directory/";

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void tests() throws Exception {
		String expected, actual;
		PrintStream org_out = System.out;
		PrintStream org_err = System.err;

		File[] command_files = (new File(COMMAND_FILES_PATH)).listFiles();

		for (File file : command_files) {
			if (file.getName().equals(".DS_Store"))
				continue;
			System.out.println("== " + file.getName() + " ==");
			expected = new String(Files.readAllBytes(
					Paths.get(OUTPUT_FILES_PATH + file.getName())));
			expected = expected.replaceAll("(\\r)", "");
			expected = expected.replaceAll(".DS_Store\n", "");


			ByteArrayOutputStream bytes_err = new ByteArrayOutputStream();
			System.setErr(new PrintStream(bytes_err, true));
			ByteArrayOutputStream bytes_out = new ByteArrayOutputStream();
			System.setOut(new PrintStream(bytes_out, true));

			String[] newArgs = {TEST_FILES_PATH, COMMAND_FILES_PATH + file.getName()};
			DirectoryProcessor.main(newArgs);

			System.setErr(org_err);
			System.setOut(org_out);

			actual = "";
			if (!bytes_err.toString().isEmpty()) {
				actual += bytes_err.toString();
			}
			actual += bytes_out.toString();
//			actual = actual.replaceAll("(\\r)", "");

//			Assert.assertEquals("difference found in file " + file.getName(), expected, actual);
			collector.checkThat("difference found in file " + file.getName(), actual,
								CoreMatchers.equalTo(expected));
		}
	}
}


