package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.IfCheckNotBooleanException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class IfStatementNode extends StatementNode {
    private final ExpressionNode ifCheckExpression;
    private final StatementNode ifBody;
    private final Token ifToken;

    public IfStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody, Token ifToken) {
        this.ifCheckExpression = ifCheckExpression;
        this.ifBody = ifBody;
        this.ifToken = ifToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        ifBody.setInExpression(false);
        Type ifCheckExpressionReturnType = ifCheckExpression.checkExpression();
        if(!ifCheckExpressionReturnType.isBoolean()) throw new IfCheckNotBooleanException(ifToken,ifCheckExpressionReturnType);
        else ifBody.checkNode();
    }

}
