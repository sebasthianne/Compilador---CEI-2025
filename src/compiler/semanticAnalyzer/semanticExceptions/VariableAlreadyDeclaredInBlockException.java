package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class VariableAlreadyDeclaredInBlockException extends SemanticException {
    public VariableAlreadyDeclaredInBlockException(Token declaredVariableName) {
        super(declaredVariableName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
