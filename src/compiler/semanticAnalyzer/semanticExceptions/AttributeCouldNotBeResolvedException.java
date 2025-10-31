package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AttributeCouldNotBeResolvedException extends SemanticException {
    public AttributeCouldNotBeResolvedException(Token name) {
        super(name);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
