package edu.rosehulman.csse.mjc.ir;


public class ValueOrRegister {

    private Integer value;
    private Boolean bool;
    private String register;

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
        return "" + (value == null ? bool : value);
    }
}
