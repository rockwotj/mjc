package edu.rosehulman.csse.mjc.ir;


public class ValueOrRegister {

    private Integer value;
    private Boolean bool;
    private String register;

    public ValueOrRegister() {}

    public ValueOrRegister(int value) {
        this.value = value;
    }

    public ValueOrRegister(boolean value) {
        this.bool = value;
    }

    public ValueOrRegister(String register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return register == null ? getValue() + "" : register;
    }

    public boolean isRegister() {
        return register != null;
    }

    public boolean isValue() {
        return !isRegister();
    }


    public String getValue() {
        return "" + (value == null ? bool == null ? "null" : bool : value);
    }

    public boolean isInt() { return value != null; }

    public boolean isBool() { return bool != null; }

    public boolean isNull() {
        return value == null && bool == null && register == null;
    }

    public String getType() {
        if (this.isRegister()) {
            return "register";
        } else {
            if (this.isInt()) {
                return "int";
            } else if (this.isBool()) {
                return "boolean";
            } else {
                return "null";
            }
        }
    }
}
