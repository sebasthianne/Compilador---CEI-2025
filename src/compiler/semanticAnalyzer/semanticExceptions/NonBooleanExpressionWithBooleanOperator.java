package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class NonBooleanExpressionWithBooleanOperator extends SemanticException {
    public NonBooleanExpressionWithBooleanOperator(Token operator) {
        super(operator);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
