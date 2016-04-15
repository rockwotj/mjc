package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.Walker;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.ClassSymbolTable;
import edu.rosehulman.csse.mjc.reflect.Method;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;


public class TypeChecker extends Walker {

    private final List<Class> classes;
    private Stack<String> typeStack = new Stack<>();
    private SymbolTable symbolTable = new SymbolTable();
    private Class thisClass;

    public TypeChecker(AbstractSyntaxNode root, List<Class> classes) {
        super(root);
        this.classes = classes;
    }

    protected void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {
    }

    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
        symbolTable = symbolTable.getParent();
    }

    protected void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
        thisClass = null;
        symbolTable = symbolTable.getParent();
    }

    protected void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
    }

    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
        // Assert valid return type, top of stack
        String returnType = current.getContext().type().getText();
        String returnedType = typeStack.pop();
        if (!isAssignable(returnType, returnedType)) {
            throw new RuntimeException("Invalid return type " + returnedType + " is not assignable from " + returnType);
        }
        if (!typeStack.isEmpty()) {
            throw new RuntimeException(typeStack.toString());
        }
        symbolTable = symbolTable.getParent();
        typeStack.clear();
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
        if (!isAssignable(varType, resultType)) {
            throw new RuntimeException("variable " + varName + " expecting type: " + varType + " got type: " + resultType);
        } else {
            symbolTable.addVar(varName, varType);
        }
    }

    protected void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
        symbolTable = symbolTable.getParent();
    }

    protected void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {
        if (!Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for while statement, expected bool");
        }
    }

    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
        System.out.println(current.getContext().getText());
        if (!Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for while statement, expected bool");
        }
    }

    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
        String printType = typeStack.pop();
        if (!printType.equals("int")) {
            throw new RuntimeException("You can only print integers!");
        }
    }

    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
        String var = current.getContext().ID().getText();
        String type = typeStack.pop();
        String varType = symbolTable.lookUpVar(var);
        if (!isAssignable(varType, type)) {
            throw new RuntimeException("Cannot assign type of " + type + " to type " + varType);
        }
    }

    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {
        System.out.println(current.getContext().getText());
    }

    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {
        if (!Objects.equals("boolean", typeStack.pop()) || !Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for && operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {
        if (!Objects.equals("boolean", typeStack.pop()) || !Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for || operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        typeStack.pop();
        typeStack.pop();
        typeStack.push("boolean");
    }

    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        typeStack.pop();
        typeStack.pop();
        typeStack.push("boolean");
    }

    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for < operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for > operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for <= operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for >= operator, expected int");
        }
        typeStack.push("boolean");
    }

    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for + operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for - operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || !Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for * operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        if (!Objects.equals("int", typeStack.pop()) || (!Objects.equals("int", typeStack.pop()))) {
            throw new RuntimeException("Invalid type for / operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (!Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for ! operator, expected bool");
        }
        typeStack.push("boolean");
    }

    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        if (!Objects.equals("int", typeStack.pop())) {
            throw new RuntimeException("Invalid type for - operator, expected int");
        }
        typeStack.push("int");
    }

    protected void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {
        System.out.println(current.getContext().getText());
        int numParams = current.getContext().expr().size();
        List<String> paramList = new ArrayList<>(numParams);
        for (int i = 0; i < numParams; i++) {
            paramList.add(0, typeStack.pop());
        }
        String classType = typeStack.pop();
        String methodName = current.getContext().ID().getText();
        Method method = null;
        for (Class clazz : classes) {
            if (clazz.getName().equals(classType)) {
                for (Method m : clazz.getMethods()) {
                    if (m.getName().equals(methodName)) {
                        method = m;
                        break;
                    }
                }
                if (method == null) {
                    throw new RuntimeException("Class " + clazz.getName() + " does not have method " + methodName);
                }
                break;
            }
        }
        if (method.getParamTypes().size() != numParams) {
            throw new RuntimeException(methodName + " has an incorrect number of parameters");
        }
        for (int i = 0; i < numParams; i++) {
            String s = paramList.get(i);
            String s1 = method.getParamTypes().get(i);
            if (!s.equals(s1)) {
                throw new RuntimeException(methodName + " has invalid type for parameter number " + i);
            }
        }
        typeStack.push(method.getReturnType());
        System.out.println(typeStack);
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
        typeStack.push(thisClass.getName());
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
        symbolTable = new SymbolTable(symbolTable);
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
        symbolTable = new SymbolTable(symbolTable);
        String returnType = current.getContext().type().getText();
        if (!isValidType(returnType)) {
            throw new RuntimeException("Return type " + returnType + " does not exist!");
        }
        for (MiniJavaParser.FormalContext param : current.getContext().formal()) {
            String type = param.type().getText();
            if (!isValidType(type)) {
                throw new RuntimeException("Type " + type + " does not exist!");
            }
            symbolTable.addVar(param.ID().getText(), type);
        }
    }

    protected void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
        // This just needs to check for valid types as they are already added to the table.
        String varName = current.getContext().ID().getText();
        String varType = current.getContext().type().getText();
        String resultType = varType;
        // TODO: This is a hack that essentially just checks that the type exists because nothing is being assigned
        if (!isAssignable(varType, resultType)) {
            throw new RuntimeException("Type " + varType + " not defined");
        } else {
            // This is done in enterClassDecl when we add a new ClassSymbolTable...
//            symbolTable.addVar(varName, varType);
        }
    }

    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
        String className = current.getContext().ID(0).getText();
        for (Class c : classes) {
            if (c.getName().equals(className)) {
                thisClass = c;
                symbolTable = new ClassSymbolTable(symbolTable, c);
                break;
            }
        }
    }

    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node) {
        symbolTable = new SymbolTable(symbolTable);
    }

    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> node) {
    }

    private boolean isPrimative(String name) {
        return name.equals("int") || name.equals("boolean");
    }

    private boolean isAssignable(String containerType, String instanceType) {
        if (isPrimative(containerType)) {
            return containerType.equals(instanceType);
        }
        if (instanceType.equals("null")) {
            return true;
        }
        for (Class c : classes) {
            if (c.getName().equals(instanceType)) {
                while (c != null) {
                    if (c.getName().equals(containerType)) {
                        return true;
                    }
                    c = c.getParent();
                }
                break;
            }
        }
        return false;
    }

    private boolean isValidType(String name) {
        return name.equals("int") ||
                name.equals("boolean") ||
                name.equals("null") ||
                classes.stream().anyMatch(aClass -> aClass.getName().equals(name));
    }

}
