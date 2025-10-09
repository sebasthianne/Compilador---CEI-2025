package compiler.symbolTable;

import compiler.domain.*;
import compiler.domain.Class;

import java.util.HashMap;

public class SymbolTableImpl implements SymbolTable {
    private final HashMap<String,Class> classTable;
    private Class currentClass;
    private Callable currentMethodOrConstructor;
    private boolean currentCallableIsConstructor;

    public SymbolTableImpl(){
        classTable=new HashMap<String, Class>(9973);
    }

    @Override
    public void addClass(Class c) {
        if(currentClass!=null){
            classTable.put(currentClass.getName().lexeme(),currentClass);
        }
        currentClass=c;
    }

    @Override
    public void addMethod(Method m) {
        currentMethodOrConstructor=m;
        currentCallableIsConstructor=false;
    }

    @Override
    public Class getCurrentClass() {
        return currentClass;
    }

    @Override
    public void addConstructor(Constructor c) {
        currentMethodOrConstructor=c;
        currentCallableIsConstructor=true;
    }

    @Override
    public Iterable<Class> getTable() {
        return classTable.values();
    }

    @Override
    public Class getClass(String name) {
        return classTable.get(name);
    }

    @Override
    public Callable getCurrentMethodOrConstructor() {
        return currentMethodOrConstructor;
    }

    @Override
    public void insertCurrentMethodOrConstructorInTable() {
        if(currentCallableIsConstructor){
            currentClass.addConstructor((Constructor) currentMethodOrConstructor);
        } else {
            currentClass.addMethod((Method) currentMethodOrConstructor);
        }
    }
}
