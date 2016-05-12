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
                enterClassDecl(current);
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
            case methodCall:
                enterMethodCall(current);
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
            case character:
                enterCharacter(current);
                break;
            case puts:
                enterPuts(current);
                break;
            case arrayConstructor:
                enterArrayConstructor(current);
                break;
            case arrayAccess:
                enterArrayAccess(current);
                break;
            case arrayIndexAssigment:
                enterArrayIndexAssignment(current);
                break;
        }
        parents.push(current);
        List<AbstractSyntaxNode> children = current.getChildren();
        int count = 0;
        for (AbstractSyntaxNode child : children) {
            walk(child);
            if (count == children.size() - 1) {
                break;
            }
            switch (current.getType()) {
                case program:
                    betweenProgram(current, count);
                    break;
                case mainClassDecl:
                    betweenMainClassDecl(current, count);
                    break;
                case classDecl:
                    betweenClassDecl(current, count);
                    break;
                case classVarDecl:
                    betweenClassVarDecl(current, count);
                    break;
                case methodDecl:
                    betweenMethodDecl(current, count);
                    break;
                case formal:
                    betweenFormal(current, count);
                    break;
                case type:
                    betweenType(current, count);
                    break;
                case stmt:
                    betweenStmt(current, count);
                    break;
                case varDecl:
                    betweenVarDecl(current, count);
                    break;
                case block:
                    betweenBlock(current, count);
                    break;
                case ifElse:
                    betweenIfElse(current, count);
                    break;
                case whileDecl:
                    betweenWhile(current, count);
                    break;
                case print:
                    betweenPrint(current, count);
                    break;
                case assigment:
                    betweenAssignment(current, count);
                    break;
                case expr:
                    betweenExpr(current, count);
                    break;
                case logicalOr:
                    betweenLogicalOr(current, count);
                    break;
                case logicalAnd:
                    betweenLogicalAnd(current, count);
                    break;
                case equals:
                    betweenEquals(current, count);
                    break;
                case notEquals:
                    betweenNotEquals(current, count);
                    break;
                case lessThan:
                    betweenLessThan(current, count);
                    break;
                case greaterThan:
                    betweenGreaterThan(current, count);
                    break;
                case lessThanEquals:
                    betweenLessThanEquals(current, count);
                    break;
                case greaterThanEquals:
                    betweenGreaterThanEquals(current, count);
                    break;
                case plus:
                    betweenPlus(current, count);
                    break;
                case minus:
                    betweenMinus(current, count);
                    break;
                case mult:
                    betweenMult(current, count);
                    break;
                case div:
                    betweenDiv(current, count);
                    break;
                case bang:
                    betweenBang(current, count);
                    break;
                case negative:
                    betweenNeg(current, count);
                    break;
                case methodCall:
                    betweenMethodCall(current, count);
                    break;
                case integer:
                    betweenInt(current, count);
                    break;
                case bool:
                    betweenBool(current, count);
                    break;
                case nil:
                    betweenNull(current, count);
                    break;
                case self:
                    betweenThis(current, count);
                    break;
                case id:
                    betweenId(current, count);
                    break;
                case constructor:
                    betweenConstructor(current, count);
                    break;
                case character:
                    betweenCharacter(current, count);
                    break;
                case puts:
                    betweenPuts(current, count);
                    break;
                case arrayConstructor:
                    betweenArrayConstructor(current, count);
                    break;
                case arrayAccess:
                    betweenArrayAccess(current, count);
                    break;
                case arrayIndexAssigment:
                    betweenArrayIndexAssignment(current, count);
                    break;
            }
            count++;
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
                exitClassDecl(current);
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
            case methodCall:
                exitMethodCall(current);
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
            case character:
                exitCharacter(current);
                break;
            case puts:
                exitPuts(current);
                break;
            case arrayConstructor:
                exitArrayConstructor(current);
                break;
            case arrayAccess:
                exitArrayAccess(current);
                break;
            case arrayIndexAssigment:
                exitArrayIndexAssignment(current);
                break;
        }
    }

    protected abstract void exitPuts(AbstractSyntaxNode<MiniJavaParser.PutsContext> current);

    protected abstract void exitCharacter(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void betweenPuts(AbstractSyntaxNode<MiniJavaParser.PutsContext> current, int count);

    protected abstract void betweenCharacter(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void enterPuts(AbstractSyntaxNode<MiniJavaParser.PutsContext> current);

    protected abstract void enterCharacter(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current);

    protected abstract void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current);

    protected abstract void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current);

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

    protected abstract void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current);

    protected abstract void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitArrayConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitArrayAccess(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void exitArrayIndexAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current);


    protected abstract void betweenProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current, int count);

    protected abstract void betweenMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current, int count);

    protected abstract void betweenClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current, int count);

    protected abstract void betweenClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current, int count);

    protected abstract void betweenMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current, int count);

    protected abstract void betweenFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current, int count);

    protected abstract void betweenType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current, int count);

    protected abstract void betweenStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current, int count);

    protected abstract void betweenVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current, int count);

    protected abstract void betweenBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current, int count);

    protected abstract void betweenIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current, int count);

    protected abstract void betweenWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current, int count);

    protected abstract void betweenPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current, int count);

    protected abstract void betweenAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current, int count);

    protected abstract void betweenExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current, int count);

    protected abstract void betweenLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current, int count);

    protected abstract void betweenLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current, int count);

    protected abstract void betweenEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count);

    protected abstract void betweenNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count);

    protected abstract void betweenLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count);

    protected abstract void betweenGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count);

    protected abstract void betweenLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count);

    protected abstract void betweenGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count);

    protected abstract void betweenPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count);

    protected abstract void betweenMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count);

    protected abstract void betweenMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count);

    protected abstract void betweenDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count);

    protected abstract void betweenBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count);

    protected abstract void betweenNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count);

    protected abstract void betweenMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current, int count);

    protected abstract void betweenInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenArrayConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenArrayAccess(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count);

    protected abstract void betweenArrayIndexAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current, int count);


    protected abstract void enterArrayConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterArrayAccess(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterArrayIndexAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current);

    protected abstract void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current);

    protected abstract void enterMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current);

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

    protected abstract void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current);

    protected abstract void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current);

    protected abstract void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current);

}
