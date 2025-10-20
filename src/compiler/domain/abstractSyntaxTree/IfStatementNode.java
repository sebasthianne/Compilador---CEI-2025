package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IfStatementNode extends StatementNode {
    private final ExpressionNode ifCheckExpression;
    private final StatementNode ifBody;

    public IfStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody) {
        this.ifCheckExpression = ifCheckExpression;
        this.ifBody = ifBody;
    }

    @Override
    public void checkNode() throws SemanticException {
        Type ifCheckExpressionReturnType = ifCheckExpression.checkExpression();
        if(!ifCheckExpressionReturnType.isBoolean()) throw new SemanticException(ifCheckExpressionReturnType.getTypeName()) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        else ifBody.checkNode();
    }

}
