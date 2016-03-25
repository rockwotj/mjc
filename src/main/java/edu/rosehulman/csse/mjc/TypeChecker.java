package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.Walker;
import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import java.lang.reflect.Type;
import java.util.Stack;

public class TypeChecker extends Walker {

    private Stack<Type> resultantTypes = new Stack<>();

    public TypeChecker(AbstractSyntaxNode root) {
        super(root);
    }

    protected void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {
    }

    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
    }

    protected void exitClassDel(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
    }

    protected void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
    }

    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
    }

    protected void exitFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {
    }

    protected void exitType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {
    }

    protected void exitStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {
    }

    protected void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
    }

    protected void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
    }

    protected void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {
    }

    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
    }

    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
    }

    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
    }

    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {
    }

    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {
        if (Boolean.class != resultantTypes.pop() || Boolean.class != resultantTypes.pop()) {
            System.err.println("Invalid type for && operator, expected bool");
        }
        resultantTypes.push(Boolean.class);
    }

    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {
        if (Boolean.class != resultantTypes.pop() || Boolean.class != resultantTypes.pop()) {
            System.err.println("Invalid type for || operator, expected bool");
        }
        resultantTypes.push(Boolean.class);
    }

    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        if (resultantTypes.pop() != resultantTypes.pop()) {
            System.err.println("Mismatched types for == operator");
        }
        resultantTypes.push(Boolean.class);
    }

    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        if (resultantTypes.pop() != resultantTypes.pop()) {
            System.err.println("Mismatched types for != operator");
        }
        resultantTypes.push(Boolean.class);
    }

    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for < operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for > operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for <= operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for >= operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for + operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for - operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for * operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (Integer.class != resultantTypes.pop() || Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for / operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (Boolean.class != resultantTypes.pop()) {
            System.err.println("Invalid type for ! operator, expected bool");
        }
        resultantTypes.push(Boolean.class);
    }

    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (Integer.class != resultantTypes.pop()) {
            System.err.println("Invalid type for - operator, expected int");
        }
        resultantTypes.push(Integer.class);
    }

    protected void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        resultantTypes.push(Integer.class);
    }

    protected void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        resultantTypes.push(Boolean.class);
    }

    protected void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }



    protected void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    }

    protected void enterNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
    }

    protected void enterBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
    }

    protected void enterDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
    }

    protected void enterMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
    }

    protected void enterMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
    }

    protected void enterPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
    }

    protected void enterGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
    }

    protected void enterLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
    }

    protected void enterGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
    }

    protected void enterLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
    }

    protected void enterNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
    }

    protected void enterEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
    }

    protected void enterLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {
    }

    protected void enterLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {
    }

    protected void enterExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {
    }

    protected void enterAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
    }

    protected void enterPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
    }

    protected void enterWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
    }

    protected void enterIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {
    }

    protected void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
    }

    protected void enterVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
    }

    protected void enterStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {
    }

    protected void enterType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {
    }

    protected void enterFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {
    }

    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
    }

    protected void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
    }

    protected void enterClassDel(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
    }

    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node) {
    }

    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> node) {
    }

}
