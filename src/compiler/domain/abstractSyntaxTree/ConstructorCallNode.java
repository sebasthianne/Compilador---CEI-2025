package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ConstructorCallNode extends PrimaryNode {
    private final Token calledConstructorName;
    private final ParameterListNode parameterList;


    public ConstructorCallNode(Token calledConstructorName, int calledConstructorArity) {
        this.calledConstructorName = calledConstructorName;
        this.parameterList = new ParameterListNode(calledConstructorName);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        if(Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor() instanceof Method method && method.isStatic()) throw new SemanticException(calledConstructorName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        Type returnType = new ReferenceType(calledConstructorName);
        returnType.checkType();
        Constructor constructor = Injector.getInjector().getSymbolTable().resolveConstructor(calledConstructorName,parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(constructor);
        return returnType;
    }


    @Override
    public boolean isAssignable() {
        return false;
    }
}
