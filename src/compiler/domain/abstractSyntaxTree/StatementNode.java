package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;

public abstract class StatementNode extends ASTNode {
    protected Token semicolonToken;

    private boolean inExpression = true;

    public Token getSemicolonToken() {
        return semicolonToken;
    }

    public void setSemicolonToken(Token semicolonToken) {
        this.semicolonToken = semicolonToken;
    }

    public boolean isInExpression() {
        return inExpression;
    }

    public void setInExpression(boolean inExpression) {
        this.inExpression = inExpression;
    }
}
