package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;

public abstract class StatementNode extends ASTNode {
    protected Token semicolonToken;

    public Token getSemicolonToken() {
        return semicolonToken;
    }

    public void setSemicolonToken(Token semicolonToken) {
        this.semicolonToken = semicolonToken;
    }
}
