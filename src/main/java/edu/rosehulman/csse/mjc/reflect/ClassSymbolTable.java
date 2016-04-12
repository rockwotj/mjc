package edu.rosehulman.csse.mjc.reflect;


public class ClassSymbolTable extends SymbolTable {
    public ClassSymbolTable(SymbolTable symbolTable, Class c) {
        super(symbolTable);
        c.getFields().forEach(this::addVar);
    }
}
