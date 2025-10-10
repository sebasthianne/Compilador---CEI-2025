package compiler.semanticAnalyzer;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;


public interface SymbolTable {
    public void addClass(Class c) throws SemanticException;
    public void addMethod(Method m);
    public Class getCurrentClass();
    public void addConstructor(Constructor c);
    public Iterable<Class> getTable();
    public Class getClass(String name);
    public Callable getCurrentMethodOrConstructor();
    public void insertCurrentMethodOrConstructorInTable() throws SemanticException;
}
