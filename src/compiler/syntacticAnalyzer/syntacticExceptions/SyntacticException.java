package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public abstract class SyntacticException extends Exception {
    private final String expectedTokenName;
    private final Token tokenReceived;

    public SyntacticException(String expectedName, Token received) {
        expectedTokenName = expectedName;
        tokenReceived = received;
    }

    public String getBasicErrorMessage() {
        return "Error Sintáctico en la línea " + tokenReceived.lineNumber();
    }

    public abstract String getDetailedErrorMessage();

    public String getExpectedTokenName() {
        return expectedTokenName;
    }

    public Token getTokenReceived() {
        return tokenReceived;
    }

}
