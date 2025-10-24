package compiler.domain;

import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.*;
import injector.Injector;

import java.util.HashMap;
import java.util.Iterator;

public class Class{
    private final Token name;
    private Token inheritsFrom;
    private final Token modifier;
    private final HashMap<String,Constructor> constructorTable;
    private final HashMap<String,Method> methodTable;
    private final HashMap<String, Attribute> attributeTable;
    private boolean isConsolidated;

    public Class(Token name, Token modifier) {
        this.name = name;
        this.modifier=modifier;
        this.inheritsFrom=null;
        constructorTable= new HashMap<>(997);
        methodTable= new HashMap<>(997);
        attributeTable = new HashMap<>(997);
        isConsolidated= false;
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

    public boolean containsConstructor(Constructor c){
        String constKey= c.getName().lexeme()+"/"+c.getArity();
        return constructorTable.get(constKey)!=null;
    }

    public Constructor getConstructor(int arity){
        return constructorTable.get(name.lexeme()+"/"+arity);
    }

    public void addConstructor(Constructor constructor){
        String constKey= constructor.getName().lexeme()+"/"+constructor.getArity();
        constructorTable.put(constKey,constructor);
    }

    public Iterable<Method> getMethodTable() {
        return methodTable.values();
    }

    public boolean containsMethod(Method m){
        String methodKey= m.getName().lexeme()+"/"+m.getArity();
        return methodTable.get(methodKey)!=null;
    }

    public Method getMethod(String lexeme, int arity) {
        return methodTable.get(lexeme+"/"+arity);
    }

    public void addMethod(Method method){
        String methodKey= method.getName().lexeme()+"/"+method.getArity();
        methodTable.put(methodKey,method);
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

    public void consolidate() throws SemanticException {
        if(!isConsolidated()&&!isObject()){
            Class inheritsFrom= Injector.getInjector().getSymbolTable().getClass(getInheritsFrom());
            recursivelyConsolidate(inheritsFrom);
            consolidateAttributes(inheritsFrom);
            consolidateMethods(inheritsFrom);
        }
        isConsolidated=true;
    }

    private void consolidateMethods(Class inheritsFrom) throws SemanticException{
        for(Method m : inheritsFrom.getMethodTable()){

            if(containsMethod(m)) {
                Method method=getMethod(m.getName().lexeme(),m.getArity());
                redefinitionChecks(m, method);
            } else{
                if(m.isAbstract()&&!isAbstract()) throw new AbstractMethodNotRedefinedException(name,m.getName());
                addMethod(m);
            }
        }
    }


    private void redefinitionChecks(Method m, Method method) throws SemanticException {
        if(m.isFinal()) throw new RedefiningFinalMethodException(method.getName(),name);
        if(m.isStatic()) throw new RedefiningStaticMethodException(method.getName(),name);
        if(method.isStatic()) throw new RedefiningStaticMethodException(method.getName(),name);
        checkParameters(m, method);
        checkReturnType(m, method);
    }

    private void checkReturnType(Method m, Method method) throws SemanticException{
        if(m.getReturnType()==null){
            if(method.getReturnType()!=null) throw new ReturnTypeMismatchInMethodRedefinition(method.getName(),name);
        } else  if(method.getReturnType()==null) throw new ReturnTypeMismatchInMethodRedefinition(method.getName(),name);
                else if (!m.getReturnType().compareType(method.getReturnType()))
                throw new ReturnTypeMismatchInMethodRedefinition(method.getName(),name);
    }

    private void checkParameters(Method m, Method method) throws SemanticException {
        Iterator<Parameter> parentParameters = m.getParameterList().iterator();
        Iterator<Parameter> currentParameters = method.getParameterList().iterator();
        while(parentParameters.hasNext() && currentParameters.hasNext()){
            Parameter parentParameter = parentParameters.next();
            Parameter currentParameter = currentParameters.next();
            if(!parentParameter.getType().compareType(currentParameter.getType())) throw new ParameterTypeMismatchInMethodRedefinition(currentParameter.getName(),name, method.getName());
        }
    }

    private void consolidateAttributes(Class inheritsFrom) throws SemanticException{
        for(Attribute a : inheritsFrom.getAttributeTable()){
            Attribute attribute=getAttribute(a.getName());
            if(attribute!=null) throw new RedefinedAttributeException(attribute.getName(),name);
            else{
                addAttribute(a);
            }
        }
    }

    private void recursivelyConsolidate(Class inheritsFrom) throws SemanticException {
        if(!inheritsFromObject()) {
            inheritsFrom.consolidate();
        }
    }

    private boolean isConsolidated() {
        return isConsolidated;
    }

    public Method resolveMethod(Token methodName, int arity) throws SemanticException{
        Method method = getMethod(methodName.lexeme(),arity);
        if(method == null) throw new SemanticException(methodName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return method;
    }

}
