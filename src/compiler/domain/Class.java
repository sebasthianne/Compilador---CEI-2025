package compiler.domain;

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
        constructorTable=new HashMap<String, Constructor>(997);
        methodTable=new HashMap<String, Method>(997);
        attributeTable =new HashMap<String, Attribute>(997);
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
}
