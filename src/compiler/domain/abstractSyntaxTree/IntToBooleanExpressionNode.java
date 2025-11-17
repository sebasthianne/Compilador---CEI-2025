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
        Type type1 = getLeftExpression().checkExpression();
        Type type2 = getRightExpression().checkExpression();
        if(!type1.compareType(typeToCheck)||!type2.compareType(typeToCheck)) {
            throw new IntComparisonOperatorNotIntException(getOperator(),type1.getTypeName(),type2.getTypeName());
        }
        return typeToReturn;
    }

    @Override
    public boolean isCall() {
        return false;
    }

}
