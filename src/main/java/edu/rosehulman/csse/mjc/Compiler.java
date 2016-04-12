package edu.rosehulman.csse.mjc;


import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.reflect.Class;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.List;

public class Compiler {
    public static void main(String[] args) throws IOException {
        // Get lexer
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRFileStream("./src/test/resources/OperatorParsePrecedence.java"));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Pass the tokens to the parser
        MiniJavaParser parser = new MiniJavaParser(tokens);

        // Listen for errors
        ParserErrorListener errorListener = new ParserErrorListener();
        parser.addErrorListener(errorListener);

        // Specify parser grammer entry point
        MiniJavaParser.ProgramContext parseTreeRoot = parser.program();

        // Walk parse tree and create AST
        ParseTreeWalker walker = new ParseTreeWalker();
        ASTBuilder astBuilder = new ASTBuilder();
        walker.walk(astBuilder, parseTreeRoot);
        AbstractSyntaxNode ast = astBuilder.getAbstractSyntaxTree();
//        System.out.println(ast.toString());

        // Build class info
        ClassHierarchyBuilder chb = new ClassHierarchyBuilder(ast);
        chb.walk();
        List<Class> classList = chb.getClasses();

        // Type checking
        TypeChecker typeCheckingWalker = new TypeChecker(ast, classList);
        typeCheckingWalker.walk();

    }
}
