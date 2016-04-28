package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.Walker;
import edu.rosehulman.csse.mjc.ir.*;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;

import java.util.Stack;

public class CodeGenerator extends Walker {

    private LlvmIr ir = new LlvmIr();
    private Stack<ValueOrRegister> exprRegisters = new Stack<>();
    private SymbolTable symbolTable = new SymbolTable();

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

    private String lastLabel;
    private Stack<Labels> lastLabels = new Stack<Labels>();
    private int registerCounter = 0;
    private int labelCount = 0;

    public CodeGenerator(AbstractSyntaxNode ast) {
        super(ast);
    }

    @Override
    protected void exitProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {

    }

    @Override
    protected void exitMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
        symbolTable = symbolTable.getParent();
        ir.returnStatment("0", "int");
        ir.endMethod();
    }

    @Override
    protected void exitClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {

    }

    @Override
    protected void exitClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {

    }

    @Override
    protected void exitMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {

    }

    @Override
    protected void exitFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {

    }

    @Override
    protected void exitType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {

    }

    @Override
    protected void exitStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {

    }

    @Override
    protected void exitVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
        String id = current.getContext().ID().getText();
        String type = current.getContext().type().getText();
        ir.allocateStack("%" + id, type);
        ir.store("%" + id, type, exprRegisters.pop().toString());
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
    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        ir.print(nextRegister(), valueOrRegister.toString());
    }

    @Override
    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {
        String var = current.getContext().ID().getText();
        String type = symbolTable.lookUpVar(var);
        ir.store("%" + var, type, exprRegisters.pop().toString());
    }

    @Override
    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {

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
        lastLabel = endLabel;
    }

    @Override
    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.lessThan(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.greaterThan(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.lessThanEqualTo(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.greaterThanEqualTo(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.add(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.minus(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.mult(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.div(nextRegister(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.bang(nextRegister(), "boolean", valueOrRegister1.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.neg(nextRegister(), "int", valueOrRegister1.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {

    }

    @Override
    protected void exitInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void exitConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void betweenProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current, int count) {

    }

    @Override
    protected void betweenMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current, int count) {

    }

    @Override
    protected void betweenClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current, int count) {

    }

    @Override
    protected void betweenClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current, int count) {

    }

    @Override
    protected void betweenMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current, int count) {

    }

    @Override
    protected void betweenFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current, int count) {

    }

    @Override
    protected void betweenType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current, int count) {

    }

    @Override
    protected void betweenStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current, int count) {

    }

    @Override
    protected void betweenVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current, int count) {

    }

    @Override
    protected void betweenBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current, int count) {

    }

    @Override
    protected void betweenIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current, int count) {
        if (count == 0) { /* After the test */
            String ifBodyLabel = getNextLabel();
            String elseBodyLabel= getNextLabel();
            String endLabel = getNextLabel();
            ValueOrRegister prevVal = exprRegisters.pop();
            ir.branch(prevVal.toString(), ifBodyLabel, elseBodyLabel);
            ir.label(ifBodyLabel);
            lastLabels.push(new IfElseLabels(ifBodyLabel, elseBodyLabel, endLabel));
            lastLabel = ifBodyLabel;
        } else if (count == 1) { /* After the if body */
            IfElseLabels labels = (IfElseLabels) lastLabels.peek();
            String elseBodyLabel= labels.getElseBody();
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
    protected void betweenPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current, int count) {

    }

    @Override
    protected void betweenAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current, int count) {

    }

    @Override
    protected void betweenExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current, int count) {

    }

    @Override
    protected void betweenLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current, int count) {
        String leftLabel = lastLabel;
        String rightLabel= getNextLabel();
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
        String rightLabel= getNextLabel();
        String endLabel = getNextLabel();
        ValueOrRegister prevVal = exprRegisters.pop();
        ir.branch(prevVal.toString(), rightLabel, endLabel);
        ir.label(rightLabel);
        lastLabels.push(new LogicalExpressionLabels(leftLabel, rightLabel, endLabel));
        lastLabel = rightLabel;
    }

    @Override
    protected void betweenEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count) {

    }

    @Override
    protected void betweenNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current, int count) {

    }

    @Override
    protected void betweenLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current, int count) {

    }

    @Override
    protected void betweenPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count) {

    }

    @Override
    protected void betweenMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current, int count) {

    }

    @Override
    protected void betweenMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count) {

    }

    @Override
    protected void betweenDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current, int count) {

    }

    @Override
    protected void betweenBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count) {

    }

    @Override
    protected void betweenNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current, int count) {

    }

    @Override
    protected void betweenMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current, int count) {

    }

    @Override
    protected void betweenInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void betweenConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current, int count) {

    }

    @Override
    protected void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String id = current.getContext().ID().getText();
        String type = symbolTable.lookUpVar(id);
        ValueOrRegister register = new ValueOrRegister(ir.load(nextRegister(), type, "%" + id));
        exprRegisters.push(register);
    }

    @Override
    protected void enterThis(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterNull(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterBool(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String aBool = current.getContext().BOOL().getText();
        exprRegisters.push(new ValueOrRegister(aBool));
    }

    @Override
    protected void enterInt(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String anInt = current.getContext().INT().getText();
        int value = Integer.parseInt(anInt);
        exprRegisters.push(new ValueOrRegister(value));
    }

    @Override
    protected void enterMethodCall(AbstractSyntaxNode<MiniJavaParser.MethodCallContext> current) {

    }

    @Override
    protected void enterNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void enterBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {

    }

    @Override
    protected void enterDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void enterMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {

    }

    @Override
    protected void enterMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void enterPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {

    }

    @Override
    protected void enterGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {

    }

    @Override
    protected void enterNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void enterEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void enterLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {

    }

    @Override
    protected void enterLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {

    }

    @Override
    protected void enterExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {

    }

    @Override
    protected void enterAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {

    }

    @Override
    protected void enterPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {

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
    protected void enterIfElse(AbstractSyntaxNode<MiniJavaParser.IfElseContext> current) {

    }

    @Override
    protected void enterBlock(AbstractSyntaxNode<MiniJavaParser.BlockContext> current) {
        symbolTable = new SymbolTable(symbolTable);
    }

    @Override
    protected void enterVarDecl(AbstractSyntaxNode<MiniJavaParser.VarDeclContext> current) {
    }

    @Override
    protected void enterStmt(AbstractSyntaxNode<MiniJavaParser.StmtContext> current) {

    }

    @Override
    protected void enterType(AbstractSyntaxNode<MiniJavaParser.TypeContext> current) {

    }

    @Override
    protected void enterFormal(AbstractSyntaxNode<MiniJavaParser.FormalContext> current) {

    }

    @Override
    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> current) {

    }

    @Override
    protected void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> current) {
    }

    @Override
    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> current) {

    }

    @Override
    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> current) {
        symbolTable = new SymbolTable(symbolTable);
        String label = getNextLabel();
        ir.startMethod("main", "int", label);
        lastLabel = label;
    }

    @Override
    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {

    }

    @Override
    public String toString() {
        return ir.toString();
    }
}
