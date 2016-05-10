package edu.rosehulman.csse.mjc.ir;


public class ValueOrRegister {

    private Integer value;
    private Character character;
    private Boolean bool;
    private String register;

    public ValueOrRegister() {
    }

    public ValueOrRegister(char value) {
        this.character = value;
    }

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
        return "" + (value == null ? bool == null ? character == null ? "null" : ((int)character) : bool : value);
    }

    public boolean isInt() {
        return value != null;
    }

    public boolean isBool() {
        return bool != null;
    }

    public boolean isNull() {
        return value == null && bool == null && register == null && character == null;
    }

    public String getType() {
        if (this.isRegister()) {
            return "register";
        } else {
            if (this.isInt()) {
                return "int";
            } else if (this.isBool()) {
                return "boolean";
            } else if (this.isCharacter()) {
                return "char";
            } else {
                return "null";
            }
        }
    }

    private boolean isCharacter() {
        return character != null;
    }
}
