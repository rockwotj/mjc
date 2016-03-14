package edu.rosehulman.csse.mjc;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        // Get lexer
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRFileStream("./src/test/resources/SampleParserTestcases/testcase00_09.java"));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        MiniJavaParser parser = new MiniJavaParser(tokens);

        // Listen for errors
        MiniJavaErrorListener errorListener = new MiniJavaErrorListener();
        parser.addErrorListener(errorListener);

        // Specify parser grammer entry point
        MiniJavaParser.ProgramContext progContext = parser.program();

        // Walk parse tree and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        MiniJavaListenerImpl listener = new MiniJavaListenerImpl();

        walker.walk(listener, progContext);
    }

}
