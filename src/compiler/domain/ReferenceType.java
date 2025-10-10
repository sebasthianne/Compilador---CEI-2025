package compiler.domain;

import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.MissingAttributeTypeException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ReferenceType extends Type {
    public ReferenceType(Token typeName) {
        super(typeName);
    }

    @Override
    public void checkType() throws SemanticException{
        SymbolTable symbolTable=Injector.getInjector().getSymbolTable();
        if(symbolTable.getClass(getTypeName())==null) throw new MissingAttributeTypeException(getTypeName(),symbolTable.getCurrentClass().getName());
    }

}
