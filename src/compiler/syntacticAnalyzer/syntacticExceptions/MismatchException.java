package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class MismatchException extends SyntacticException {
    public MismatchException(String expectedName, Token received) {
        super(expectedName, received);
    }
}
