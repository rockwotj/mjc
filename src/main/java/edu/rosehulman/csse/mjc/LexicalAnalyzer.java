package edu.rosehulman.csse.mjc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LexicalAnalyzer {

    public LexicalAnalyzer(File inputFile) {

    }

    public MiniJavaLexer getLexer() {
        return null;
    }

    public List<String> getLexicalStructures() {
        return Arrays.asList();
    }

    @Override
    public String toString() {
        return String.join("\n", getLexicalStructures());
    }
}
