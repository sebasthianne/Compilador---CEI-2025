package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ConstructorCallNode extends PrimaryNode {
    private final Token calledConstructorName;
    private final ParameterListNode parameterList;


    public ConstructorCallNode(Token calledConstructorName, ParameterListNode parameterList) {
        this.calledConstructorName = calledConstructorName;
        this.parameterList = parameterList;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Type returnType = new ReferenceType(calledConstructorName);
        returnType.checkType();
        Constructor constructor = Injector.getInjector().getSymbolTable().resolveConstructor(calledConstructorName,parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(constructor);
        return returnType;
    }


    @Override
    public boolean isAssignableWithoutReference() {
        return false;
    }

    @Override
    public boolean isCallWithoutReference() {
        return true;
    }
}
