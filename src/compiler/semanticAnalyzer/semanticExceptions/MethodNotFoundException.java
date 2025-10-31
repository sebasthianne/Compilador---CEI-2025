package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class MethodNotFoundException extends SemanticException {
    public MethodNotFoundException(Token methodName) {
        super(methodName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
