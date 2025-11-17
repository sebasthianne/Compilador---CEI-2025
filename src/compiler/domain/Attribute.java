package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class Attribute {
    private final Token name;
    private final Type type;

    public Attribute(Token name, Type type) {
        this.name = name;
        this.type=type;
    }

    public Token getName() {
        return name;
    }

    public void checkAttribute() throws SemanticException {
        type.checkType();
    }

    public Type getType() {
        return type;
    }
}
