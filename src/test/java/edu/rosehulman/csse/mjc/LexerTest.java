package edu.rosehulman.csse.mjc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LexerTest {


    private static final String INPUT_FOLDER = "src/test/resources/SampleLexerTestcases/";
    private static final String OUTPUT_FOLDER = "src/test/resources/SampleLexerTestcases/";

    private static final String INPUT_EXT = ".java";
    private static final String OUTPUT_EXT = ".out";

    @Parameterized.Parameters()
    public static Collection<File[]> testCases() {
        File folder = new File(INPUT_FOLDER);
        List<File[]> fileCollection = new ArrayList<>();
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

    public LexerTest(File inputTest, File outputFile) {
        this.inputTest = inputTest;
        this.outputFile = outputFile;
    }

    @Test
    public void runFullTests() throws IOException {
        List<String> lexicalStructure = new LexicalAnalyzer(inputTest).getLexicalStructures();
        List<String> expectedStructure = Files.readAllLines(outputFile.toPath());
        System.out.println("Running test for: " + inputTest.getName());
        for (int i = 0; i < expectedStructure.size(); i++) {
            assertEquals(expectedStructure.get(i), lexicalStructure.get(i));
        }
    }
}
