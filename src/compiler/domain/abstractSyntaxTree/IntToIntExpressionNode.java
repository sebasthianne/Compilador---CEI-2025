package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.ArithmeticOperatorNotIntException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IntToIntExpressionNode extends BinaryOperatorExpressionNode {

    public IntToIntExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        PrimitiveType typeToReturn = new PrimitiveType(new Token("palabraReservadaint", "int", getOperator().lineNumber()));
        if(!getLeftExpression().checkExpression().compareType(typeToReturn)||!getRightExpression().checkExpression().compareType(typeToReturn)) {
            throw new ArithmeticOperatorNotIntException(getOperator());
        }
        return typeToReturn;
    }

}
