package edu.rosehulman.csse.mjc;

import java.lang.reflect.Type;
import java.util.Stack;

public class ContextSenstiveAnalysis extends MiniJavaBaseListener {

    Stack<Type> resultantTypes = new Stack<>();

    @Override
    public void enterExpr(MiniJavaParser.ExprContext ctx) {
    }

    @Override
    public void enterLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        super.enterLogicalOr(ctx);
    }

    @Override
    public void enterLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        super.enterLogicalAnd(ctx);
    }

    @Override
    public void enterEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        super.enterEqualsOrNotEquals(ctx);
    }

    @Override
    public void enterRelation(MiniJavaParser.RelationContext ctx) {
        super.enterRelation(ctx);
    }

    @Override
    public void enterPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {
        System.out.println("enterPlusOrMinus");
    }

    @Override
    public void enterMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
        System.out.println("enterMultOrDiv");
    }

    @Override
    public void enterUnary(MiniJavaParser.UnaryContext ctx) {
        super.enterUnary(ctx);
    }

    @Override
    public void enterAtom(MiniJavaParser.AtomContext ctx) {
        System.out.println("enterAtom");
    }

    @Override
    public void exitAtom(MiniJavaParser.AtomContext ctx) {
        if (ctx.BOOL() != null) {
            System.out.println("BOOL: " + ctx.getText());
            resultantTypes.push(Boolean.class);
        } else if (ctx.INTEGER() != null) {
            System.out.println("INT: " + ctx.getText());
            resultantTypes.push(Integer.class);
        } else {
            System.err.println("INVALID TYPE?!?!");
        }
        System.out.println("exitAtom");
    }

    @Override
    public void exitUnary(MiniJavaParser.UnaryContext ctx) {
//        Type type = resultantTypes.peek();
//        if (ctx.BANG() != null) {
//            assert type.equals(Boolean.class);
//        } else if (ctx.MINUS() != null) {
//            assert type.equals(Integer.class);
//        }
    }

    @Override
    public void exitMultOrDiv(MiniJavaParser.MultOrDivContext ctx) {
//        Type type1 = resultantTypes.pop();
//        Type type2 = resultantTypes.pop();
//        assert type1.equals(type2) && type1.equals(Integer.class);
//        resultantTypes.push(Integer.class);
        System.out.println("exitMultOrDiv");
    }

    @Override
    public void exitPlusOrMinus(MiniJavaParser.PlusOrMinusContext ctx) {

    }

    @Override
    public void exitRelation(MiniJavaParser.RelationContext ctx) {
        super.exitRelation(ctx);
    }

    @Override
    public void exitEqualsOrNotEquals(MiniJavaParser.EqualsOrNotEqualsContext ctx) {
        super.exitEqualsOrNotEquals(ctx);
    }

    @Override
    public void exitLogicalAnd(MiniJavaParser.LogicalAndContext ctx) {
        super.exitLogicalAnd(ctx);
    }

    @Override
    public void exitLogicalOr(MiniJavaParser.LogicalOrContext ctx) {
        super.exitLogicalOr(ctx);
    }

    @Override
    public void exitExpr(MiniJavaParser.ExprContext ctx) {
        super.exitExpr(ctx);
    }
}
