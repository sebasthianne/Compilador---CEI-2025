package compiler.domain.abstractSyntaxTree;

import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.CalledInstancedMethodInsideOfStaticMethodException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class MethodCallNode extends PrimaryNode {


    protected final Token calledMethodName;
    protected final ParameterListNode parameterList;

    public MethodCallNode(Token calledMethodName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.parameterList = parameterList;
    }


    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Method method = Injector.getInjector().getSymbolTable().getCurrentClass().resolveMethod(calledMethodName, parameterList.size());
        if(!method.isStatic()&&Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor() instanceof Method currentMethod && currentMethod.isStatic()) {
            throw new CalledInstancedMethodInsideOfStaticMethodException(calledMethodName);
        }
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        return method.getReturnType();
    }


    @Override
    public boolean isAssignable() {
        return false;
    }

}
