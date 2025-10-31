package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class BooleanLiteralNode extends PrimitiveLiteralNode {
    private final Literal booleanLiteral;

    public BooleanLiteralNode(Token booleanLiteralValue) {
        booleanLiteral = new Literal(new PrimitiveType(new Token("palabraReservadaboolean","boolean", booleanLiteralValue.lineNumber())),booleanLiteralValue);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        return booleanLiteral.getType();
    }

    @Override
    public boolean isCall() {
        return false;
    }
}
