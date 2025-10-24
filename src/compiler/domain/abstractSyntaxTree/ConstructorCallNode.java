package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ConstructorCallNode extends PrimaryNode {
    private final Token calledConstructorName;
    private final ParameterListNode parameterList;


    public ConstructorCallNode(Token calledConstructorName, int calledConstructorArity) {
        this.calledConstructorName = calledConstructorName;
        this.parameterList = new ParameterListNode();
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Type returnType = new ReferenceType(calledConstructorName);
        returnType.checkType();
        Constructor constructor = Injector.getInjector().getSymbolTable().resolveConstructor(calledConstructorName,parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(constructor);
        return returnType;
    }


}
