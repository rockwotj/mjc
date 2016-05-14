package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.BaseWalker;
import edu.rosehulman.csse.mjc.ir.*;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.ClassSymbolTable;
import edu.rosehulman.csse.mjc.reflect.Method;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class CodeGenerator extends BaseWalker {

    private final List<Class> classList;
    private LlvmIr ir;
    private Stack<ValueOrRegister> exprRegisters = new Stack<>();
    private SymbolTable symbolTable = new SymbolTable();
    private Class thisClass;

    private String nextRegister() {
        int i = this.registerCounter;
        this.registerCounter++;
        return "%" + i;
    }

    private String getNextLabel() {
        String label = "LABEL" + labelCount;
        labelCount++;
        return label;
    }

    private String getValOrRegType(ValueOrRegister valOrReg) {
        if (valOrReg.isRegister()) {
            return symbolTable.lookUpVar(valOrReg.toString());
        } else {
            return valOrReg.getType();
        }
    }

    private String lastLabel;
    private Stack<Labels> lastLabels = new Stack<>();
    private int registerCounter = 0;
    private int labelCount = 0;

    private Stack<MethodCall> methodArgs = new Stack<>();

    public CodeGenerator(AbstractSyntaxNode ast, List<Class> classList) {
        super(ast);
        this.classList = classList;
        ir = new LlvmIr(classList);
    }

    @Override
    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
        symbolTable = symbolTable.getParent();
        symbolTable.clearPreviouslyDeclaredVars();
        ir.returnStatment("0", "int");
        ir.endMethod();
    }

    @Override
    protected void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
        symbolTable = symbolTable.getParent();
        symbolTable.clearPreviouslyDeclaredVars();
    }

    @Override
    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        String methodName = current.getContext().ID().getText();
        Method method = thisClass.getMethods()
                .stream()
                .filter(m -> m.getName().equals(methodName))
                .findFirst()
                .get();
        String type = getValOrRegType(valueOrRegister);
        String returnReg = ir.cast(nextRegister(), valueOrRegister.toString(), method.getReturnType(), type);
        ir.returnStatment(returnReg, method.getReturnType());
        ir.endMethod();
        symbolTable = symbolTable.getParent();
        symbolTable.clearPreviouslyDeclaredVars();
    }

    @Override
    protected void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
        String id = symbolTable.declareVar(current.getContext().ID().getText());
        String type = current.getContext().type().getText();
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        String valOrRegType = getValOrRegType(valueOrRegister);
        String castReg = ir.cast(nextRegister(), valueOrRegister.toString(), type, valOrRegType);
        ir.allocateStack("%" + id, type);
        ir.store("%" + id, type, castReg);
        symbolTable.addVar(id, type);
    }

    @Override
    protected void exitBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    protected void exitIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {
        IfElseLabels labels = (IfElseLabels) lastLabels.pop();
        String endLabel = labels.getEnd();
        ir.jump(endLabel);
        ir.label(endLabel);
        lastLabel = endLabel;
    }

    @Override
    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
        WhileLabels labels = (WhileLabels) lastLabels.pop();
        String testLabel = labels.getTest();
        String exitLabel = labels.getExit();
        ir.jump(testLabel);
        ir.label(exitLabel);
        lastLabel = exitLabel;
    }

    @Override
    protected void exitPuts(AbstractSyntaxNode<MiniJavaParser.PutsContext> current) {
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        ir.puts(nextRegister(), nextRegister(), valueOrRegister.toString());
    }

    @Override
    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        ir.print(nextRegister(), valueOrRegister.toString());
    }

    @Override
    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
        String var = symbolTable.getName(current.getContext().ID().getText());
        String type = symbolTable.lookUpVar(var);
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        String valOrRegType = getValOrRegType(valueOrRegister);
        String castReg = ir.cast(nextRegister(), valueOrRegister.toString(), type, valOrRegType);
        if (symbolTable.isClassVar(var)) {
            int index = thisClass.getFieldIndex(var);
            String tmpReg = ir.load(nextRegister(), thisClass.getName(), "%this");
            ir.setClassElement(nextRegister(), castReg, type, thisClass.getName(), tmpReg, index);
        } else {
            ir.store("%" + var, type, castReg);
        }
    }

    @Override
    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {
        LogicalExpressionLabels labels = (LogicalExpressionLabels) lastLabels.pop();
        String endLabel = labels.getEnd();
        String leftLabel = labels.getLeft();
        ir.jump(endLabel);
        ir.label(endLabel);
        String dstReg = ir.phi(nextRegister(), "true", exprRegisters.pop().toString(), leftLabel, lastLabel);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
        lastLabel = endLabel;
    }

    @Override
    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {
        LogicalExpressionLabels labels = (LogicalExpressionLabels) lastLabels.pop();
        String endLabel = labels.getEnd();
        String leftLabel = labels.getLeft();
        ir.jump(endLabel);
        ir.label(endLabel);
        String dstReg = ir.phi(nextRegister(), "false", exprRegisters.pop().toString(), leftLabel, lastLabel);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
        lastLabel = endLabel;
    }

    @Override
    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String valOrReg1 = valueOrRegister1.toString();
        String valOrReg2 = valueOrRegister2.toString();
        String type1 = getValOrRegType(valueOrRegister1);
        String type2 = getValOrRegType(valueOrRegister2);
        if (!isPrimative(type1)) {
            valOrReg1 = ir.cast(nextRegister(), valOrReg1, "null", type1);
            valOrReg2 = ir.cast(nextRegister(), valOrReg2, "null", type2);
            type1 = "null";
        }
        String dstReg = ir.equals(nextRegister(), type1, valOrReg1, valOrReg2);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String valOrReg1 = valueOrRegister1.toString();
        String valOrReg2 = valueOrRegister2.toString();
        String type1 = getValOrRegType(valueOrRegister1);
        String type2 = getValOrRegType(valueOrRegister2);
        if (!isPrimative(type1)) {
            valOrReg1 = ir.cast(nextRegister(), valOrReg1, "null", type1);
            valOrReg2 = ir.cast(nextRegister(), valOrReg2, "null", type2);
            type1 = "null";
        }
        String dstReg = ir.notEquals(nextRegister(), type1, valOrReg1, valOrReg2);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.lessThan(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.greaterThan(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.lessThanEqualTo(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.greaterThanEqualTo(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.add(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "int");
    }

    @Override
    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.minus(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "int");
    }

    @Override
    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.mult(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "int");
    }

    @Override
    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.div(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "int");
    }

    @Override
    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.bang(nextRegister(), "boolean", valueOrRegister1.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "boolean");
    }

    @Override
    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.neg(nextRegister(), "int", valueOrRegister1.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, "int");
    }

    @Override
    protected void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {
        betweenMethodCall(current, current.getChildren().size() - 1);
        MethodCall methodCall = methodArgs.pop();
        String dstReg = ir.methodCall(nextRegister(), nextRegister(), nextRegister(), nextRegister(), nextRegister(), methodCall);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, methodCall.getMethod().getReturnType());
    }


    @Override
    protected void betweenIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current, int count) {
        if (count == 0) { /* After the test */
            String ifBodyLabel = getNextLabel();
            String elseBodyLabel = getNextLabel();
            String endLabel = getNextLabel();
            ValueOrRegister prevVal = exprRegisters.pop();
            ir.branch(prevVal.toString(), ifBodyLabel, elseBodyLabel);
            ir.label(ifBodyLabel);
            lastLabels.push(new IfElseLabels(ifBodyLabel, elseBodyLabel, endLabel));
            lastLabel = ifBodyLabel;
        } else if (count == 1) { /* After the if body */
            IfElseLabels labels = (IfElseLabels) lastLabels.peek();
            String elseBodyLabel = labels.getElseBody();
            String endLabel = labels.getEnd();
            ir.jump(endLabel);
            ir.label(elseBodyLabel);
            lastLabel = elseBodyLabel;
        }
    }

    @Override
    protected void betweenWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current, int count) {
        WhileLabels labels = (WhileLabels) lastLabels.peek();
        String bodyLabel = labels.getBody();
        String exitLabel = labels.getExit();
        ir.branch(exprRegisters.pop().toString(), bodyLabel, exitLabel);
        ir.label(bodyLabel);
        lastLabel = bodyLabel;
    }

    @Override
    protected void betweenLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current, int count) {
        String leftLabel = lastLabel;
        String rightLabel = getNextLabel();
        String endLabel = getNextLabel();
        ValueOrRegister prevVal = exprRegisters.pop();
        ir.branch(prevVal.toString(), endLabel, rightLabel);
        ir.label(rightLabel);
        lastLabels.push(new LogicalExpressionLabels(leftLabel, rightLabel, endLabel));
        lastLabel = rightLabel;
    }

    @Override
    protected void betweenLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current, int count) {
        String leftLabel = lastLabel;
        String rightLabel = getNextLabel();
        String endLabel = getNextLabel();
        ValueOrRegister prevVal = exprRegisters.pop();
        ir.branch(prevVal.toString(), rightLabel, endLabel);
        ir.label(rightLabel);
        lastLabels.push(new LogicalExpressionLabels(leftLabel, rightLabel, endLabel));
        lastLabel = rightLabel;
    }

    @Override
    protected void betweenMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current, int count) {
        MethodCall methodCall = methodArgs.peek();
        String type, tempReg;
        switch (count) {
            case 0:
                // Get reciever
                ValueOrRegister receiverReg = exprRegisters.pop();
                type = symbolTable.lookUpVar(receiverReg.toString());
                Class receiverType = getClass(type);
                List<Method> methods = receiverType.getMethods();
                for (Method m : methods) {
                    if (m.getName().equals(current.getContext().ID().getText())) {
                        methodCall.setMethod(m);
                        break;
                    }
                }
                Class castToType = receiverType.getMethodParent(methodCall.getMethod());
                tempReg = ir.cast(nextRegister(), receiverReg.toString(), castToType.getName(), receiverType.getName());
                methodCall.addArg(tempReg, castToType.getName());
                methodCall.setReceiver(castToType);
                break;
            default:
                // Get param
                ValueOrRegister arg = exprRegisters.pop();
                type = getValOrRegType(arg);
                String castTo = null;
                int index = 1;
                for (Map.Entry<String, String> entry : methodCall.getMethod().getParams().entrySet()) {
                    if (index == count) {
                        castTo = entry.getValue();
                        break;
                    }
                    index++;
                }
                tempReg = ir.cast(nextRegister(), arg.toString(), castTo, type);
                methodCall.addArg(tempReg, type);
                break;
        }
    }

    @Override
    protected void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String className = current.getContext().ID().getText();
        Class clazz = getClass(className);
        String dstReg = ir.newConstruct(nextRegister(), nextRegister(), nextRegister(), clazz);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, className);
    }

    @Override
    protected void exitArrayConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String arrayType = current.getContext().single_type().getText();
        String arrayLength = exprRegisters.pop().toString();
        String dstReg = ir.newArray(nextRegister(), nextRegister(), nextRegister(), arrayType, arrayLength);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, arrayType + "[]");
    }

    @Override
    protected void exitArrayIndexAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
        String var = symbolTable.getName(current.getContext().ID().getText());
        String arrayType = symbolTable.lookUpVar(var);
        String type = arrayType.substring(0, arrayType.length() - 2);
        ValueOrRegister assignmentValue = exprRegisters.pop();
        ValueOrRegister index = exprRegisters.pop();
        String valOrRegType = getValOrRegType(assignmentValue);
        String castReg = ir.cast(nextRegister(), assignmentValue.toString(), type, valOrRegType);
        String arrayReg = ir.load(nextRegister(), arrayType, "%" + var);
        String elementReg = ir.getArrayElement(nextRegister(), arrayReg, index.toString(), type);
        ir.store(elementReg, type, castReg);
    }

    @Override
    protected void exitArrayAccess(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String var = symbolTable.getName(current.getContext().ID().getText());
        String arrayType = symbolTable.lookUpVar(var);
        String type = arrayType.substring(0, arrayType.length() - 2);
        ValueOrRegister index = exprRegisters.pop();
        String arrayReg = ir.load(nextRegister(), arrayType, "%" + var);
        String elementReg = ir.getArrayElement(nextRegister(), arrayReg, index.toString(), type);
        String dstReg = ir.load(nextRegister(), type, elementReg);
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, type);
    }

    @Override
    protected void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String id = symbolTable.getName(current.getContext().ID().getText());
        String type = symbolTable.lookUpVar(id);
        String dstReg;
        if (symbolTable.isClassVar(id)) {
            String tmpReg = nextRegister();
            ir.load(tmpReg, thisClass.getName(), "%this");
            int index = thisClass.getFieldIndex(id);
            dstReg = ir.getClassElement(nextRegister(), nextRegister(), type, thisClass.getName(), tmpReg, index);
        } else {
            dstReg = ir.load(nextRegister(), type, "%" + id);
        }
        exprRegisters.push(new ValueOrRegister(dstReg));
        symbolTable.addVar(dstReg, type);
    }

    @Override
    protected void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String type = thisClass.getName();
        String dstReg = nextRegister();
        exprRegisters.push(new ValueOrRegister(ir.load(dstReg, type, "%this")));
        symbolTable.addVar(dstReg, type);
    }

    @Override
    protected void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        exprRegisters.push(new ValueOrRegister());
    }

    @Override
    protected void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String aBool = current.getContext().BOOL().getText();
        boolean bool = Boolean.parseBoolean(aBool);
        exprRegisters.push(new ValueOrRegister(bool));
    }

    @Override
    protected void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String anInt = current.getContext().INT().getText();
        int value = Integer.parseInt(anInt);
        exprRegisters.push(new ValueOrRegister(value));
    }

    @Override
    protected void enterCharacter(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String aChar = current.getContext().CHAR().getText();
        char value;
        switch (aChar) {
            case "'\\n'":
                value = '\n';
                break;
            case "'\\t'":
                value = '\t';
                break;
            default:
                value = aChar.charAt(1);
        }
        exprRegisters.push(new ValueOrRegister(value));
    }

    @Override
    protected void enterMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {
        methodArgs.push(new MethodCall());
    }

    @Override
    protected void enterWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {
        String testLabel = getNextLabel();
        String bodyLabel = getNextLabel();
        String exitLabel = getNextLabel();
        ir.jump(testLabel);
        ir.label(testLabel);
        lastLabels.push(new WhileLabels(testLabel, bodyLabel, exitLabel));
        lastLabel = testLabel;
    }

    @Override
    protected void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
        symbolTable = new SymbolTable(symbolTable);
    }

    @Override
    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {
        String methodName = current.getContext().ID().getText();
        Method method = thisClass.getMethods()
                .stream()
                .filter(method1 -> method1.getName().equals(methodName))
                .findFirst()
                .get();
        String mangledName = thisClass.getName() + "_" + method.getName();
        symbolTable = new SymbolTable(symbolTable);
        method.getParams().entrySet()
                .stream()
                .forEach(e -> symbolTable.addVar(e.getKey(), e.getValue()));
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("this", thisClass.getName());
        params.putAll(method.getParams());
        Map<String, String> mangledParams = params.entrySet()
                .stream()
                .map(e -> new Pair<>("$" + e.getKey(), e.getValue()))
                .collect(Collectors.toMap(e -> e.a, e -> e.b, (a, b) -> b, LinkedHashMap::new));

        // Reset labels and registers
        labelCount = 0;
        registerCounter = 0;
        ir.startMethod(mangledName, mangledParams, method.getReturnType(), getNextLabel());
        params.forEach((name, type) -> {
            String mName = "%$" + name;
            name = "%" + name;
            ir.allocateStack(name, type);
            ir.store(name, type, mName);
        });

    }

    @Override
    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {
        String clazzName = current.getContext().ID(0).getText();
        thisClass = getClass(clazzName);
        symbolTable = new ClassSymbolTable(symbolTable, thisClass);
    }

    @Override
    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
        symbolTable = new SymbolTable(symbolTable);
        String label = getNextLabel();
        ir.startMethod("main", Collections.emptyMap(), "int", label);
        lastLabel = label;
    }

    private Class getClass(String name) {
        Class result = null;
        for (Class clazz : classList) {
            if (clazz.getName().equals(name)) {
                result = clazz;
                break;
            }
        }
        return result;
    }


    private boolean isPrimative(String name) {
        return name.equals("int") || name.equals("boolean") || name.equals("char");
    }

    @Override
    public String toString() {
        return ir.toString();
    }
}
