package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;

public abstract class BinaryOperatorExpressionNode extends ComposedExpressionNode {
    private final Token operator;
    private final BasicExpressionNode leftExpression;
    private final ComposedExpressionNode rightExpression;

    protected BinaryOperatorExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    public Token getOperator() {
        return operator;
    }

    public BasicExpressionNode getLeftExpression() {
        return leftExpression;
    }

    public ComposedExpressionNode getRightExpression() {
        return rightExpression;
    }
}
