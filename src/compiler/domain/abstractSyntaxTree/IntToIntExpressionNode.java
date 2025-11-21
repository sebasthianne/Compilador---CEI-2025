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
        Type type1 = getLeftExpression().checkExpression();
        Type type2 = getRightExpression().checkExpression();
        if(!type1.compareType(typeToReturn)||!type2.compareType(typeToReturn)) {
            throw new ArithmeticOperatorNotIntException(getOperator(),type1.getTypeName(),type2.getTypeName());
        }
        return typeToReturn;
    }

    @Override
    public boolean isCall() {
        return false;
    }

    @Override
    public boolean isVoidMethodCall() {
        return false;
    }

}
