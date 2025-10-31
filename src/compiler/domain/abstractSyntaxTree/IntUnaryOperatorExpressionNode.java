package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.IntUnaryNotIntOperatorException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IntUnaryOperatorExpressionNode extends UnaryOperatorExpressionNode {
    private final Token operator;
    private final BasicExpressionNode expression;

    public IntUnaryOperatorExpressionNode(Token operator, BasicExpressionNode expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Type typeToReturn= new PrimitiveType(new Token("palabraReservadaint","int", operator.lineNumber()));
        if(!expression.checkExpression().compareType(typeToReturn)) {
            throw new IntUnaryNotIntOperatorException(operator);
        }
        return typeToReturn;
    }

}
