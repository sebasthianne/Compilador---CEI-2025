package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class MismatchException extends SyntacticException {
    public MismatchException(String expectedName, Token received) {
        super(expectedName, received);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getTokenReceived().lineNumber() + " se esperaba el token: " + getExpectedTokenName() + " pero se recibió el token: " + getTokenReceived().name() + " con el lexema: " + getTokenReceived().lexeme();
    }
}
