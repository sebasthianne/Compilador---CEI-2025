package compiler.domain;

import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.*;
import injector.Injector;

import java.util.HashMap;

public class Class{
    private final Token name;
    private Token inheritsFrom;
    private final Token modifier;
    private final HashMap<String,Constructor> constructorTable;
    private final HashMap<String,Method> methodTable;
    private final HashMap<String, Attribute> attributeTable;

    public Class(Token name, Token modifier) {
        this.name = name;
        this.modifier=modifier;
        this.inheritsFrom=null;
        constructorTable= new HashMap<>(997);
        methodTable= new HashMap<>(997);
        attributeTable = new HashMap<>(997);
    }

    public Token getName() {
        return name;
    }

    public void setInheritsFrom(Token inheritsFrom) {
        this.inheritsFrom = inheritsFrom;
    }

    public Token getModifier(){
        return modifier;
    }

    public Token getInheritsFrom() {
        return inheritsFrom;
    }

    public Iterable<Constructor> getConstructorTable() {
        return constructorTable.values();
    }

    public Constructor getConstructor(Token name){
        return constructorTable.get(name.lexeme());
    }

    public void addConstructor(Constructor constructor){
        constructorTable.put(constructor.getName().lexeme(),constructor);
    }

    public Iterable<Method> getMethodTable() {
        return methodTable.values();
    }

    public Method getMethod(Token name){
        return methodTable.get(name.lexeme());
    }

    public void addMethod(Method method){
        methodTable.put(method.getName().lexeme(),method);
    }

    public Iterable<Attribute> getAttributeTable() {
        return attributeTable.values();
    }

    public Attribute getAttribute(Token name){
        return attributeTable.get(name.lexeme());
    }

    public void addAttribute(Attribute attribute){
        attributeTable.put(attribute.getName().lexeme(),attribute);
    }

    public boolean isFinal(){
        return (getModifier() != null && getModifier().name().equals("palabraReservadafinal"));
    }

    public boolean isStatic(){
        return (getModifier() != null && getModifier().name().equals("palabraReservadastatic"));
    }

    public boolean isAbstract(){
        return (getModifier() != null && getModifier().name().equals("palabraReservadaabstract"));
    }

    public boolean isAncestor(Token className){
        boolean isAncestor = false;
        Class currentClass = this;
        if(className.lexeme().equals("Object")) isAncestor=true;
        else {
            while (!isAncestor&&!currentClass.getInheritsFrom().lexeme().equals("Object")) {
                isAncestor= className.lexeme().equals(currentClass.getInheritsFrom().lexeme());
                currentClass= Injector.getInjector().getSymbolTable().getClass(currentClass.getInheritsFrom());
            }
        }
        return isAncestor;
    }

    public void checkClass() throws SemanticException {
        if(!isObject() && !inheritsFromObject()) {
            inheritanceChecks();
        }
        attributeChecks();
        methodChecks();
        constructorChecks();

    }

    private void constructorChecks() throws SemanticException {
        if(!isAbstract()&&constructorTable.isEmpty()) {
            addDefaultConstructor();
        } else{
            for(Constructor c:getConstructorTable()){
                c.checkConstructor();
            }
        }
    }

    private void methodChecks() throws SemanticException {
        for (Method m:getMethodTable()){
            m.checkMethod();
        }
    }

    private void attributeChecks() throws SemanticException {
        for(Attribute a:getAttributeTable()){
            a.checkAttribute();
        }
    }

    private void addDefaultConstructor() {
        addConstructor(new Constructor(name));
    }

    private boolean inheritsFromObject() {
        return inheritsFrom.lexeme().equals("Object");
    }

    private boolean isObject() {
        return name.lexeme().equals("Object");
    }

    private void inheritanceChecks() throws SemanticException {
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        Class classInheritsFrom = symbolTable.getClass(inheritsFrom);
        if (classInheritsFrom == null) throw new InheritsFromMissingClass(inheritsFrom, name);
        if (classInheritsFrom.isFinal()) throw new InheritsFromFinalClassException(inheritsFrom, name);
        if (classInheritsFrom.isStatic()) throw new InheritsFromStaticClassException(inheritsFrom, name);
        if (isAncestor(name)) throw new CircularInheritanceException(name);
        if (isAbstract()) {
            if (!classInheritsFrom.isAbstract()) throw new AbstractClassInheritsFromConcreteClassException(inheritsFrom,name);
        }
    }

}
