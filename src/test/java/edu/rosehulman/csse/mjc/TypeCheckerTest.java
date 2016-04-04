package edu.rosehulman.csse.mjc;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class TypeCheckerTest {

    private static List<String> VALID_PROGRAMS = Arrays.asList(
            "testcase00_58.out",
            "testcase00_15.out",
            "testcase95-01.out",
            "testcase00_20.out",
            "testcase97-04.out",
            "testcase00_18.out",
            "testcase00_38.out",
            "testcase00_72.out",
            "testcase96-04.out",
            "testcase00_05.out",
            "testcase00_66.out",
            "testcase00_16.out",
            "testcase00_47.out",
            "testcase00_24.out",
            "testcase94-01.out",
            "testcase03_01.out",
            "testcase00_25.out",
            "testcase00_51.out",
            "testcase00_95.out",
            "testcase00_93.out",
            "testcase00_54.out",
            "testcase00_96.out",
            "testcase00_19.out",
            "testcase00_74.out",
            "testcase00_35.out",
            "testcase00_84.out",
            "testcase02_04.out",
            "testcase00_56.out",
            "testcase04_05.out",
            "testcase00_14.out",
            "testcase00_71.out",
            "testcase00_06.out",
            "testcase00_17.out",
            "testcase00_89.out",
            "testcase00_75.out",
            "testcase93-01.out",
            "testcase00_88.out",
            "testcase00_59.out");


    @Test
    public void sampleTypeCheckerTest() throws IOException {
        File folder = new File("./src/test/resources/TypeCheckerFullTests/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                String baseFileName = file.getName().replaceFirst("[.][^.]+$", "");
                boolean validProgram = VALID_PROGRAMS.contains(file.getName());
                TypeCheckerAnalyzer typeChecker = new TypeCheckerAnalyzer(file);
                // if (validProgram) {
                //     assertEquals(baseFileName + " should be a valid program", 0, typeChecker.getErrorCount());
                // } else {
                //     assertTrue(baseFileName + " should have errors", typeChecker.getErrorCount() > 0);
                // }
            }
        }
    }

}
