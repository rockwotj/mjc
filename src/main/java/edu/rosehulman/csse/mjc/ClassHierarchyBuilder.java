package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.BaseWalker;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassHierarchyBuilder extends BaseWalker {

    private List<Class> classes = new ArrayList<>();
    private Class current;
    private String mainClassName = null;

    public ClassHierarchyBuilder(AbstractSyntaxNode root) {
        super(root);
    }

    @Override
    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> node) {
        String methodName = node.getContext().ID().getText();
        if (current.getMethods().stream().anyMatch(m -> m.getName().equals(methodName))) {
            throw new RuntimeException("Duplicate method name " + methodName + " in " + current.getName());
        }
        List<String> methodParams = node.getContext().formal()
                .stream()
                .map(f -> f.type().getText())
                .collect(Collectors.toList());
        String returnType = node.getContext().type().getText();
        Method method = new Method(returnType, methodParams, methodName);
        this.current.addMethod(method);
    }

    @Override
    protected void enterClassVarDecl(AbstractSyntaxNode<MiniJavaParser.ClassVarDeclContext> node) {
        String fieldType = node.getContext().type().getText();
        String fieldName = node.getContext().ID().getText();
        this.current.addFields(fieldName, fieldType);
    }

    @Override
    protected void enterClassDecl(AbstractSyntaxNode<MiniJavaParser.ClassDeclContext> node) {
        this.current = new Class(node.getContext().ID(0).getText());
        if (classes.stream().anyMatch(c -> c.getName().equals(current.getName())) || current.getName().equals(mainClassName)) {
            throw new RuntimeException("Duplicate class name: " + current.getName());
        }
        if (node.getContext().ID(1) != null) {
            String superName = node.getContext().ID(1).getText();
            Optional<Class> superClass = classes.stream()
                    .filter(c -> c.getName().equals(superName))
                    .findFirst();
            if (!superClass.isPresent()) {
                throw new RuntimeException("Superclass " + superName + " does not exist");
            }
            current.setExtends(superClass.get());
        }
        classes.add(current);
    }

    @Override
    protected void enterMainClassDecl(AbstractSyntaxNode<MiniJavaParser.MainClassDeclContext> node) {
        mainClassName = node.getContext().ID(0).getText();
    }

    public List<Class> getClasses() {
        return classes;
    }

}
