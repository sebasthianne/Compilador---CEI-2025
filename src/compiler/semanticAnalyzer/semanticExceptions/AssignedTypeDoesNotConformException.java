package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AssignedTypeDoesNotConformException extends SemanticException {
    public AssignedTypeDoesNotConformException(Token semicolonToken) {
        super(semicolonToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
