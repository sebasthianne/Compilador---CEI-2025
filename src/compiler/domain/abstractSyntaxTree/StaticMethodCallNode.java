package compiler.domain.abstractSyntaxTree;

import compiler.domain.Class;
import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
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
        return method.getReturnType();
    }


    @Override
    public boolean isAssignableWithoutReference() {
        return false;
    }

    @Override
    public boolean isCallWithoutReference() {
        return true;
    }

    private static class StaticMethodCallClassNotFoundException extends SemanticException {
        public StaticMethodCallClassNotFoundException(Token calledMethodClassName) {
            super(calledMethodClassName);
        }

        @Override
        public String getDetailedErrorMessage() {
            return "";
        }
    }
}
