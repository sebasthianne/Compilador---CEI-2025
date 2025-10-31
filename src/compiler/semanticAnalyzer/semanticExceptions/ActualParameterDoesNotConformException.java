package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ActualParameterDoesNotConformException extends SemanticException {
    public ActualParameterDoesNotConformException(Token callableName1) {
        super(callableName1);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
