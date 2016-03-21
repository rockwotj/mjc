package edu.rosehulman.csse.mjc;

import java.lang.reflect.Type;
import java.util.Stack;

public class ContextSenstiveAnalysis extends MiniJavaBaseListener {

    Stack<Type> resultantTypes = new Stack<>();

    @Override
    public void enterVarDecl(MiniJavaParser.VarDeclContext ctx) {
        resultantTypes.clear();
    }

    @Override
    public void enterAssigment(MiniJavaParser.AssigmentContext ctx) {
        resultantTypes.clear();
    }

    @Override
    public void enterExpr(MiniJavaParser.ExprContext ctx) {
        System.out.println("Enter Expr ");
        System.out.println(resultantTypes.toString());
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
        System.out.println(resultantTypes.toString());
    }

    @Override
    public void exitUnary(MiniJavaParser.UnaryContext ctx) {
        System.out.print("Exit Unary ");
        if (ctx.BANG() != null) {
            System.out.println("BANG: " + ctx.getText());
            if (Boolean.class != resultantTypes.peek()) {
                System.err.println("Invalid type for ! operator, expected bool got int");
            }
        } else if (ctx.NEGATIVE() != null) {
            System.out.println("NEGATIVE: " + ctx.getText());
            if (Integer.class != resultantTypes.peek()) {
                System.err.println("Invalid type for - operator, expected int got bool");
            }
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        System.out.print("Exit MultOrDiv ");
        if (ctx.MUL() != null) {
            System.out.println("MUL: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
            System.out.println(resultantTypes.toString());
        } else if (ctx.DIV() != null) {
            System.out.println("DIV: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        System.out.print("Exit PlusOrMinus ");
        if (ctx.ADD() != null) {
            System.out.println("ADD: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else if (ctx.SUB() != null) {
            System.out.println("SUB: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitRelation(MiniJavaParser.RelationContext ctx) {
        System.out.print("Exit Relation ");
        if (ctx.LEQ() != null) {
            System.out.println("LEQ: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else if (ctx.GEQ() != null) {
            System.out.println("GEQ: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else if (ctx.LT() != null) {
            System.out.println("LT: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else if (ctx.GT() != null) {
            System.out.println("GT: " + ctx.getText());
            assert Integer.class == resultantTypes.pop();
            assert Integer.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        System.out.print("Exit EqualsOrNotEquals ");
        if (ctx.EEQ() != null) {
            System.out.println("EEQ: " + ctx.getText());
            assert Boolean.class == resultantTypes.pop();
            assert Boolean.class == resultantTypes.peek();
        } else if (ctx.NEQ() != null) {
            System.out.println("NEQ: " + ctx.getText());
            assert Boolean.class == resultantTypes.pop();
            assert Boolean.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        System.out.print("Exit LogicalAnd ");
        if (ctx.AND() != null) {
            System.out.println("AND: " + ctx.getText());
            assert Boolean.class == resultantTypes.pop();
            assert Boolean.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        System.out.print("Exit LogicalOr ");
        if (ctx.OR() != null) {
            System.out.println("OR: " + ctx.getText());
            assert Boolean.class == resultantTypes.pop();
            assert Boolean.class == resultantTypes.peek();
        } else {
            System.out.println("FALL THROUGH");
        }
    }

    @Override
    public void exitExpr(MiniJavaParser.ExprContext ctx) {
        System.out.println("Exit Expr ");
    }
}
