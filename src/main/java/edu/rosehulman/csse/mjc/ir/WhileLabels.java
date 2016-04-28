package edu.rosehulman.csse.mjc.ir;

public class WhileLabels extends Labels {
    private final String test;
    private final String body;
    private final String exit;

    public WhileLabels(String test, String body, String exit) {
        this.test = test;
        this.body = body;
        this.exit = exit;
    }

    public String getTest() {
        return test;
    }

    public String getBody() {
        return body;
    }

    public String getExit() {
        return exit;
    }
}
