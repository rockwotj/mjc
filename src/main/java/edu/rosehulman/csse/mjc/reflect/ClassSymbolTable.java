package edu.rosehulman.csse.mjc.reflect;


public class ClassSymbolTable extends SymbolTable {
    public ClassSymbolTable(SymbolTable symbolTable, Class c) {
        super(symbolTable);
        c.getFields().forEach(this::addVar);
    }

    @Override
    protected boolean containsVar(String name) {
        return false;
    }

    @Override
    public boolean isClassVar(String name) {
        return vars.containsKey(name);
    }

    @Override
    public String declareVar(String name) { return name; }

    @Override
    public String getName(String name) {
        return name;
    }
}
