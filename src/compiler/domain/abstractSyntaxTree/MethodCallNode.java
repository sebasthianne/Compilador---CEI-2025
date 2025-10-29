package compiler.domain.abstractSyntaxTree;

import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class MethodCallNode extends PrimaryNode {
    private final Token calledMethodName;
    private final ParameterListNode parameterList;


    public MethodCallNode(Token calledMethodName) {
        this.calledMethodName = calledMethodName;
        this.parameterList = new ParameterListNode(calledMethodName);
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        if(Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor() instanceof Method method && method.isStatic()) throw new SemanticException(calledMethodName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        Method method = Injector.getInjector().getSymbolTable().getCurrentClass().resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        return method.getReturnType();
    }


    @Override
    public boolean isAssignable() {
        return false;
    }
}
