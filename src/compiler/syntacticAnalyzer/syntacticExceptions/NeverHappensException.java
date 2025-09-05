package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class NeverHappensException extends SyntacticException {
    public NeverHappensException(String expectedName, Token received) {
        super(expectedName, received);
    }
}
