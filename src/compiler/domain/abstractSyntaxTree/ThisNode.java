package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class ThisNode extends PrimaryNode {
    private final Token thisToken;

    public ThisNode(Token thisToken) {
        this.thisToken = thisToken;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if(currentMethodOrConstructor instanceof Method currentMethod && currentMethod.isStatic()) throw new SemanticException(thisToken) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return new ReferenceType(Injector.getInjector().getSymbolTable().getCurrentClass().getName());
    }
}
