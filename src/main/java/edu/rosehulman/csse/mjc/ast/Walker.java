package edu.rosehulman.csse.mjc.ast;

public abstract class Walker {

    protected final AbstractSyntaxNode root;
    protected AbstractSyntaxNode current;

    public Walker(AbstractSyntaxNode root) {
        this.root = root;
    }



}
