package edu.rosehulman.csse.mjc.reflect;


import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Class {
    private LinkedHashMap<String, String> fields;
    private List<Method> parentMethods;
    private List<Method> methods;
    private String name;
    private Class parent;

    public Class(String name) {
        this.name = name;
        fields = new LinkedHashMap<>();
        methods = new ArrayList<>();
        parentMethods = new ArrayList<>();
    }

    public LinkedHashMap<String, String> getFields() {
        return fields;
    }

    public int getFieldIndex(String name) {
        // Account for VTable
        int index = 1;
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getKey().equals(name)) {
                return index;
            }
            index++;
        }
        throw new RuntimeException("Name " + name + " not in class " + this.name);
    }

    public int getMethodIndex(Method m) {
        return getMethods().indexOf(m);
    }

    public void addFields(String name, String type) {
        if (fields.containsKey(name)) {
            throw new RuntimeException("Class " + name + " already has field with that name");
        }
        this.fields.put(name, type);
    }

    public List<Method> getMethods() {
        ArrayList<Method> list = new ArrayList<>();
        List<Method> overriddenMethods = new ArrayList<>();
        List<Method> inhiertedMethods = parentMethods.stream()
                .map(superMethod -> {
                    for (Method method : methods) {
                       if (superMethod.equals(method)) {
                           overriddenMethods.add(method);
                           return method;
                       }
                    }
                    return superMethod;
                })
                .collect(toList());
        list.addAll(inhiertedMethods);
        list.addAll(methods.stream()
                .filter(method -> !overriddenMethods.contains(method))
                .collect(Collectors.toList()));
        return list;
    }

    public Class getMethodParent(Method method) {
        if (this.methods.contains(method)) {
            return this;
        } else {
            return parent.getMethodParent(method);
        }
    }

    public void addMethod(Method method) {
        boolean dup = methods.stream()
                .anyMatch(m -> Objects.equals(m.getName(), method.getName()));
        if (dup) {
            throw new RuntimeException("Class " + name + " already has method with name: " + method.getName());
        }
        Optional<Method> overridenMethod = parentMethods.stream()
                .filter(m -> Objects.equals(m.getName(), method.getName()))
                .findFirst();
        overridenMethod.ifPresent(m -> {
            if (!Objects.equals(method, m)) {
                throw new RuntimeException("Overriden method " + method.getName() + " cannot have a different signature.");
            }
        });
        this.methods.add(method);
    }

    public void setExtends(Class clazz) {
        // Don't need parent fields because no shadowing is allowed.
        this.fields.putAll(clazz.fields);
        this.parentMethods.addAll(clazz.getMethods());
        this.parent = clazz;
    }
    public Class getParent() {
        return parent;
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
