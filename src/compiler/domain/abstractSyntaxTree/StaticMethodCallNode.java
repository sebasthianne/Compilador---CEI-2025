package compiler.domain.abstractSyntaxTree;

import compiler.domain.Class;
import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.StaticMethodCallClassNotFoundException;
import compiler.semanticAnalyzer.semanticExceptions.VoidMethodCallInsideExpressionException;
import injector.Injector;

public class StaticMethodCallNode extends PrimaryNode {
    private final Token calledMethodName;
    private final Token calledMethodClassName;
    private final ParameterListNode parameterList;


    public StaticMethodCallNode(Token calledMethodName, Token calledMethodClassName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.calledMethodClassName = calledMethodClassName;
        this.parameterList = parameterList;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Class foundClass = Injector.getInjector().getSymbolTable().getClass(calledMethodClassName);
        if(foundClass == null) {
            throw new StaticMethodCallClassNotFoundException(calledMethodClassName);
        }
        Method method = foundClass.resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        Type returnType = method.getReturnType();
        if(returnType == null && isInExpression()) throw new VoidMethodCallInsideExpressionException(calledMethodName);
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
