package edu.rosehulman.csse.mjc;

import edu.rosehulman.csse.mjc.ast.AbstractSyntaxNode;
import edu.rosehulman.csse.mjc.ast.BaseWalker;
import edu.rosehulman.csse.mjc.reflect.Class;
import edu.rosehulman.csse.mjc.reflect.Method;

import java.util.*;
import java.util.stream.Collectors;

public class ClassHierarchyBuilder extends BaseWalker {

    private List<Class> classes = new ArrayList<>();
    private Class current;

    public ClassHierarchyBuilder(AbstractSyntaxNode root) {
        super(root);
    }

    @Override
    protected void enterMethodDecl(AbstractSyntaxNode<MiniJavaParser.MethodDeclContext> node) {
        String methodName = node.getContext().ID().getText();
        Map<String, String> methodParams = node.getContext().formal()
                .stream()
                .collect(Collectors.toMap(
                        f -> f.ID().getText(),
                        f -> f.type().getText(),
                        (u, v) -> { throw new RuntimeException("Duplicate method param for " + methodName); },
                        LinkedHashMap::new
                ));
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
        if (classes.stream().anyMatch(c -> c.getName().equals(current.getName()))) {
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
        classes.add(new Class(node.getContext().ID(0).getText()));
    }

    public List<Class> getClasses() {
        return classes;
    }

}
