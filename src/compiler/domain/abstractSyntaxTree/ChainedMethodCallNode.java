package compiler.domain.abstractSyntaxTree;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.VoidMethodCallInsideExpressionException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class ChainedMethodCallNode extends ChainedReferenceNode {
    private final Token calledMethodName;
    private final ParameterListNode parameterList;
    private Method method;

    public ChainedMethodCallNode(Token calledMethodName, ParameterListNode parameterList) {
        this.calledMethodName = calledMethodName;
        this.parameterList = parameterList;
        method=null;
    }

    @Override
    public Type checkChainedReference(Type chainedTo) throws SemanticException {
        checkTypeChainable(chainedTo,calledMethodName);
        Class chainedClass = Injector.getInjector().getSymbolTable().getClass(chainedTo.getTypeName());
        method = chainedClass.resolveMethod(calledMethodName, parameterList.size());
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

    @Override
    public boolean isVoidMethodCallWithoutReference() {
        return (method.getReturnType()==null);
    }

    @Override
    public void generateWithoutReference() {
        SourceManager source = Injector.getInjector().getSource();
        if(method.getReturnType()!=null){
            source.generate("RMEM 1");
            source.generate("SWAP");
        }
        parameterList.setInConstructorOrDynamicMethod(true);
        parameterList.generate();
        source.generate("DUP");
        source.generate("LOADREF 0");
        source.generate("LOADREF "+method.getOffset());
        source.generate("CALL");
    }
}
