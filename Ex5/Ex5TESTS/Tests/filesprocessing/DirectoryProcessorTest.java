package filesprocessing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectoryProcessorTest {

	@Before
	public void setUp() throws Exception {

	}


	@Test
	public void main() {
//		File dir = new File("Ex5_Supplied Material/Test Directory");
//		File file = new File("Ex5_Supplied Material/Command files/filter002.flt");
//		String sourceDir = dir.getAbsolutePath();
//		String commandFile = file.getAbsolutePath();
//		System.out.println(sourceDir);
//		System.out.println(commandFile);

		String sourceDir = "Ex5_Supplied Material/Test_directory";
		String commandFile = "Ex5_Supplied Material/Command files/filter015.flt";
		String[] input = {sourceDir, commandFile};
		DirectoryProcessor.main(input);


//		String sourceDir = "Ex5TESTS/Test Directory";
//		String commandFilesDir = "Ex5TESTS/Command files";
//
//		File[] commandFiles = new File(commandFilesDir).listFiles();
//		assert commandFiles != null;
//		for (File commandFile: commandFiles) {
//			System.out.println("==== " + commandFile.getName() + " ====");
//			String[] input = {sourceDir, commandFile.getAbsolutePath()};
//			DirectoryProcessor.main(input);
//			System.out.println();
//
//		}




//		String[] commandParts = "all".split("#");
//		System.out.println(Arrays.toString(commandParts));
	}
}