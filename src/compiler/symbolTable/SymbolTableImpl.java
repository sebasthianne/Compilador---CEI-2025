package compiler.symbolTable;

import compiler.domain.*;
import compiler.domain.Class;

import java.util.HashMap;

public class SymbolTableImpl implements SymbolTable {
    private final HashMap<Token,Class> classTable;
    private Class currentClass;
    private Callable currentMethodOrConstructor;
    private boolean currentCallableIsConstructor;

    public SymbolTableImpl(){
        classTable=new HashMap<Token, Class>(9973);
    }

    @Override
    public void addClass(Class c) {
        if(currentClass!=null){
            classTable.put(currentClass.getName(),currentClass);
        }
        currentClass=c;
    }

    @Override
    public void addMethod(Method m) {

    }

    @Override
    public void addConstructor(Constructor c) {

    }

    @Override
    public Iterable<Class> getTable() {
        return classTable.values();
    }

    @Override
    public Class getClass(Token t) {
        return classTable.get(t);
    }
}
