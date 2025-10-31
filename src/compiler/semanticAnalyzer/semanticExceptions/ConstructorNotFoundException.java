package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ConstructorNotFoundException extends SemanticException {
    public ConstructorNotFoundException(Token className) {
        super(className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
