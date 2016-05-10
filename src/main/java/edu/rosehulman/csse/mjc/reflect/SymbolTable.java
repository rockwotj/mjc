package edu.rosehulman.csse.mjc.reflect;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    protected Map<String, String> vars = new HashMap<>();
    protected Map<String, Integer> previouslyDelcaredVars = new HashMap<>();
    private SymbolTable parent = null;

    public SymbolTable() {

    }

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.previouslyDelcaredVars.putAll(parent.previouslyDelcaredVars);
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

    public boolean isClassVar(String name) {
        return !vars.containsKey(name) && parent.isClassVar(name);
    }

    public void addVar(String name, String type) {
        if (containsVar(name)) {
            System.out.println(this);
            throw new RuntimeException("Variable " + name + " already has been declared in scope.");
        }
        vars.put(name, type);
    }

    protected boolean containsVar(String name) {
        return vars.containsKey(name) || (parent != null && getParent().containsVar(name));
    }

    public String getName(String name) {
        Integer timesSeen = previouslyDelcaredVars.getOrDefault(name, 0);
        if (timesSeen > 0) {
            return name + timesSeen;
        } else if (parent != null) {
            return parent.getName(name);
        } else {
            return name;
        }
    }

    public String declareVar(String name) {
        int timesSeen = previouslyDelcaredVars.getOrDefault(name, 0);
        previouslyDelcaredVars.put(name, timesSeen + 1);
        if (parent != null) {
            parent.declareVar(name);
        }
        return getName(name);
    }

    public SymbolTable getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return vars.toString() + " | " + (parent != null ? parent.toString() : "null");
    }

    public void clearPreviouslyDeclaredVars() {
        previouslyDelcaredVars.clear();
    }
}
