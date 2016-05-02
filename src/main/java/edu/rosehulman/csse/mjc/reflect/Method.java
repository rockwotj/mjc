package edu.rosehulman.csse.mjc.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Method {

    private String returnType;
    private Map<String, String> params;
    private String name;

    public Method(String returnType, Map<String, String> params, String name) {
        this.returnType = returnType;
        this.params = params;
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public List<String> getParamTypes() {
        List<String> paramTypes = new ArrayList<String>();
        paramTypes.addAll(params.values());
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
                this.params.equals(that.params);
    }

    @Override
    public String toString() {
        return name + " params: " + params.toString() + " returns:" + returnType;
    }
}
