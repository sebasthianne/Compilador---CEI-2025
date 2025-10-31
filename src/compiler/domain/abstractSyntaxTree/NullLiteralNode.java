package compiler.domain.abstractSyntaxTree;

import compiler.domain.NullType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class NullLiteralNode extends PrimitiveLiteralNode {
    private final Token nullToken;

    public NullLiteralNode(Token nullToken) {
        this.nullToken = nullToken;
    }


    @Override
    public Type checkExpression() throws SemanticException {
        return new NullType(nullToken);
    }

    @Override
    public boolean isCall() {
        return false;
    }
}
