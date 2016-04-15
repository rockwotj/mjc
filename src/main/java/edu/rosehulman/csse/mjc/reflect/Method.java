package edu.rosehulman.csse.mjc.reflect;

import java.util.List;

public class Method {

    private String returnType;
    private List<String> paramTypes;
    private String name;

    public Method(String returnType, List<String> paramTypes, String name) {
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Method)) {
            return false;
        }
        Method that = (Method) obj;
        return this.returnType.equals(that.returnType) &&
                this.name.equals(that.name) &&
                this.paramTypes.equals(that.paramTypes);
    }

    @Override
    public String toString() {
        return name + " params: " + paramTypes.toString() + " returns:" + returnType;
    }
}
