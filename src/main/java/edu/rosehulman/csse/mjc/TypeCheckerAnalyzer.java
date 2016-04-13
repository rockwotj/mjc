package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.reflect.Class;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class TypeCheckerAnalyzer {
    private final ParserErrorListener errorListener;
    private int typeCheckerErrors = 0;

    public TypeCheckerAnalyzer(File file) throws IOException {
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
        ASTBuilder astBuilder = new ASTBuilder();
        walker.walk(astBuilder, progContext);
        AbstractSyntaxNode ast = astBuilder.getAbstractSyntaxTree();

        /* TODO: Add more robust method for detecting errors in type checking */
        try {
            // Build class info
            ClassHierarchyBuilder chb = new ClassHierarchyBuilder(ast);
            chb.walk();
            List<Class> classList = chb.getClasses();

            // Type checking
            TypeChecker typeCheckingWalker = new TypeChecker(ast, classList);
            typeCheckingWalker.walk();
        } catch (Exception e){
            typeCheckerErrors += 1;
        }
    }

    public int getErrorCount() {
        return errorListener.getExceptions().size() + typeCheckerErrors;
    }
}
