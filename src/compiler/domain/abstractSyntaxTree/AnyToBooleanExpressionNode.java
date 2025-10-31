package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class AnyToBooleanExpressionNode extends BinaryOperatorExpressionNode {

    public AnyToBooleanExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        PrimitiveType typeToReturn = new PrimitiveType(new Token("palabraReservadaboolean", "boolean", getOperator().lineNumber()));
        Type leftType = getLeftExpression().checkExpression();
        Type rightType = getLeftExpression().checkExpression();
        if(!leftType.conformsTo(rightType)&&!rightType.conformsTo(leftType)) throw new SemanticException(getOperator()) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return typeToReturn;
    }
}
