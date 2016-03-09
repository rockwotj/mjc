package edu.rosehulman.csse.mjc;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

public class Compiler {
    public static void main(String[] args) {
        MiniJavaLexer lexer = new MiniJavaLexer(new ANTLRInputStream("123+321-+12"));
        for (Token token = lexer.nextToken();
             token.getType() != MiniJavaLexer.EOF;
             token = lexer.nextToken()) {
            switch (token.getType()) {
                case MiniJavaLexer.Plus:
                    System.out.println("plus");
                    break;
                case MiniJavaLexer.Minus:
                    System.out.println("minus");
                    break;
                case MiniJavaLexer.Integer:
                    System.out.println(token.getText());
                    break;
                default:
                    System.err.println("I don't know what this is!");
                    break;
            }
        }

    }
}
