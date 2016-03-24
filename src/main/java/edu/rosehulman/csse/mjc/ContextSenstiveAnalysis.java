package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class ContextSenstiveAnalysis extends MiniJavaBaseListener {

    private Stack<AbstractSyntaxNode> parents = new Stack<>();
    private AbstractSyntaxNode<MiniJavaParser.ProgramContext> parseTree;
    private AbstractSyntaxNode currentNode;

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        parseTree = new AbstractSyntaxNode<>(ctx);
        currentNode = parseTree;
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {
    }

    @Override
    public void enterMainClassDecl(MiniJavaParser.MainClassDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }


    @Override
    public void exitMainClassDecl(MiniJavaParser.MainClassDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterClassDecl(MiniJavaParser.ClassDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitClassDecl(MiniJavaParser.ClassDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterClassVarDecl(MiniJavaParser.ClassVarDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitClassVarDecl(MiniJavaParser.ClassVarDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterMethodDecl(MiniJavaParser.MethodDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitMethodDecl(MiniJavaParser.MethodDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterFormal(MiniJavaParser.FormalContext ctx) {
    }

    @Override
    public void exitFormal(MiniJavaParser.FormalContext ctx) {

    }

    @Override
    public void enterType(MiniJavaParser.TypeContext ctx) {
    }

    @Override
    public void exitType(MiniJavaParser.TypeContext ctx) {

    }

    @Override
    public void enterVarDecl(MiniJavaParser.VarDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitVarDecl(MiniJavaParser.VarDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterBlock(MiniJavaParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(MiniJavaParser.BlockContext ctx) {

    }

    @Override
    public void enterIfElse(MiniJavaParser.IfElseContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitIfElse(MiniJavaParser.IfElseContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterWhileDecl(MiniJavaParser.WhileDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitWhileDecl(MiniJavaParser.WhileDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterPrint(MiniJavaParser.PrintContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitPrint(MiniJavaParser.PrintContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterAssigment(MiniJavaParser.AssigmentContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitAssigment(MiniJavaParser.AssigmentContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterExpr(MiniJavaParser.ExprContext ctx) {

    }

    @Override
    public void enterLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        if (ctx.OR() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }

    }

    @Override
    public void enterLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        if (ctx.AND() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void enterEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        if (ctx.EEQ() != null || ctx.NEQ() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void enterRelation(MiniJavaParser.RelationContext ctx) {
        if (ctx.LEQ() != null || ctx.GEQ() != null || ctx.LT() != null || ctx.GT() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void enterPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        if (ctx.ADD() != null || ctx.SUB() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void enterMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        if (ctx.MUL() != null || ctx.DIV() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void enterUnary(MiniJavaParser.UnaryContext ctx) {
        if (ctx.BANG() != null || ctx.NEGATIVE() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }

    }

    @Override
    public void enterAtom(MiniJavaParser.AtomContext ctx) {
        if (ctx.expr() == null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitAtom(MiniJavaParser.AtomContext ctx) {
        if (ctx.expr() == null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitUnary(MiniJavaParser.UnaryContext ctx) {
        if (ctx.BANG() != null || ctx.NEGATIVE() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        if (ctx.MUL() != null || ctx.DIV() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        if (ctx.ADD() != null || ctx.SUB() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitRelation(MiniJavaParser.RelationContext ctx) {
        if (ctx.LEQ() != null || ctx.GEQ() != null || ctx.LT() != null || ctx.GT() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        if (ctx.EEQ() != null || ctx.NEQ() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        if (ctx.AND() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        if (ctx.OR() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void exitExpr(MiniJavaParser.ExprContext ctx) {

    }

    @Override
    public void enterExprPrime(MiniJavaParser.ExprPrimeContext ctx) {
        if (ctx.ID() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitExprPrime(MiniJavaParser.ExprPrimeContext ctx) {
        if (ctx.ID() != null) {
            currentNode = parents.pop();
        }
    }


    public AbstractSyntaxNode<MiniJavaParser.ProgramContext> getParseTree() {
        return parseTree;
    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }
}
