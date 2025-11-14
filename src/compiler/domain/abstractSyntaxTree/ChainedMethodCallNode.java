package compiler.domain.abstractSyntaxTree;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.VoidMethodCallInsideExpressionException;
import injector.Injector;

public class ChainedMethodCallNode extends ChainedReferenceNode {
    private final Token calledMethodName;
    private final ParameterListNode parameterList;

    public ChainedMethodCallNode(Token calledMethodName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.parameterList = parameterList;
    }

    @Override
    public Type checkChainedReference(Type chainedTo) throws SemanticException {
        checkTypeChainable(chainedTo,calledMethodName);
        Class chainedClass = Injector.getInjector().getSymbolTable().getClass(chainedTo.getTypeName());
        Method method = chainedClass.resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        Type returnType = method.getReturnType();
        if(returnType == null && isInExpression()) throw new VoidMethodCallInsideExpressionException(calledMethodName);
        if(chainedReference==null) return returnType;
        else return chainedReference.checkChainedReference(returnType);
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
