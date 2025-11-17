package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class CharLiteralNode extends PrimitiveLiteralNode {
    private final Literal charLiteral;

    public CharLiteralNode(Token charLiteralValue) {
        charLiteral = new Literal(new PrimitiveType(new Token("palabraReservadachar","char", charLiteralValue.lineNumber())),charLiteralValue);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        return charLiteral.getType();
    }

    @Override
    public boolean isCall() {
        return false;
    }
}
