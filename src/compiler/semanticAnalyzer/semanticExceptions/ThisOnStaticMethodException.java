package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ThisOnStaticMethodException extends SemanticException {
    public ThisOnStaticMethodException(Token thisToken) {
        super(thisToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
