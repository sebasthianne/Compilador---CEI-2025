package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class Literal {
    private final Type type;
    private final Token literalValue;

    public Literal(Type type, Token literalValue) {
        this.type=type;
        this.literalValue = literalValue;
    }

    public void checkAttribute() throws SemanticException {
        type.checkType();
    }

    public Type getType() {
        return type;
    }
}
