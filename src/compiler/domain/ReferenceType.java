package compiler.domain;

import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.MissingReferenceTypeClassException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ReferenceType extends Type {
    public ReferenceType(Token typeName) {
        super(typeName);
    }

    @Override
    public void checkType() throws SemanticException{
        SymbolTable symbolTable=Injector.getInjector().getSymbolTable();
        if(symbolTable.getClass(getTypeName())==null) throw new MissingReferenceTypeClassException(getTypeName(),symbolTable.getCurrentClass().getName());
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean conformsTo(Type type) {
        Class classOfType = Injector.getInjector().getSymbolTable().getClass(type.getTypeName());
        return (classOfType!=null)&&(compareType(type)||classOfType.isAncestor(getTypeName()));
    }


}
