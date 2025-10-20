package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class WhileStatementNode extends StatementNode {
    private final ExpressionNode whileConditionExpression;
    private final StatementNode whileBody;

    public WhileStatementNode(ExpressionNode whileConditionExpression, StatementNode whileBody) {
        this.whileConditionExpression = whileConditionExpression;
        this.whileBody = whileBody;
    }

    @Override
    public void checkNode() throws SemanticException {
        Type ifCheckExpressionReturnType = whileConditionExpression.checkExpression();
        if(!ifCheckExpressionReturnType.isBoolean()) throw new SemanticException(ifCheckExpressionReturnType.getTypeName()) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        else whileBody.checkNode();
    }

}
