package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IfStatementNodeImpl extends StatementNode {
    ExpressionNode ifCheckExpression;
    StatementNode ifBody;

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
