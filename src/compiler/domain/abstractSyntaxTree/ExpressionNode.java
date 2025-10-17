package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class ExpressionNode extends ASTNode{
    public abstract Type checkExpression() throws SemanticException;

    @Override
    public void checkNode() throws SemanticException {
        checkExpression();
    };
}
