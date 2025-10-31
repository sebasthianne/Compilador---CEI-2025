package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class IntUnaryNotIntOperatorException extends SemanticException {
    public IntUnaryNotIntOperatorException(Token operator1) {
        super(operator1);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
