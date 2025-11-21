package compiler.domain.abstractSyntaxTree;

import compiler.GenerationUtils;
import compiler.domain.Class;
import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.StaticMethodCallClassNotFoundException;
import compiler.semanticAnalyzer.semanticExceptions.VoidMethodCallInsideExpressionException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class StaticMethodCallNode extends PrimaryNode {
    private final Token calledMethodName;
    private final Token calledMethodClassName;
    private final ParameterListNode parameterList;
    private Method method;


    public StaticMethodCallNode(Token calledMethodName, Token calledMethodClassName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.calledMethodClassName = calledMethodClassName;
        this.parameterList = parameterList;
        method=null;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Class foundClass = Injector.getInjector().getSymbolTable().getClass(calledMethodClassName);
        if(foundClass == null) {
            throw new StaticMethodCallClassNotFoundException(calledMethodClassName);
        }
        method = foundClass.resolveMethod(calledMethodName, parameterList.size());
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

    @Override
    public boolean isVoidMethodCallWithoutReference() {
        return method.getReturnType()==null;
    }

    @Override
    public void generateWithoutReference() {
        parameterList.generate();
        SourceManager source = Injector.getInjector().getSource();
        source.generate("PUSH "+GenerationUtils.getMethodLabel(method));
        source.generate("CALL");
    }
}
