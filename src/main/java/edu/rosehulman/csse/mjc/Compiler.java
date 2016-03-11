package edu.rosehulman.csse.mjc;


import java.io.File;
import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        LexicalAnalyzer analyzer = new LexicalAnalyzer(new File("./src/test/resources/LexerFullTests/testcase00_01.java"));
        System.out.println(analyzer);
    }
}
