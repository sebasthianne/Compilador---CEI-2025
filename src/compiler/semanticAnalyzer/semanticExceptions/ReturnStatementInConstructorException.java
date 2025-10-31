package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReturnStatementInConstructorException extends SemanticException {
    public ReturnStatementInConstructorException(Token returnToken) {
        super(returnToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
