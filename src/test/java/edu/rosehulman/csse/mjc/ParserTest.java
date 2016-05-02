package edu.rosehulman.csse.mjc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ParserTest {

    private static final String INPUT_FOLDER = "src/test/resources/SampleParserTestcases";
    private static final String INPUT_EXT = ".java";

    private static List<String> VALID_PROGRAMS = Arrays.asList(
            "testcase00_09.java",
            "testcase00_11.java",
            "testcase00_16.java");


    @Parameterized.Parameters(name = "{0}")
    public static Collection<File[]> testCases() {
        File folder = new File(INPUT_FOLDER);
        List<File[]> fileCollection = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();
        for (File inputFile : listOfFiles) {
            if (inputFile.isFile() && inputFile.getName().endsWith(INPUT_EXT)) {
                fileCollection.add(new File[]{inputFile});
            }
        }
        return fileCollection;
    }

    private File inputFile;

    public ParserTest(File inputFile) {
        this.inputFile = inputFile;
    }

    @Test
    public void sampleParserTest() throws IOException {
        String baseFileName = inputFile.getName().replaceFirst("[.][^.]+$", "");
        boolean validProgram = VALID_PROGRAMS.contains(inputFile.getName());
        ParserAnalyzer parser = new ParserAnalyzer(inputFile);
        if (validProgram) {
            assertEquals(baseFileName + " should be a valid program", 0, parser.getErrorCount());
        } else {
            assertTrue(baseFileName + " should have errors", parser.getErrorCount() > 0);
        }
    }

}

