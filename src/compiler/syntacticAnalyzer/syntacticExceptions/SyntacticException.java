package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class SyntacticException extends Exception {
    private String expectedTokenName;
    private Token tokenReceived;
    public SyntacticException(String expectedName,Token received) {
        expectedTokenName=expectedName;
        tokenReceived=received;
    }
}
