package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ArithmeticOperatorNotIntException extends SemanticException {
    public ArithmeticOperatorNotIntException(Token operator) {
        super(operator);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
