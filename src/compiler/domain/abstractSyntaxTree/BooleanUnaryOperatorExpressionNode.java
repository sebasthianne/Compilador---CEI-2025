package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.NonBooleanExpressionWithBooleanOperator;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class BooleanUnaryOperatorExpressionNode extends UnaryOperatorExpressionNode {

    public BooleanUnaryOperatorExpressionNode(Token operator, BasicExpressionNode expression) {
        super(operator, expression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Type typeToReturn= new PrimitiveType(new Token("palabraReservadaboolean","boolean", operator.lineNumber()));
        if(!expression.checkExpression().compareType(typeToReturn)) throw new NonBooleanExpressionWithBooleanOperator(operator);
        return typeToReturn;
    }

}
