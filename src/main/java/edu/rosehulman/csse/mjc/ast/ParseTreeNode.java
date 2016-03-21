package edu.rosehulman.csse.mjc.ast;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeNode<T extends ParserRuleContext> {

    protected T context;

    protected List<ParseTreeNode> children = new ArrayList<>();


    public ParseTreeNode(T context) {
        this.context = context;
    }

    public void addChild(ParseTreeNode child) {
        children.add(child);
    }

    public List<ParseTreeNode> getChildren() {
        return children;
    }

    public T getContext() {
        return context;
    }
}
