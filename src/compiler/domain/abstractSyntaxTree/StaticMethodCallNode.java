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


    public StaticMethodCallNode(Token calledMethodName, Token calledMethodClassName) {
        this.calledMethodName = calledMethodName;
        this.parameterList = new ParameterListNode(calledMethodName);
        this.calledMethodClassName = calledMethodClassName;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Class foundClass = Injector.getInjector().getSymbolTable().getClass(calledMethodClassName);
        if(foundClass == null) throw new SemanticException(calledMethodClassName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        Method method = foundClass.resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        return method.getReturnType();
    }


    @Override
    public boolean isAssignable() {
        return false;
    }
}
