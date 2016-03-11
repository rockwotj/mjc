package edu.rosehulman.csse.mjc;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {

    private final MiniJava lexer;

    public LexicalAnalyzer(File file) throws IOException {
        lexer = new MiniJava(new ANTLRFileStream(file.getAbsolutePath()));
    }

    public Lexer getLexer() {
        return lexer;
    }

    public List<String> getLexicalStructures() {
        List<String> tokens = new ArrayList<String>();
        for (Token token = lexer.nextToken();
                    token.getType() != MiniJava.EOF;
                    token = lexer.nextToken()) {
            if (token.getType() == MiniJava.Whitespace) continue;
            if (token.getType() == MiniJava.Comment) continue;
            tokens.add(MiniJava.VOCABULARY.getDisplayName(token.getType()) +
                    ", " +
                    token.getText());
        }
        return tokens;
    }

    @Override
    public String toString() {
        return String.join("\n", getLexicalStructures());
    }
}
