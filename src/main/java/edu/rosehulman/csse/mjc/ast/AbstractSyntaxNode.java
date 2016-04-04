package edu.rosehulman.csse.mjc.ast;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractSyntaxNode<T extends ParserRuleContext> {

    protected T context;
    protected final NodeType type;

    protected List<AbstractSyntaxNode<ParserRuleContext>> children = new ArrayList<>();


    public enum NodeType {
        program,
        mainClassDecl,
        classDecl,
        classVarDecl,
        methodDecl,
        formal,
        type,
        stmt,
        varDecl,
        block,
        ifElse,
        whileDecl,
        print,
        assigment,
        expr,
        logicalOr,
        logicalAnd,
        equals,
        notEquals,
        lessThan,
        greaterThan,
        lessThanEquals,
        greaterThanEquals,
        plus,
        minus,
        mult,
        div,
        bang,
        negative,
        methodCall,
        integer,
        bool,
        nil,
        self,
        id,
        constructor
    }


    public AbstractSyntaxNode(T context, NodeType type) {
        this.context = context;
        this.type = type;
    }

    public void addChild(AbstractSyntaxNode<ParserRuleContext> child) {
        children.add(child);
    }

    public List<AbstractSyntaxNode<ParserRuleContext>> getChildren() {
        return children;
    }

    public T getContext() {
        return context;
    }

    public NodeType getType() {
        return type;
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
