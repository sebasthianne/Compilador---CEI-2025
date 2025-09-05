package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class InvalidAttributeOrMethodDeclarationException extends SyntacticException {
    public InvalidAttributeOrMethodDeclarationException(String expectedName, Token received) {
        super(expectedName, received);
    }
}
