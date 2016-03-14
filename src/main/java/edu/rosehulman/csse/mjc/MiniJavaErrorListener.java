package edu.rosehulman.csse.mjc;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class MiniJavaErrorListener extends BaseErrorListener {

    private List<RecognitionException> exceptions = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        System.err.printf("Syntax Error: line: %d, char: %d, message: %s%n", line, charPositionInLine, msg);
        exceptions.add(e);
    }


    public List<RecognitionException> getExceptions() {
        return exceptions;
    }

}