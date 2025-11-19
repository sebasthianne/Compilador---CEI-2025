package compiler.domain.abstractSyntaxTree;

import compiler.GenerationUtils;
import compiler.domain.Token;
import injector.Injector;

public abstract class UnaryOperatorExpressionNode extends BasicExpressionNode {
    protected final Token operator;
    protected final BasicExpressionNode expression;

    public UnaryOperatorExpressionNode(Token operator, BasicExpressionNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }


    @Override
    public boolean isCall() {
        return false;
    }

    @Override
    public void generate() {
        Injector.getInjector().getSource().generate(GenerationUtils.getUnaryOperation(operator));
    }
}
