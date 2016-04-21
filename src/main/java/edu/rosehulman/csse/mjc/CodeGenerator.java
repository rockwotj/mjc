package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.Walker;
import edu.rosehulman.csse.mjc.ir.LlvmIr;
import edu.rosehulman.csse.mjc.ir.ValueOrRegister;
import edu.rosehulman.csse.mjc.reflect.SymbolTable;

import java.util.Stack;

public class CodeGenerator extends Walker {

    private LlvmIr ir = new LlvmIr();
    private Stack<ValueOrRegister> exprRegisters = new Stack<>();
    private SymbolTable symbolTable = new SymbolTable();

    private String getRegisterCount() {
        int i = this.registerCounter;
        this.registerCounter++;
        return "%" + i;

    }

    private String getLabelCount() {
        int i = this.labelCounter;
        this.labelCounter++;
        return "%" + i;

    }

    private int registerCounter = 1;
    private int labelCounter = 1;



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

    }

    @Override
    protected void exitWhile(AbstractSyntaxNode<MiniJavaParser.WhileDeclContext> current) {

    }

    @Override
    protected void exitPrint(AbstractSyntaxNode<MiniJavaParser.PrintContext> current) {
        ValueOrRegister valueOrRegister = exprRegisters.pop();
        ir.print(valueOrRegister.toString());
    }

    @Override
    protected void exitAssignment(AbstractSyntaxNode<MiniJavaParser.AssigmentContext> current) {

    }

    @Override
    protected void exitExpr(AbstractSyntaxNode<MiniJavaParser.ExprContext> current) {

    }

    @Override
    protected void exitLogicalOr(AbstractSyntaxNode<MiniJavaParser.LogicalOrContext> current) {

    }

    @Override
    protected void exitLogicalAnd(AbstractSyntaxNode<MiniJavaParser.LogicalAndContext> current) {

    }

    @Override
    protected void exitEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitNotEquals(AbstractSyntaxNode<MiniJavaParser.EqualsOrNotEqualsContext> current) {

    }

    @Override
    protected void exitLessThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.lessThan(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitGreaterThan(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.greaterThan(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitLessThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.lessThanEqualTo(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitGreaterThanEquals(AbstractSyntaxNode<MiniJavaParser.RelationContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.greaterThanEqualTo(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitPlus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.add(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitMinus(AbstractSyntaxNode<MiniJavaParser.PlusOrMinusContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.minus(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitMult(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.mult(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitDiv(AbstractSyntaxNode<MiniJavaParser.MultOrDivContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        ValueOrRegister valueOrRegister2 = exprRegisters.pop();
        String dstReg = ir.div(getRegisterCount(), "int", valueOrRegister1.toString(), valueOrRegister2.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitBang(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.bang(getRegisterCount(), "boolean", valueOrRegister1.toString());
        exprRegisters.push(new ValueOrRegister(dstReg));
    }

    @Override
    protected void exitNeg(AbstractSyntaxNode<MiniJavaParser.UnaryContext> current) {
        ValueOrRegister valueOrRegister1 = exprRegisters.pop();
        String dstReg = ir.neg(getRegisterCount(), "int", valueOrRegister1.toString());
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
    protected void enterConstructor(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {

    }

    @Override
    protected void enterId(AbstractSyntaxNode<MiniJavaParser.AtomContext> current) {
        String id = current.getContext().ID().getText();
        String type = symbolTable.lookUpVar(id);
        ValueOrRegister register = new ValueOrRegister(ir.load(getRegisterCount(), type, "%" + id));
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
        ir.startMethod("main", "int");
    }

    @Override
    protected void enterProgram(AbstractSyntaxNode<MiniJavaParser.ProgramContext> current) {

    }

    @Override
    public String toString() {
        return ir.toString();
    }
}
