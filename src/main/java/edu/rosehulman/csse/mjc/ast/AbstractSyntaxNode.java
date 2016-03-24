package edu.rosehulman.csse.mjc.ast;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractSyntaxNode<T extends ParserRuleContext> {

    protected T context;

    protected List<AbstractSyntaxNode> children = new ArrayList<>();


    public AbstractSyntaxNode(T context) {
        this.context = context;
    }

    public void addChild(AbstractSyntaxNode child) {
        children.add(child);
    }

    public List<AbstractSyntaxNode> getChildren() {
        return children;
    }

    public T getContext() {
        return context;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int level) {
        String result = "{ \"node\": \"" + context.getClass().getSimpleName() + "\"";
        result += ",\"children\": [" +
                String.join(",",
                        children.stream()
                                .map(n -> n.toString(level + 1))
                                .collect(Collectors.toList())) + "]";
        result += "}";
        return result;
    }
}
