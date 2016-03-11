package edu.rosehulman.csse.mjc;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test
    public void runFullTests() throws IOException {
        File folder = new File("src/test/resources/LexerFullTests");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String baseFileName = file.getName().replaceFirst("[.][^.]+$", "");
                List<String> lexicalStructure = new LexicalAnalyzer(file).getLexicalStructures();
                File correctOutput = new File("src/test/resources/LexerExpectedOutput/" + baseFileName + ".out");
                List<String> expectedStructure = Files.readAllLines(correctOutput.toPath());
                System.out.println("Running test for: " + baseFileName);
                for (int i = 0; i < expectedStructure.size(); i++) {
                    assertEquals(expectedStructure.get(i), lexicalStructure.get(i));
                }
                break; // Only test the first file for now.
            }
        }
    }


}
