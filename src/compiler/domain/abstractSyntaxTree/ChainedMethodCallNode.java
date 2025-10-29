package compiler.domain.abstractSyntaxTree;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ChainedMethodCallNode extends ChainedReferenceNode {
    private final Token calledMethodName;
    private final ParameterListNode parameterList;

    public ChainedMethodCallNode(Token calledMethodName,Token pointToken) {
        super(pointToken);
        this.calledMethodName = calledMethodName;
        this.parameterList = new ParameterListNode(calledMethodName);
    }

    @Override
    public Type checkChainedReference(Type chainedTo) throws SemanticException {
        checkTypeChainable(chainedTo);
        Class chainedClass = Injector.getInjector().getSymbolTable().getClass(chainedTo.getTypeName());
        Method method = chainedClass.resolveMethod(calledMethodName, parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(method);
        if(chainedReference==null) return method.getReturnType();
        else return chainedReference.checkChainedReference(method.getReturnType());
    }

    @Override
    public boolean isAssignable() {
        return false;
    }
}
