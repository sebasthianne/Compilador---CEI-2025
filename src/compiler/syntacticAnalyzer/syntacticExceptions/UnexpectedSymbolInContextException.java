package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class UnexpectedSymbolInContextException extends SyntacticException {
    private final String exceptionContext;

    public UnexpectedSymbolInContextException(String expectedName, Token received, String context) {
        super(expectedName, received);
        exceptionContext = context;
    }

    public String getDetailedErrorMessage() {
        return "En la línea " + getTokenReceived().lineNumber() + " con el contexto: " + exceptionContext + " se esperaba el token: " + getExpectedTokenName() + " pero se recibió el token: " + getTokenReceived().name() + " con el lexema: " + getTokenReceived().lexeme();
    }

}
