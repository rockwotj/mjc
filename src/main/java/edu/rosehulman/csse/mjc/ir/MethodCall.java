package edu.rosehulman.csse.mjc.ir;

import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.Method;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;

public class MethodCall {

    private Class receiver;
    private List<Pair<String, String>> arguments = new ArrayList<>();
    private Method method;


    public MethodCall() {
    }

    public Class getReceiver() {
        return receiver;
    }

    public void setReceiver(Class receiver) {
        this.receiver = receiver;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return receiver.getName() + "_" + method.getName();
    }

    public List<Pair<String, String>> getArguments() {
        return arguments;
    }

    public void addArg(String valueOrReg, String type) {
        arguments.add(new Pair<>(valueOrReg, type));
    }

    public void setArguments(List<Pair<String, String>> arguments) {
        this.arguments = arguments;
    }

}
