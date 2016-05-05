package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.helpers.OSValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CompilerTest {

    private static final String INPUT_FOLDER = "src/test/resources/FinalFullTests/";
    private static final String OUTPUT_FOLDER = "src/test/resources/FinalExpectedOutput/";

    private static final String INPUT_EXT = ".java";
    private static final String OUTPUT_EXT = ".out";

    private static final boolean SIMPLE_TEST = false;

    @Parameterized.Parameters(name = "{0}")
    public static Collection<File[]> testCases() {
        List<File[]> fileCollection = new ArrayList<>();
        if (SIMPLE_TEST) {
            File input = new File(INPUT_FOLDER + "testcase00_14" + INPUT_EXT);
            File output = new File(OUTPUT_FOLDER + "testcase00_14" + OUTPUT_EXT);
            fileCollection.add(new File[]{input, output});
            return fileCollection;
        }
        File folder = new File(INPUT_FOLDER);
        File[] listOfFiles = folder.listFiles();
        for (File inputFile : listOfFiles) {
            if (inputFile.isFile() && inputFile.getName().endsWith(INPUT_EXT)) {
                String baseFileName = inputFile.getName().replaceFirst("[.][^.]+$", "");
                File correctOutput = new File(OUTPUT_FOLDER + baseFileName + OUTPUT_EXT);
                fileCollection.add(new File[]{inputFile, correctOutput});
            }
        }
        return fileCollection;
    }

    private final File inputTest;
    private final File outputFile;

    public CompilerTest(File inputTest, File outputFile) {
        this.inputTest = inputTest;
        this.outputFile = outputFile;
    }

    @Test
    public void test() throws Exception {
        String intermediateOutput = "./build/out.ll";

        // Compile
        Compiler c = new Compiler();
        c.input = inputTest.getAbsolutePath();
        c.output = intermediateOutput;
        c.run(); // Generate ll file

        // Run lli, the JIT VM
        Process p = getLLI(intermediateOutput);
        p.waitFor();  // wait for process to finish then continue.

        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        List<String> result = new ArrayList<>();
        while ((line = bri.readLine()) != null) {
            result.add(line);
        }
        List<String> expectedStructure = Files.readAllLines(outputFile.toPath());

        for (int i = 0; i < expectedStructure.size(); i++) {
            String msg = String.format("Output Line %d of %s", i, inputTest.getName());
            assertEquals(msg, expectedStructure.get(i), result.get(i));
        }
    }


    public Process getLLI(String input) throws IOException {
        String osString;
        if (OSValidator.isMac()) {
            osString = "darwin";
        } else if (OSValidator.isUnix()) {
            osString = "ubuntu";
        } else {
            throw new RuntimeException("OS not supported");
        }
        ProcessBuilder pb = new ProcessBuilder("src/main/resources/bin/" + osString + "/lli", input);
        pb.directory(new File("."));
        pb.redirectErrorStream(true);
        return pb.start();
    }
}