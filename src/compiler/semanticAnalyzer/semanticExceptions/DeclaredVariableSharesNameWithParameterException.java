package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class DeclaredVariableSharesNameWithParameterException extends SemanticException {
    public DeclaredVariableSharesNameWithParameterException(Token declaredVariableName) {
        super(declaredVariableName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
