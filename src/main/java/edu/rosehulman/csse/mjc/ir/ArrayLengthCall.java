package edu.rosehulman.csse.mjc.ir;

import edu.rosehulman.csse.mjc.reflect.Method;
import org.antlr.v4.runtime.misc.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ArrayLengthCall extends MethodCall {
    private String arrayType;
    private String register;

    public String getArrayType() {
        return arrayType;
    }

    public void setArrayType(String arrayType) {
        this.arrayType = arrayType;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    @Override
    public Method getMethod() {
        return new Method("int", new HashMap<>(), "length");
    }

    @Override
    public List<Pair<String, String>> getArguments() {
        return Collections.emptyList();
    }
}
