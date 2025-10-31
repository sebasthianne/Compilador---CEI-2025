package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.NonBooleanExpressionWithBooleanOperator;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class BooleanUnaryOperatorExpressionNode extends UnaryOperatorExpressionNode {
    private final Token operator;
    private final BasicExpressionNode expression;

    public BooleanUnaryOperatorExpressionNode(Token operator, BasicExpressionNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Type typeToReturn= new PrimitiveType(new Token("palabraReservadaboolean","boolean", operator.lineNumber()));
        if(!expression.checkExpression().compareType(typeToReturn)) throw new NonBooleanExpressionWithBooleanOperator(operator);
        return typeToReturn;
    }
}
