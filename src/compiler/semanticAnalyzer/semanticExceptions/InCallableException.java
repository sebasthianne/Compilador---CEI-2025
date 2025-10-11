package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public abstract class InCallableException extends InClassException {
    private final Token methodOrConstructorName;

    public InCallableException(Token token, Token className, Token methodOrConstructorName) {
        super(token, className);
        this.methodOrConstructorName=methodOrConstructorName;
    }

    public Token getMethodOrConstructorName() {
        return methodOrConstructorName;
    }
}
