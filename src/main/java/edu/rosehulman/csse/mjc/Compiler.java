package edu.rosehulman.csse.mjc;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.reflect.Class;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Compiler {

    @Parameter(names = {"--output", "-o"})
    String output = "./build/out.ll";
    @Parameter(names = {"--input", "-i"})
    String input = "./src/test/resources/FinalFullTests/testcase97-02.java";
//    String input = "./src/test/resources/SampleMini.java";
    @Parameter(names = {"--help", "-h"}, help = true)
    boolean help;

    public static void main(String[] args) throws IOException {
        Compiler main = new Compiler();
        new JCommander(main, args);
        main.run();
    }

    public void run() throws IOException {
        // Get lexer
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRFileStream(input));

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

        // Generate Code :)
        CodeGenerator codeGenWalker = new CodeGenerator(ast, classList);
        codeGenWalker.walk();
        String outputIR = codeGenWalker.toString();
        Files.write(Paths.get(output), outputIR.getBytes());
    }
}
