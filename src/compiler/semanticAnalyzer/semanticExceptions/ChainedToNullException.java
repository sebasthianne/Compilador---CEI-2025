package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ChainedToNullException extends SemanticException {
    public ChainedToNullException(Token chainedToken) {
        super(chainedToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "Es muy probable que esta excepción no pueda ocurrir nunca por el sintáctico";
    }
}
