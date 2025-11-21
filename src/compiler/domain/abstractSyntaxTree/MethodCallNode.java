package compiler.domain.abstractSyntaxTree;

import compiler.domain.Method;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.CalledInstanceMethodInsideOfStaticMethodException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.VoidMethodCallInsideExpressionException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class MethodCallNode extends PrimaryNode {


    protected final Token calledMethodName;
    protected final ParameterListNode parameterList;
    private Method method;

    public MethodCallNode(Token calledMethodName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.parameterList = parameterList;
        method = null;
    }


    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        method = Injector.getInjector().getSymbolTable().getCurrentClass().resolveMethod(calledMethodName, parameterList.size());
        if(!method.isStatic()&&Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor() instanceof Method currentMethod && currentMethod.isStatic()) {
            throw new CalledInstanceMethodInsideOfStaticMethodException(calledMethodName,Injector.getInjector().getSymbolTable().getCurrentClass().getName(),Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName());
        }
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
        SourceManager source = Injector.getInjector().getSource();
        source.generate("LOAD 3");
        parameterList.setInConstructorOrDynamicMethod(true);
        parameterList.generate();
        source.generate("DUP");
        source.generate("LOADREF 0");
        source.generate("LOADREF "+method.getOffset());
        source.generate("CALL");
    }

}
