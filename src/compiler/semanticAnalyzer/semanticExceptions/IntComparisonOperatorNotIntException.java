package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class IntComparisonOperatorNotIntException extends SemanticException {
    public IntComparisonOperatorNotIntException(Token operator) {
        super(operator);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
