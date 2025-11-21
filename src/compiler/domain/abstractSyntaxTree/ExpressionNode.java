package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class ExpressionNode extends StatementNode{

    public abstract Type checkExpression() throws SemanticException;

    @Override
    public void checkNode() throws SemanticException {
        checkExpression();
    }

    public abstract boolean isCall();

    public abstract boolean isVoidMethodCall();

}
