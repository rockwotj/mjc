package edu.rosehulman.csse.mjc.ir;

public class LogicalExpressionLabels extends Labels {
    private final String left;
    private final String right;
    private final String end;

    public LogicalExpressionLabels(String left, String right, String end) {
        this.left = left;
        this.right = right;
        this.end = end;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getEnd() {
        return end;
    }
}
