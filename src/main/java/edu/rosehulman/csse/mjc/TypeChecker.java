package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.BaseWalker;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.ClassSymbolTable;
import edu.rosehulman.csse.mjc.reflect.Method;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;


public class TypeChecker extends BaseWalker {

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
        if (!Objects.equals("boolean", typeStack.pop())) {
            throw new RuntimeException("Invalid type for while statement, expected bool");
        }
    }

    @Override
    protected void exitPuts(AbstractSyntaxNode<MiniJavaParser.PutsContext> current) {
        String printType = typeStack.pop();
        if (!printType.equals("char")) {
            throw new RuntimeException("You can only print chars!");
        }
    }

    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
        String printType = typeStack.pop();
        if (!printType.equals("int")) {
            throw new RuntimeException("You can only println integers!");
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

    @Override
    protected void exitArrayIndexAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
        String varName = current.getContext().ID().getText();
        String type = getArrayType(symbolTable.lookUpVar(varName));
        String assignmentType = typeStack.pop();
        String assignmentIndex = typeStack.pop();
        if (!assignmentIndex.equals("int")) {
            throw new RuntimeException("You can only access arrays by integers");
        }
        if (!type.equals(assignmentType)) {
            throw new RuntimeException("Invalid assignment of array " + varName);
        }
    }

    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {
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
        String type1 = typeStack.pop();
        String type2 = typeStack.pop();
        if (!isPrimative(type1) && !isPrimative(type2)) {
            typeStack.push("boolean");
        } else if (type1.equals(type2)) {
            typeStack.push("boolean");
        } else {
            throw new RuntimeException("Cannot compare different types");
        }
    }

    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        String type1 = typeStack.pop();
        String type2 = typeStack.pop();
        if (!isPrimative(type1) && !isPrimative(type2)) {
            typeStack.push("boolean");
        } else if (type1.equals(type2)) {
            typeStack.push("boolean");
        } else {
            throw new RuntimeException("Cannot compare different types");
        }
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
            if (!isAssignable(s1, s)) {
                System.err.println(current.getContext().getText());
                throw new RuntimeException(methodName + " has invalid type for parameter number " + i);
            }
        }
        typeStack.push(method.getReturnType());
    }

    protected void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        typeStack.push("int");
    }

    @Override
    protected void exitCharacter(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        typeStack.push("char");
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

    @Override
    protected void exitArrayAccess(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String indexAssignment = typeStack.pop();
        String varName = current.getContext().ID().getText();
        String arrayType = symbolTable.lookUpVar(varName);
        if (!indexAssignment.equals("int")) {
           throw new RuntimeException("Can only access arrays by integers!");
        }
        typeStack.push(getArrayType(arrayType));
    }

    @Override
    protected void exitArrayConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String constructedType = current.getContext().single_type().getText();
        String indexAssignment = typeStack.pop();
        boolean validClass = isPrimative(constructedType) || classes.stream().anyMatch(c -> c.getName().equals(constructedType));
        if (!indexAssignment.equals("int")) {
            throw new RuntimeException("Can only access arrays by integers!");
        }
        if (!validClass) {
            throw new RuntimeException("Class " + constructedType + " does not exist!");
        }
        typeStack.push(constructedType + "[]");
    }

    protected void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String constructedType = current.getContext().ID().getText();
        boolean validClass = classes.stream().anyMatch(c -> c.getName().equals(constructedType));
        if (!validClass) {
            throw new RuntimeException("Class " + constructedType + " does not exist!");
        }
        typeStack.push(constructedType);
    }


    protected void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
        symbolTable = new SymbolTable(symbolTable);
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
        return name.equals("int") || name.equals("boolean") || name.equals("char");
    }

    private boolean isAssignable(String containerType, String instanceType) {
        if (isPrimative(containerType)) {
            return containerType.equals(instanceType);
        }
        if (instanceType.equals("null")) {
            return true;
        }
        if (isArray(containerType) && isArray(instanceType)) {
           return isAssignable(getArrayType(containerType), getArrayType(instanceType));
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
                name.equals("char") ||
                name.equals("null") ||
                classes.stream().anyMatch(aClass -> aClass.getName().equals(name)) ||
                isArray(name);
    }

    private boolean isArray(String name) {
        return (name.endsWith("[]")) && isValidType(getArrayType(name));
    }

    private String getArrayType(String name) {
        if (name.endsWith("[]")) {
            return name.substring(0, name.length() - 2);
        } else {
            return name;
        }
    }

}
