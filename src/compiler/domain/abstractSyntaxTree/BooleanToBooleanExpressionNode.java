package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class BooleanToBooleanExpressionNode extends BinaryOperatorExpressionNode {

    protected BooleanToBooleanExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        PrimitiveType typeToReturn = new PrimitiveType(new Token("palabraReservadaboolean", "boolean", getOperator().lineNumber()));
        if(!getLeftExpression().checkExpression().compareType(typeToReturn)||!getRightExpression().checkExpression().compareType(typeToReturn)) throw new SemanticException(getOperator()) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return typeToReturn;
    }
}
