package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class PrimitiveLiteralNode extends BasicExpressionNode {
    @Override
    public boolean isAssignable() {
        return false;
    }
}
