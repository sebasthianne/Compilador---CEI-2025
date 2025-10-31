package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class DeclaredVariableIsNullTypeException extends SemanticException {
    public DeclaredVariableIsNullTypeException(Token assignmentToken) {
        super(assignmentToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
