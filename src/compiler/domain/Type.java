package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class Type {
    private final Token typeName;

    public Type(Token typeName) {
        this.typeName = typeName;
    }

    public abstract void checkType() throws SemanticException;

    public Token getTypeName() {
        return typeName;
    }

    public abstract boolean isBoolean();

    public boolean compareType(Type type){
        return type.getTypeName().lexeme().equals(typeName.lexeme());
    }

    public abstract boolean conformsTo(Type t);
}
