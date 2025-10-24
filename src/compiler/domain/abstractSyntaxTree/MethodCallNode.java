package compiler.domain.abstractSyntaxTree;

import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class MethodCallNode extends PrimaryNode {
    private final Token calledMethodName;
    private final ParameterListNode parameterList;


    public MethodCallNode(Token calledMethodName, int calledConstructorArity) {
        this.calledMethodName = calledMethodName;
        this.parameterList = new ParameterListNode(calledMethodName);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Method method = Injector.getInjector().getSymbolTable().getCurrentClass().resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        return method.getReturnType();
    }


}
