package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.Walker;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;

import java.util.List;
import java.util.Objects;
import java.util.Stack;


public class TypeChecker extends Walker {

    private final List<Class> classes;
    private Stack<String> typeStack = new Stack<>();
    private SymbolTable symbolTable = new SymbolTable();

    public TypeChecker(AbstractSyntaxNode root, List<Class> classes) {
        super(root);
        this.classes = classes;
    }

    protected void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {
    }

    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
    }

    protected void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
    }

    protected void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
    }

    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
        symbolTable = symbolTable.getParent();
    }

    protected void exitFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {
    }

    protected void exitType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {
    }

    protected void exitStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {
    }

    protected void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
        String varName = current.getContext().ID().getText();
        String varType = current.getContext().type().getText();
        String resultType = typeStack.pop();
        if (!Objects.equals(varType, resultType)) {
            throw new RuntimeException("variable " + varName + " expecting type: " + varType + " got type: " + resultType);
        } else {
            symbolTable.addVar(varName, varType);
        }
        if (!typeStack.isEmpty()) {
            System.err.println(typeStack.toString());
        }
        typeStack.clear();
    }

    protected void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
    }

    protected void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {
        if (!Objects.equals("boolean", typeStack.pop())) {
            System.err.println("Invalid type for while statement, expected bool");
        }
    }

    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
        if (!Objects.equals("boolean", typeStack.pop())) {
            System.err.println("Invalid type for while statement, expected bool");
        }
    }

    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
    }

    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
    }

    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {
        System.out.println(current.getContext().getText());
    }

    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {
        if (!Objects.equals("boolean", typeStack.pop()) || !Objects.equals("boolean", typeStack.pop())) {
            System.err.println("Invalid type for && operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {
        if (!Objects.equals("boolean", typeStack.pop()) || !Objects.equals("boolean", typeStack.pop())) {
            System.err.println("Invalid type for || operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        if (!Objects.equals(typeStack.pop(), typeStack.pop())) {
            System.err.println("Mismatched types for == operator");
        }
        typeStack.push("boolean");
    }

    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        if (!Objects.equals(typeStack.pop(), typeStack.pop())) {
            System.err.println("Mismatched types for != operator");
        }
        typeStack.push("boolean");
    }

    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for < operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for > operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for <= operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for >= operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for + operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for - operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for * operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || (!Objects.equals("int", typeStack.pop()))) {
            System.err.println("Invalid type for / operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (!Objects.equals("boolean", typeStack.pop())) {
            System.err.println("Invalid type for ! operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (!Objects.equals("int", typeStack.pop())) {
            System.err.println("Invalid type for - operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {
    	/*  TODO
           -Pop off the number of elements equal to the number of elements we were passed
   		   -The top of the stack now contains the type of the object calling the function
   		   -The ID is the method and can be looked up int the class hierarchy
           -Confirm the method signiture with the types we poped off
           -Push the return type of the method on the stack */
   		   System.out.println(current.getContext().getText());
    	// symbolTable = new SymbolTable(symbolTable);
    	// List<MiniJavaParser.FormalContext> params = current.getContext().formal();
    	// for (MiniJavaParser.FormalContext p : params) {
    	//     String varName = p.ID().getText();
    	//     String typeName = p.type().getText();
    	//     symbolTable.addVar(varName, typeName);
    	// }
    }

    protected void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        typeStack.push("int");
    }

    protected void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        typeStack.push("boolean");
    }

    protected void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
    	typeStack.push("null");
    }

    protected void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    protected void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String varName = current.getContext().getText();
        typeStack.push(symbolTable.lookUpVar(varName));
    }

    protected void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String constructedType = current.getContext().ID().getText();
        boolean validClass = classes.stream().anyMatch(c -> c.getName().equals(constructedType));
        if (!validClass) {
            throw new RuntimeException("Class " + constructedType + " does not exist!");
        }
        typeStack.push(constructedType);
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

    protected void enterMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {
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

    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
    }

    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node) {
    }

    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> node) {
    }

}
