package edu.rosehulman.csse.mjc.reflect;


import java.util.*;

public class Class {
    private Map<String, String> fields;
    private List<Method> methods;
    private String name;

    public Class(String name) {
        this.name = name;
        fields = new HashMap<>();
        methods = new ArrayList<>();
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void addFields(String name, String type) {
        this.fields.put(name, type);
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        this.methods.add(method);
    }

    public void setExtends(Class clazz) {
        this.fields.putAll(clazz.fields);
        this.methods.addAll(clazz.methods);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " fields: " + fields.toString() + " methods:" + methods.toString();
    }
}
