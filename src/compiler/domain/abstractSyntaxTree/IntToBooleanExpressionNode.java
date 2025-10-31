package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.IntComparisonOperatorNotIntException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IntToBooleanExpressionNode extends BinaryOperatorExpressionNode {

    public IntToBooleanExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        PrimitiveType typeToCheck = new PrimitiveType(new Token("palabraReservadaint", "int", getOperator().lineNumber()));
        PrimitiveType typeToReturn = new PrimitiveType(new Token("palabraReservadaboolean", "boolean", getOperator().lineNumber()));
        if(!getLeftExpression().checkExpression().compareType(typeToCheck)||!getRightExpression().checkExpression().compareType(typeToCheck)) {
            throw new IntComparisonOperatorNotIntException(getOperator());
        }
        return typeToReturn;
    }

}
