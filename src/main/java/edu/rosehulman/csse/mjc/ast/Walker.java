package edu.rosehulman.csse.mjc.ast;

import edu.rosehulman.csse.mjc.MiniJavaParser;

import java.util.List;
import java.util.Stack;

public abstract class Walker {

    private final AbstractSyntaxNode root;
    private Stack<AbstractSyntaxNode> parents;

    public Walker(AbstractSyntaxNode root) {
        this.root = root;
        this.parents = new Stack<>();
    }

    public AbstractSyntaxNode getParent() {
        return parents.peek();
    }

    public void walk() {
        walk(root);
    }

    private void walk(AbstractSyntaxNode current) {
        switch (current.getType()) {
            case program:
                enterProgram(current);
                break;
            case mainClassDecl:
                enterMainClassDecl(current);
                break;
            case classDecl:
                enterClassDel(current);
                break;
            case classVarDecl:
                enterClassVarDecl(current);
                break;
            case methodDecl:
                enterMethodDecl(current);
                break;
            case formal:
                enterFormal(current);
                break;
            case type:
                enterType(current);
                break;
            case stmt:
                enterStmt(current);
                break;
            case varDecl:
                enterVarDecl(current);
                break;
            case block:
                enterBlock(current);
                break;
            case ifElse:
                enterIfElse(current);
                break;
            case whileDecl:
                enterWhile(current);
                break;
            case print:
                enterPrint(current);
                break;
            case assigment:
                enterAssignment(current);
                break;
            case expr:
                enterExpr(current);
                break;
            case logicalOr:
                enterLogicalOr(current);
                break;
            case logicalAnd:
                enterLogicalAnd(current);
                break;
            case equals:
                enterEquals(current);
                break;
            case notEquals:
                enterNotEquals(current);
                break;
            case lessThan:
                enterLessThan(current);
                break;
            case greaterThan:
                enterGreaterThan(current);
                break;
            case lessThanEquals:
                enterLessThanEquals(current);
                break;
            case greaterThanEquals:
                enterGreaterThanEquals(current);
                break;
            case plus:
                enterPlus(current);
                break;
            case minus:
                enterMinus(current);
                break;
            case mult:
                enterMult(current);
                break;
            case div:
                enterDiv(current);
                break;
            case bang:
                enterBang(current);
                break;
            case negative:
                enterNeg(current);
                break;
            case integer:
                enterInt(current);
                break;
            case bool:
                enterBool(current);
                break;
            case nil:
                enterNull(current);
                break;
            case self:
                enterThis(current);
                break;
            case id:
                enterId(current);
                break;
            case constructor:
                enterConstructor(current);
                break;
        }
        parents.push(current);
        List<AbstractSyntaxNode> children = current.getChildren();
        for (AbstractSyntaxNode child : children) {
            walk(child);
        }
        parents.pop();
        switch (current.getType()) {
            case program:
                exitProgram(current);
                break;
            case mainClassDecl:
                exitMainClassDecl(current);
                break;
            case classDecl:
                exitClassDel(current);
                break;
            case classVarDecl:
                exitClassVarDecl(current);
                break;
            case methodDecl:
                exitMethodDecl(current);
                break;
            case formal:
                exitFormal(current);
                break;
            case type:
                exitType(current);
                break;
            case stmt:
                exitStmt(current);
                break;
            case varDecl:
                exitVarDecl(current);
                break;
            case block:
                exitBlock(current);
                break;
            case ifElse:
                exitIfElse(current);
                break;
            case whileDecl:
                exitWhile(current);
                break;
            case print:
                exitPrint(current);
                break;
            case assigment:
                exitAssignment(current);
                break;
            case expr:
                exitExpr(current);
                break;
            case logicalOr:
                exitLogicalOr(current);
                break;
            case logicalAnd:
                exitLogicalAnd(current);
                break;
            case equals:
                exitEquals(current);
                break;
            case notEquals:
                exitNotEquals(current);
                break;
            case lessThan:
                exitLessThan(current);
                break;
            case greaterThan:
                exitGreaterThan(current);
                break;
            case lessThanEquals:
                exitLessThanEquals(current);
                break;
            case greaterThanEquals:
                exitGreaterThanEquals(current);
                break;
            case plus:
                exitPlus(current);
                break;
            case minus:
                exitMinus(current);
                break;
            case mult:
                exitMult(current);
                break;
            case div:
                exitDiv(current);
                break;
            case bang:
                exitBang(current);
                break;
            case negative:
                exitNeg(current);
                break;
            case integer:
                exitInt(current);
                break;
            case bool:
                exitBool(current);
                break;
            case nil:
                exitNull(current);
                break;
            case self:
                exitThis(current);
                break;
            case id:
                exitId(current);
                break;
            case constructor:
                exitConstructor(current);
                break;
        }
    }

    protected abstract void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current);

    protected abstract void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current);

    protected abstract void exitClassDel(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current);

    protected abstract void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current);

    protected abstract void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current);

    protected abstract void exitFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current);

    protected abstract void exitType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current);

    protected abstract void exitStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current);

    protected abstract void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current);

    protected abstract void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current);

    protected abstract void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current);

    protected abstract void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current);

    protected abstract void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current);

    protected abstract void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current);

    protected abstract void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current);

    protected abstract void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current);

    protected abstract void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current);

    protected abstract void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current);

    protected abstract void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current);

    protected abstract void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current);

    protected abstract void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current);

    protected abstract void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current);

    protected abstract void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current);

    protected abstract void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current);

    protected abstract void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current);

    protected abstract void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);



    protected abstract void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current);

    protected abstract void enterBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current);

    protected abstract void enterDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current);

    protected abstract void enterMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current);

    protected abstract void enterMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current);

    protected abstract void enterPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current);

    protected abstract void enterGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void enterLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void enterGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void enterLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current);

    protected abstract void enterNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current);

    protected abstract void enterEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current);

    protected abstract void enterLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current);

    protected abstract void enterLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current);

    protected abstract void enterExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current);

    protected abstract void enterAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current);

    protected abstract void enterPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current);

    protected abstract void enterWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current);

    protected abstract void enterIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current);

    protected abstract void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current);

    protected abstract void enterVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current);

    protected abstract void enterStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current);

    protected abstract void enterType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current);

    protected abstract void enterFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current);

    protected abstract void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current);

    protected abstract void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current);

    protected abstract void enterClassDel(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current);

    protected abstract void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node);

    protected abstract void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> node);

}
