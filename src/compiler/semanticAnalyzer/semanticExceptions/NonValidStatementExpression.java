package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.abstractSyntaxTree.StatementNode;

public class NonValidStatementExpression extends SemanticException {
    public NonValidStatementExpression(StatementNode s) {
        super(s.getSemicolonToken());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lexeme()+" se recibe el token "+getErrorToken().lexeme()+" cuando la expresión que le precede no conforma una sentencia válida";
    }
}
