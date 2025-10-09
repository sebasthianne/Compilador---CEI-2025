package compiler.symbolTable;
import compiler.domain.*;
import compiler.domain.Class;


public interface SymbolTable {
    public void addClass(Class c);
    public void addMethod(Method m);
    public Class getCurrentClass();
    public void addConstructor(Constructor c);
    public Iterable<Class> getTable();
    public Class getClass(String name);
    public Callable getCurrentMethodOrConstructor();
    public void insertCurrentMethodOrConstructorInTable();
}
