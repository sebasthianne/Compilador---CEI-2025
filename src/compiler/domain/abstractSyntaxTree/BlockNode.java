package compiler.domain.abstractSyntaxTree;

import compiler.domain.Block;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public abstract class BlockNode extends StatementNode {
    @Override
    public void checkNode() throws SemanticException {
        Injector.getInjector().getSymbolTable().setCurrentBlock(new Block());
    }

    public Type resolveName(Token variableName){
        Type variableType = resolveNameLocal(variableName);
        if(variableType==null) variableType=resolveNameExternal(variableName);
        return variableType;
    }

    private Type resolveNameLocal(Token variableName) {
        return null;
    }// TODO: Implement Local name resolution

    protected abstract Type resolveNameExternal(Token variableName);

}
