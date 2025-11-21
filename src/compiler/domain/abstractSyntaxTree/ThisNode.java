package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.ThisOnStaticMethodException;
import injector.Injector;

public class ThisNode extends PrimaryNode {
    private final Token thisToken;

    public ThisNode(Token thisToken) {
        this.thisToken = thisToken;
    }

    @Override
    public boolean isAssignableWithoutReference() {
        return false;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if(currentMethodOrConstructor instanceof Method currentMethod && currentMethod.isStatic()) {
            throw new ThisOnStaticMethodException(this.thisToken,Injector.getInjector().getSymbolTable().getCurrentClass().getName(),currentMethodOrConstructor.getName());
        }
        return new ReferenceType(Injector.getInjector().getSymbolTable().getCurrentClass().getName());
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }

    @Override
    public boolean isVoidMethodCallWithoutReference() {
        return false;
    }

    @Override
    public void generateWithoutReference() {
        Injector.getInjector().getSource().generate("LOAD 3");
    }
}
