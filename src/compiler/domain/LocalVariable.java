package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class LocalVariable {
    private final Token name;
    private final Type type;

    public LocalVariable(Token name, Type type) {
        this.name = name;
        this.type=type;
    }

    public Token getName() {
        return name;
    }

    public Type getType(){
        return type;
    }

    public void checkLocalVariable() throws SemanticException {
        type.checkType();
    }
}
