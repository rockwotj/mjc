package edu.rosehulman.csse.mjc;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;

/**
 * Created by rockwotj on 3/14/16.
 */
public class ParserAnalyzer {
    private final MiniJavaErrorListener errorListener;
    private final MiniJavaListenerImpl listener;

    public ParserAnalyzer(File file) throws IOException {
        MiniJava lexer = new MiniJava(new ANTLRFileStream(file.getAbsolutePath()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        MiniJavaParser parser = new MiniJavaParser(tokens);

        // Listen for errors
        errorListener = new MiniJavaErrorListener();
        parser.addErrorListener(errorListener);

        // Specify parser grammer entry point
        MiniJavaParser.ProgramContext progContext = parser.program();

        // Walk parse tree and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        listener = new MiniJavaListenerImpl();

        walker.walk(listener, progContext);
    }

     public int getErrorCount() {
         return errorListener.getExceptions().size();
     }
}
