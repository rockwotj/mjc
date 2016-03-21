package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Stack;

public class ContextSenstiveAnalysis extends MiniJavaBaseListener {

    private Stack<Type> resultantTypes = new Stack<>();
    private ParseTreeNode<MiniJavaParser.ProgramContext> parseTree;
    private ParseTreeNode currentNode;

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        parseTree = new ParseTreeNode<>(ctx);
        currentNode = parseTree;
    }

    @Override
    public void enterVarDecl(MiniJavaParser.VarDeclContext ctx) {
        resultantTypes.clear();
        ParseTreeNode<MiniJavaParser.VarDeclContext> child = new ParseTreeNode<>(ctx);
        currentNode.addChild(child);
        currentNode = child;
    }

    @Override
    public void enterAssigment(MiniJavaParser.AssigmentContext ctx) {
        resultantTypes.clear();
        ParseTreeNode<MiniJavaParser.AssigmentContext> child = new ParseTreeNode<>(ctx);
        currentNode.addChild(child);
        currentNode = child;
    }

    @Override
    public void enterExpr(MiniJavaParser.ExprContext ctx) {
        System.out.println("Enter Expr ");
    }

    @Override
    public void enterLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        System.out.print("Enter LogicalOr ");
        if (ctx.OR() != null) {
            System.out.println("OR: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        System.out.print("Enter LogicalAnd ");
        if (ctx.AND() != null) {
            System.out.println("AND: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        System.out.print("Enter EqualsOrNotEquals ");
        if (ctx.EEQ() != null) {
            System.out.println("EEQ: " + ctx.getText());
        } else if (ctx.NEQ() != null) {
            System.out.println("NEQ: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterRelation(MiniJavaParser.RelationContext ctx) {
        System.out.print("Enter Relation ");
        if (ctx.LEQ() != null) {
            System.out.println("LEQ: " + ctx.getText());
        } else if (ctx.GEQ() != null) {
            System.out.println("GEQ: " + ctx.getText());
        } else if (ctx.LT() != null) {
            System.out.println("LT: " + ctx.getText());
        } else if (ctx.GT() != null) {
            System.out.println("GT: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        System.out.print("Enter PlusOrMinus ");
        if (ctx.ADD() != null) {
            System.out.println("ADD: " + ctx.getText());
        } else if (ctx.SUB() != null) {
            System.out.println("SUB: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        System.out.print("Enter MultOrDiv ");
        if (ctx.MUL() != null) {
            System.out.println("MUL: " + ctx.getText());
        } else if (ctx.DIV() != null) {
            System.out.println("DIV: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterUnary(MiniJavaParser.UnaryContext ctx) {
        System.out.print("Enter Unary ");
        if (ctx.BANG() != null) {
            System.out.println("BANG: " + ctx.getText());
        } else if (ctx.NEGATIVE() != null) {
            System.out.println("NEGATIVE: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void enterAtom(MiniJavaParser.AtomContext ctx) {
        System.out.print("Enter Atom ");
        if (ctx.BOOL() != null) {
            System.out.println("BOOL: " + ctx.getText());
        } else if (ctx.INT() != null) {
            System.out.println("INT: " + ctx.getText());
        } else if (ctx.ID() != null) {
            System.out.println("ID: " + ctx.getText());
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitAtom(MiniJavaParser.AtomContext ctx) {
        System.out.print("Exit Atom ");
        if (ctx.BOOL() != null) {
            System.out.println("BOOL: " + ctx.getText());
            resultantTypes.push(Boolean.class);
        } else if (ctx.INT() != null) {
            System.out.println("INT: " + ctx.getText());
            resultantTypes.push(Integer.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitUnary(MiniJavaParser.UnaryContext ctx) {
        System.out.print("Exit Unary ");
        if (ctx.BANG() != null) {
            System.out.println("BANG: " + ctx.getText());
            if (Boolean.class != resultantTypes.pop()) {
                System.err.println("Invalid type for ! operator, expected bool");
            }
            resultantTypes.push(Boolean.class);
        } else if (ctx.NEGATIVE() != null) {
            System.out.println("NEGATIVE: " + ctx.getText());
            if (Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for - operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        System.out.print("Exit MultOrDiv ");
        if (ctx.MUL() != null) {
            System.out.println("MUL: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for * operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else if (ctx.DIV() != null) {
            System.out.println("DIV: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for / operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        System.out.print("Exit PlusOrMinus ");
        if (ctx.ADD() != null) {
            System.out.println("ADD: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for + operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else if (ctx.SUB() != null) {
            System.out.println("SUB: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for / operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitRelation(MiniJavaParser.RelationContext ctx) {
        System.out.print("Exit Relation ");
        if (ctx.LEQ() != null) {
            System.out.println("LEQ: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for <= operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else if (ctx.GEQ() != null) {
            System.out.println("GEQ: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for >= operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else if (ctx.LT() != null) {
            System.out.println("LT: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for < operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else if (ctx.GT() != null) {
            System.out.println("GT: " + ctx.getText());
            if (Integer.class != resultantTypes.pop() ||
                Integer.class != resultantTypes.pop()) {
                System.err.println("Invalid type for > operator, expected int");
            }
            resultantTypes.push(Integer.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        System.out.print("Exit EqualsOrNotEquals ");
        // TODO: Can compare object pointers
        if (ctx.EEQ() != null) {
            System.out.println("EEQ: " + ctx.getText());
            if (resultantTypes.pop() != resultantTypes.peek()) {
                System.err.println("Mismatched types for == operator");
            }
        } else if (ctx.NEQ() != null) {
            System.out.println("NEQ: " + ctx.getText());
            if (resultantTypes.pop() != resultantTypes.peek()) {
                System.err.println("Mismatched types for != operator");
            }
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        System.out.print("Exit LogicalAnd ");
        if (ctx.AND() != null) {
            System.out.println("AND: " + ctx.getText());
            if (Boolean.class != resultantTypes.pop() ||
                Boolean.class != resultantTypes.pop()) {
                System.err.println("Invalid type for && operator, expected bool");
            }
            resultantTypes.push(Boolean.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        System.out.print("Exit LogicalOr ");
        if (ctx.OR() != null) {
            System.out.println("OR: " + ctx.getText());
            if (Boolean.class != resultantTypes.pop() ||
                Boolean.class != resultantTypes.pop()) {
                System.err.println("Invalid type for || operator, expected bool");
            }
            resultantTypes.push(Boolean.class);
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitExpr(MiniJavaParser.ExprContext ctx) {
        System.out.println("Exit Expr ");
    }


    public ParseTreeNode<MiniJavaParser.ProgramContext> getParseTree() {
        return parseTree;
    }

}
