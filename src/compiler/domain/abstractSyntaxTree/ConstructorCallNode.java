package compiler.domain.abstractSyntaxTree;

import compiler.GenerationUtils;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class ConstructorCallNode extends PrimaryNode {
    private final Token calledConstructorName;
    private final ParameterListNode parameterList;
    private Constructor constructor;


    public ConstructorCallNode(Token calledConstructorName, ParameterListNode parameterList) {
        this.calledConstructorName = calledConstructorName;
        this.parameterList = parameterList;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Type returnType = new ReferenceType(calledConstructorName);
        returnType.checkType();
        constructor = Injector.getInjector().getSymbolTable().resolveConstructor(calledConstructorName,parameterList.size());
        parameterList.checkNode();
        parameterList.checkParameterMatch(constructor);
        return returnType;
    }

    @Override
    public void generateWithoutReference() {
        SourceManager source = Injector.getInjector().getSource();
        Class constructorClass = Injector.getInjector().getSymbolTable().getClass(constructor.getName());
        source.generate("RMEM 1");
        source.generate("PUSH "+constructorClass.getAttributeCount());
        source.generate("PUSH simple_malloc");
        source.generate("CALL");
        source.generate("DUP");
        source.generate("PUSH lblVT"+constructorClass.getName().lexeme());
        source.generate("STOREREF 0");
        source.generate("DUP");
        parameterList.setInConstructorOrDynamicMethod(true);
        parameterList.generate();
        source.generate("PUSH "+ GenerationUtils.getConstructorLabel(constructor));
        source.generate("CALL");
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
        return false;
    }
}
