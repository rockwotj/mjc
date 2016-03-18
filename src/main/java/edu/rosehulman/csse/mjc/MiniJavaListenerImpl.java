package edu.rosehulman.csse.mjc;

public class MiniJavaListenerImpl extends MiniJavaBaseListener {

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
