package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IntLiteralNode extends PrimitiveLiteralNode {
    private final Literal intLiteral;

    public IntLiteralNode(Token intLiteralValue) {
        intLiteral = new Literal(new PrimitiveType(new Token("palabraReservadaint","int", intLiteralValue.lineNumber())),intLiteralValue);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        return intLiteral.getType();
    }
}
