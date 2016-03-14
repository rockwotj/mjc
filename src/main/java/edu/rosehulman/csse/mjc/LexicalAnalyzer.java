package edu.rosehulman.csse.mjc;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {

    private final Lexer lexer;

    public LexicalAnalyzer(File file) throws IOException {
        lexer = new MiniJavaLexOnly(new ANTLRFileStream(file.getAbsolutePath()));
    }

    public Lexer getLexer() {
        return lexer;
    }

    public List<String> getLexicalStructures() {
        List<String> tokens = new ArrayList<>();
        for (Token token = lexer.nextToken();
                    token.getType() != MiniJavaLexOnly.EOF;
                    token = lexer.nextToken()) {
            if (token.getType() == MiniJavaLexOnly.Whitespace) continue;
            if (token.getType() == MiniJavaLexOnly.Comment) continue;
            tokens.add(MiniJavaLexOnly.VOCABULARY.getDisplayName(token.getType()) +
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
