package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class ParenthesizedExpressionNode extends PrimaryNode {
    private final ExpressionNode subExpression;

    public ParenthesizedExpressionNode(ExpressionNode subExpression) {
        this.subExpression = subExpression;
    }

    @Override
    public boolean isAssignableWithoutReference() {
        return false;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        return subExpression.checkExpression();
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }
}
