package edu.rosehulman.csse.mjc;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ParserTest {

    public static List<String> VALID_PROGRAMS = Arrays.asList(
            "testcase00_09.java",
            "testcase00_11.java",
            "testcase00_16.java");


    @Test
    public void sampleParserTest() throws IOException {
        File folder = new File("./src/test/resources/SampleParserTestcases/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                String baseFileName = file.getName().replaceFirst("[.][^.]+$", "");
                boolean validProgram = VALID_PROGRAMS.contains(file.getName());
                ParserAnalyzer parser = new ParserAnalyzer(file);
                if (validProgram) {
                    assertEquals(baseFileName + " should be a valid program", 0, parser.getErrorCount());
                } else {
                    assertTrue(baseFileName + " should have errors", parser.getErrorCount() > 0);
                }
            }
        }
    }

}
