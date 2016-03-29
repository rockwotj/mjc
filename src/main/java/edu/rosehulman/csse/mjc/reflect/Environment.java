package edu.rosehulman.csse.mjc.reflect;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String, String> vars = new HashMap<>();
    private Environment parent = null;

    public Environment() {

    }

    public Environment(Environment parent) {
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
        vars.put(name, type);
    }

    public Environment getParent() {
        return parent;
    }
}
