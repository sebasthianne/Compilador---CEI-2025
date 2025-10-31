package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.abstractSyntaxTree.StatementNode;

public class NonValidStatementExpression extends SemanticException {
    public NonValidStatementExpression(StatementNode s) {
        super(s.getSemicolonToken());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
