package compiler.semanticAnalyzer;

import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.ConstructorNameClassMismatchException;
import compiler.semanticAnalyzer.semanticExceptions.ReusedClassNameException;
import compiler.semanticAnalyzer.semanticExceptions.ReusedMethodNameInClassException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

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
    public void addClass(Class c) throws SemanticException {
        if(currentClass!=null){
            Class previousClassWithName = classTable.put(currentClass.getName().lexeme(),currentClass);
            if(previousClassWithName!=null) throw new ReusedClassNameException(currentClass.getName());
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
    public void insertCurrentMethodOrConstructorInTable() throws SemanticException {
        if(currentCallableIsConstructor){
            currentClass.addConstructor((Constructor) currentMethodOrConstructor);
            if(!currentMethodOrConstructor.getName().lexeme().equals(currentClass.getName().lexeme())) throw new ConstructorNameClassMismatchException(currentMethodOrConstructor.getName());
        } else {
            if(currentClass.getMethod(currentMethodOrConstructor.getName())!=null){
                throw new ReusedMethodNameInClassException(currentMethodOrConstructor.getName(),currentClass.getName());
            }
            currentClass.addMethod((Method) currentMethodOrConstructor);
        }
    }

}
