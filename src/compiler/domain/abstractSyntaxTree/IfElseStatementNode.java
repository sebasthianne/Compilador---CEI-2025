package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IfElseStatementNode extends IfStatementNode {
    private final StatementNode elseBody;

    public IfElseStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody, Token ifToken, StatementNode elseBody) {
        super(ifCheckExpression,ifBody,ifToken);
        this.elseBody = elseBody;
    }

    @Override
    public void checkNode() throws SemanticException {
        super.checkNode();
        elseBody.setInExpression(false);
        elseBody.checkNode();
    }
}
