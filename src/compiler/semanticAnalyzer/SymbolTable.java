package compiler.semanticAnalyzer;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;


public interface SymbolTable {
    void addClass(Class c) throws SemanticException;
    void addMethod(Method m);
    Class getCurrentClass();
    void addConstructor(Constructor c);
    Iterable<Class> getTable();
    Class getClass(Token name);
    Callable getCurrentMethodOrConstructor();
    void insertCurrentMethodOrConstructorInTable() throws SemanticException;
    void checkSymbolTable() throws SemanticException;
    void consolidate() throws SemanticException;
    Constructor resolveConstructor(Token className, int arity) throws SemanticException;

    Block getCurrentBlock();

    void setCurrentBlock(Block currentBlock);
}
