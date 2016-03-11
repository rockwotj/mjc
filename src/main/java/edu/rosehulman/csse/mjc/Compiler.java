package edu.rosehulman.csse.mjc;


import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRFileStream("./src/test/resources/SimpleTokensTest.txt"));
        for (Token token = lexer.nextToken();
             token.getType() != MiniJavaLexer.EOF;
             token = lexer.nextToken()) {
            switch (token.getType()) {
//                case MiniJavaLexer.Plus:
//                    System.out.println("plus");
//                    break;
//                case MiniJavaLexer.Minus:
//                    System.out.println("minus");
//                    break;
//                case MiniJavaLexer.Integer:
//                    System.out.println(token.getText());
//                    break;
//                default:
//                    System.err.println("I don't know what this is!");
//                    break;
            }
        }

    }
}
