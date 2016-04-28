package edu.rosehulman.csse.mjc.ir;

public class IfElseLabels extends Labels {
    private final String ifBody;
    private final String elseBody;
    private final String end;

    public IfElseLabels(String ifBody, String elseBody, String end) {
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.end = end;
    }

    public String getIfBody() {
        return ifBody;
    }

    public String getElseBody() {
        return elseBody;
    }

    public String getEnd() {
        return end;
    }
}
