package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AssigningToNonAssignableException extends SemanticException {

    public AssigningToNonAssignableException(Token a) {
        super(a);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
