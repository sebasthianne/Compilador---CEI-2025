package compiler.domain.abstractSyntaxTree;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IfElseStatementNode extends IfStatementNode {
    private final StatementNode elseBody;

    public IfElseStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody, StatementNode elseBody) {
        super(ifCheckExpression, ifBody);
        this.elseBody = elseBody;
    }

    @Override
    public void checkNode() throws SemanticException {
        super.checkNode();
        elseBody.checkNode();
    }
}
