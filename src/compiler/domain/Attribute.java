package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class Attribute extends Variable {

    public Attribute(Token name, Type type) {
        super(name,type);
    }

    public void checkAttribute() throws SemanticException {
        type.checkType();
    }

}
