package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class Parameter extends Variable {

    public Parameter(Token name, Type type) {
        super(name,type);
    }

    public void checkParameter() throws SemanticException {
        type.checkType();
    }
}
