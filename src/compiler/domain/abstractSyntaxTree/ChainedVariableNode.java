package compiler.domain.abstractSyntaxTree;

import compiler.domain.Class;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.domain.Variable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ChainedVariableNode extends ChainedReferenceNode {
    private final Token variableName;
    private Variable variable;

    public ChainedVariableNode(Token variableName) {
        this.variableName = variableName;
        variable = null;
    }

    @Override
    public Type checkChainedReference(Type chainedTo) throws SemanticException {
        checkTypeChainable(chainedTo,variableName);
        Class chainedClass = Injector.getInjector().getSymbolTable().getClass(chainedTo.getTypeName());
        variable = chainedClass.resolveAttribute(variableName);
        Type typeToReturn = variable.getType();
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

    public boolean isNameResolved(){
        return variable!=null;
    }
}
