package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode.NodeType;

import java.util.Stack;

public class ASTBuilder extends MiniJavaBaseListener {

    private Stack<AbstractSyntaxNode> parents = new Stack<>();
    private AbstractSyntaxNode<MiniJavaParser.ProgramContext> ast;
    private AbstractSyntaxNode currentNode;

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        ast = new AbstractSyntaxNode<>(ctx, NodeType.program);
        currentNode = ast;
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {
    }

    @Override
    public void enterMainClassDecl(MiniJavaParser.MainClassDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.mainClassDecl);
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
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.classDecl);
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
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.classVarDecl);
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
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.methodDecl);
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
    public void enterStmt(MiniJavaParser.StmtContext ctx) {
    }

    @Override
    public void exitStmt(MiniJavaParser.StmtContext ctx) {
    }

    @Override
    public void enterVarDecl(MiniJavaParser.VarDeclContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.varDecl);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitVarDecl(MiniJavaParser.VarDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterBlock(MiniJavaParser.BlockContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.block);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitBlock(MiniJavaParser.BlockContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterIfElse(MiniJavaParser.IfElseContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.ifElse);
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
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.whileDecl);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitWhileDecl(MiniJavaParser.WhileDeclContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterPuts(MiniJavaParser.PutsContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.puts);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void enterPrint(MiniJavaParser.PrintContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.print);
        currentNode.addChild(node);
        currentNode = node;
    }

    @Override
    public void exitPuts(MiniJavaParser.PutsContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void exitPrint(MiniJavaParser.PrintContext ctx) {
        currentNode = parents.pop();
    }

    @Override
    public void enterAssigment(MiniJavaParser.AssigmentContext ctx) {
        parents.push(currentNode);
        AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.assigment);
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
    public void exitExpr(MiniJavaParser.ExprContext ctx) {
    }

    @Override
    public void enterLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        if (ctx.OR() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.logicalOr);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        if (ctx.OR() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        if (ctx.AND() != null) {
            parents.push(currentNode);
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.logicalAnd);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        if (ctx.AND() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.EEQ() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.equals);
        } else if (ctx.NEQ() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.notEquals);
        }
        if (node != null) {
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        if (ctx.EEQ() != null || ctx.NEQ() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterRelation(MiniJavaParser.RelationContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.LEQ() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.lessThanEquals);
        } else if (ctx.GEQ() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.greaterThanEquals);
        } else if (ctx.LT() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.lessThan);
        } else if (ctx.GT() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.greaterThan);
        }
        if (node != null) {
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitRelation(MiniJavaParser.RelationContext ctx) {
        if (ctx.LEQ() != null || ctx.GEQ() != null || ctx.LT() != null || ctx.GT() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.ADD() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.plus);
        } else if (ctx.SUB() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.minus);
        }
        if (node != null) {
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        if (ctx.ADD() != null || ctx.SUB() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.MUL() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.mult);
        } else if (ctx.DIV() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.div);
        }
        if (node != null) {
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        if (ctx.MUL() != null || ctx.DIV() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterUnary(MiniJavaParser.UnaryContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.BANG() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.bang);
        } else if (ctx.SUB() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.negative);
        }
        if (node != null) {
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitUnary(MiniJavaParser.UnaryContext ctx) {
        if (ctx.BANG() != null || ctx.SUB() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterMethodCall(MiniJavaParser.MethodCallContext ctx) {
        if (ctx.DOT() != null) {
            AbstractSyntaxNode node = new AbstractSyntaxNode<>(ctx, NodeType.methodCall);
            parents.push(currentNode);
            currentNode.addChild(node);
            currentNode = node;
        }
    }

    @Override
    public void exitMethodCall(MiniJavaParser.MethodCallContext ctx) {
        if (ctx.DOT() != null) {
            currentNode = parents.pop();
        }
    }

    @Override
    public void enterAtom(MiniJavaParser.AtomContext ctx) {
        AbstractSyntaxNode node = null;
        if (ctx.INT() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.integer);
        } else if (ctx.BOOL() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.bool);
        } else if (ctx.NULL() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.nil);
        } else if (ctx.THIS() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.self);
        } else if (ctx.NEW() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.constructor);
        } else if (ctx.ID() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.id);
        } else if (ctx.CHAR() != null) {
            node = new AbstractSyntaxNode<>(ctx, NodeType.character);
        }
        if (node != null) {
            parents.push(currentNode);
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

    // @Override
    // public void enterEveryRule(ParserRuleContext ctx) { }

    // @Override
    // public void exitEveryRule(ParserRuleContext ctx) { }

    // @Override
    // public void visitTerminal(TerminalNode node) { }

    // @Override
    // public void visitErrorNode(ErrorNode node) { }

    public AbstractSyntaxNode<MiniJavaParser.ProgramContext> getAbstractSyntaxTree() {
        return ast;
    }

}
