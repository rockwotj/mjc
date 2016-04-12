package edu.rosehulman.csse.mjc.reflect;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, String> vars = new HashMap<>();
    private SymbolTable parent = null;

    public SymbolTable() {

    }

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    /**
     * @param name var name
     * @return var type or null if it doesn't exist
     */
    public String lookUpVar(String name) {
        if (vars.containsKey(name)) {
            return vars.get(name);
        } else if (parent != null) {
            return parent.lookUpVar(name);
        } else {
            throw new RuntimeException("Variable " + name + " does not exist within scope.");
        }
    }

    public void addVar(String name, String type) {
        if (vars.containsKey(name)) {
            throw new RuntimeException("Variable " + name + " already has been declared in scope.");
        }
        vars.put(name, type);
    }

    public SymbolTable getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return vars.toString() + " | " + (parent != null ? parent.toString() : "");
    }
}
