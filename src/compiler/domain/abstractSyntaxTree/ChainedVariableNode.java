package compiler.domain.abstractSyntaxTree;

import compiler.domain.Class;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ChainedVariableNode extends ChainedReferenceNode {
    private final Token variableName;
    public ChainedVariableNode(Token variableName) {
        this.variableName = variableName;
    }

    @Override
    public Type checkChainedReference(Type chainedTo) throws SemanticException {
        checkTypeChainable(chainedTo);
        Class chainedClass = Injector.getInjector().getSymbolTable().getClass(chainedTo.getTypeName());
        Type typeToReturn = chainedClass.resolveAttribute(variableName);
        if(chainedReference==null) return typeToReturn;
        else return chainedReference.checkChainedReference(typeToReturn);
    }

    @Override
    public boolean isAssignableWithoutReference() {
        return true;
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }
}
