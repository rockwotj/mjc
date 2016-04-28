package edu.rosehulman.csse.mjc.ast;

import edu.rosehulman.csse.mjc.MiniJavaParser;

public abstract class BaseWalker extends Walker {

    public BaseWalker(AbstractSyntaxNode root) {
        super(root);
    }

    @Override
    protected void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {

    }

    @Override
    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {

    }

    @Override
    protected void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {

    }

    @Override
    protected void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {

    }

    @Override
    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {

    }

    @Override
    protected void exitFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {

    }

    @Override
    protected void exitType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {

    }

    @Override
    protected void exitStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {

    }

    @Override
    protected void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {

    }

    @Override
    protected void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {

    }

    @Override
    protected void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {

    }

    @Override
    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {

    }

    @Override
    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {

    }

    @Override
    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {

    }

    @Override
    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {

    }

    @Override
    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {

    }

    @Override
    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {

    }

    @Override
    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void enterMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {

    }

    @Override
    protected void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void betweenProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current, int count) {

    }

    @Override
    protected void betweenMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current, int count) {

    }

    @Override
    protected void betweenClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current, int count) {

    }

    @Override
    protected void betweenClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current, int count) {

    }

    @Override
    protected void betweenMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current, int count) {

    }

    @Override
    protected void betweenFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current, int count) {

    }

    @Override
    protected void betweenType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current, int count) {

    }

    @Override
    protected void betweenStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current, int count) {

    }

    @Override
    protected void betweenVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current, int count) {

    }

    @Override
    protected void betweenBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current, int count) {

    }

    @Override
    protected void betweenIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current, int count) {

    }

    @Override
    protected void betweenWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current, int count) {

    }

    @Override
    protected void betweenPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current, int count) {

    }

    @Override
    protected void betweenAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current, int count) {

    }

    @Override
    protected void betweenExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current, int count) {

    }

    @Override
    protected void betweenLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current, int count) {

    }

    @Override
    protected void betweenLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current, int count) {

    }

    @Override
    protected void betweenEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count) {

    }

    @Override
    protected void betweenNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count) {

    }

    @Override
    protected void betweenLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count) {

    }

    @Override
    protected void betweenMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count) {

    }

    @Override
    protected void betweenMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count) {

    }

    @Override
    protected void betweenDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count) {

    }

    @Override
    protected void betweenBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count) {

    }

    @Override
    protected void betweenNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count) {

    }

    @Override
    protected void betweenMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current, int count) {

    }

    @Override
    protected void betweenInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {

    }

    @Override
    protected void enterNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void enterBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void enterDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void enterMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void enterMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void enterPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void enterGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void enterEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void enterLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {

    }

    @Override
    protected void enterLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {

    }

    @Override
    protected void enterExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {

    }

    @Override
    protected void enterAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {

    }

    @Override
    protected void enterPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {

    }

    @Override
    protected void enterWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {

    }

    @Override
    protected void enterIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {

    }

    @Override
    protected void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {

    }

    @Override
    protected void enterVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {

    }

    @Override
    protected void enterStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {

    }

    @Override
    protected void enterType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {

    }

    @Override
    protected void enterFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {

    }

    @Override
    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {

    }

    @Override
    protected void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {

    }

    @Override
    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {

    }

    @Override
    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {

    }

    @Override
    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {

    }
}
