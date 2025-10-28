package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class UnaryOperatorExpressionNode extends BasicExpressionNode {
    @Override
    public boolean isAssignable() {
        return false;
    }

}
