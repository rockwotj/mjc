package edu.rosehulman.csse.mjc;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.IOException;

public class ParserAnalyzer {
    private final ParserErrorListener errorListener;

    public ParserAnalyzer(File file) throws IOException {
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRFileStream(file.getAbsolutePath()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        MiniJavaParser parser = new MiniJavaParser(tokens);

        // Listen for errors
        errorListener = new ParserErrorListener();
        parser.addErrorListener(errorListener);

        // Specify parser grammer entry point
        MiniJavaParser.ProgramContext progContext = parser.program();

        // Walk parse tree and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        MiniJavaListenerImpl listener = new MiniJavaListenerImpl();

        walker.walk(listener, progContext);
    }

    public int getErrorCount() {
        return errorListener.getExceptions().size();
    }

    private class MiniJavaListenerImpl extends MiniJavaBaseListener {

        @Override
        public void enterBlock(MiniJavaParser.BlockContext ctx) {
            System.out.println("block");
        }

        @Override
        public void enterVarDecl(MiniJavaParser.VarDeclContext ctx) {
            System.out.println("VarDecl");
        }

        @Override
        public void enterIfElse(MiniJavaParser.IfElseContext ctx) {
            System.out.println("IfElse");
        }

        @Override
        public void enterAssigment(MiniJavaParser.AssigmentContext ctx) {
            System.out.println("Assigment");
        }

        @Override
        public void enterWhileDecl(MiniJavaParser.WhileDeclContext ctx) {
            System.out.println("WhileDecl");
        }

        @Override
        public void enterPrint(MiniJavaParser.PrintContext ctx) {
            System.out.println("Print");
        }
    }
}
