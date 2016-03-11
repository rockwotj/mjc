package edu.rosehulman.csse.mjc;


import java.io.File;
import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        LexicalAnalyzer analyzer = new LexicalAnalyzer(new File("./src/test/resources/LexerFullTests/testcaseA7-01.java"));
        System.out.println(analyzer);
    }
}
