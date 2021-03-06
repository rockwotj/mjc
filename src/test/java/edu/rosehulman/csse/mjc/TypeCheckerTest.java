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
public class TypeCheckerTest {

    private static final String INPUT_FOLDER = "src/test/resources/TypeCheckerFullTests";
    private static final String INPUT_EXT = ".java";

    private static List<String> VALID_PROGRAMS = Arrays.asList(
            "testcase00_58.java",
            "testcase00_15.java",
            "testcase95-01.java",
            "testcase00_20.java",
            "testcase97-04.java",
            "testcase00_18.java",
            "testcase00_38.java",
            "testcase00_72.java",
            "testcase96-04.java",
            "testcase00_05.java",
            "testcase00_66.java",
            "testcase00_16.java",
            "testcase00_47.java",
            "testcase00_24.java",
            "testcase94-01.java",
            "testcase03_01.java",
            "testcase00_25.java",
            "testcase00_51.java",
            "testcase00_95.java",
            "testcase00_93.java",
            "testcase00_54.java",
            "testcase00_96.java",
            "testcase00_19.java",
            "testcase00_74.java",
            "testcase00_35.java",
            "testcase00_84.java",
            "testcase02_04.java",
            "testcase00_56.java",
            "testcase04_05.java",
            "testcase00_14.java",
            "testcase00_71.java",
            "testcase00_06.java",
            "testcase00_17.java",
            "testcase00_89.java",
            "testcase00_75.java",
            "testcase93-01.java",
            "testcase00_88.java",
            "testcase00_59.java"
    );


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

    public TypeCheckerTest(File inputFile) {
        this.inputFile = inputFile;
    }

    @Test
    public void sampleTypeCheckerTest() throws IOException {
        String baseFileName = inputFile.getName().replaceFirst("[.][^.]+$", "");
        boolean validProgram = VALID_PROGRAMS.contains(inputFile.getName());
        TypeCheckerAnalyzer typeChecker = new TypeCheckerAnalyzer(inputFile);
        if (validProgram) {
            assertEquals(baseFileName + " should be a valid program", 0, typeChecker.getErrorCount());
        } else {
            assertTrue(baseFileName + " should have errors", typeChecker.getErrorCount() > 0);
        }
    }

}
