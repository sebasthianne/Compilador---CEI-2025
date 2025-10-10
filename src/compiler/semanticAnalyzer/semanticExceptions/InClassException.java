package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public abstract class InClassException extends SemanticException {
    private final Token className;

    public InClassException(Token token, Token className) {
        super(token);
        this.className = className;
    }


    public Token getClassName() {
        return className;
    }
}
