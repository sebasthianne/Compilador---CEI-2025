package compiler.symbolTable;
import compiler.domain.Class;
import compiler.domain.Constructor;
import compiler.domain.Method;
import compiler.domain.Token;


public interface SymbolTable {
    public void addClass(Class c);
    public void addMethod(Method m);
    public Class getCurrentClass();
    public void addConstructor(Constructor c);
    public Iterable<Class> getTable();
    public Class getClass(Token t);
}
